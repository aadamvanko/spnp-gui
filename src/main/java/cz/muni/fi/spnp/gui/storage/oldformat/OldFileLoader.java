package cz.muni.fi.spnp.gui.storage.oldformat;

import cz.muni.fi.spnp.gui.components.menu.project.ProjectViewModel;
import cz.muni.fi.spnp.gui.storage.oldformat.converters.OldFormatToViewModelConverter;
import cz.muni.fi.spnp.gui.storage.oldformat.converters.OldProjectConverter;
import cz.muni.fi.spnp.gui.storage.oldformat.models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;

/**
 * Loads the data from the old format files.
 */
public class OldFileLoader {

    private final OldFormatToViewModelConverter oldFormatToViewModelConverter;
    private final OldProjectConverter oldProjectConverter;

    public OldFileLoader() {
        this.oldFormatToViewModelConverter = new OldFormatToViewModelConverter();
        this.oldProjectConverter = new OldProjectConverter();
    }

    public ProjectViewModel loadProject(String projectFilepath) {
        var oldProject = loadOldFormatProject(projectFilepath);
        if (oldProject == null) {
            return null;
        }

        var projectViewModel = loadProject(projectFilepath, oldProject);
        return projectViewModel;
    }

    private ProjectViewModel loadProject(String projectFilepath, ProjectOldFormat oldProject) {
        var project = oldProjectConverter.convert(oldProject);
        for (var submodelName : oldProject.submodelsNames) {
            var projectFolder = Path.of(projectFilepath).getParent();
            var submodelFilename = String.format("%s_%s.srn", oldProject.modelName, submodelName);
            var submodelFilepath = Path.of(projectFolder.toString(), submodelFilename);
            var submodel = loadSubmodel(submodelFilepath);
            if (submodel == null) {
                continue;
            }

            submodel.name = submodelName;
            try {
                var diagram = oldFormatToViewModelConverter.convert(submodel, project);
                project.getDiagrams().add(diagram);
            } catch (Exception exception) {
                System.err.println(String.format("Could not load submodel %s in old format due to error when converting to view model:", submodelFilepath));
                exception.printStackTrace();
            }
        }
        return project;
    }

    private Submodel loadSubmodel(Path submodelFilepath) {
        var submodel = new Submodel();
        try (BufferedReader bufferedReader = Files.newBufferedReader(submodelFilepath)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                if (line.equals("Include:")) {
                    submodel.includes = readBlock(bufferedReader, "IncludeEnd");
                } else if (line.equals("Define:")) {
                    submodel.defines = readBlock(bufferedReader, "DefineEnd");
                } else if (line.equals("Place:")) {
                    submodel.elements.add(readPlace(bufferedReader));
                } else if (line.equals("Immediate:")) {
                    submodel.elements.add(readImmediate(bufferedReader));
                } else if (line.equals("Timed:")) {
                    submodel.elements.add(readTimed(bufferedReader));
                } else if (line.equals("Arc:")) {
                    submodel.elements.add(readArc(bufferedReader));
                } else if (line.startsWith("Function:")) {
                    submodel.functions.add(readFunction(bufferedReader, line));
                } else if (line.startsWith("Variable:")) {
                    submodel.variables.add(readVariable(bufferedReader, line));
                } else if (line.startsWith("UserPromptText:")) {
                    addUserPromptText(submodel.variables, extractValue(line));
                } else if (line.startsWith("Inf:")) {
                    throw new UnsupportedOperationException("Infinite transitions are currently not supported!");
                }
            }
        } catch (UnsupportedOperationException unsupportedOperationException) {
            System.err.println(String.format("Could not load submodel %s in old format due to error: %s", submodelFilepath, unsupportedOperationException.getMessage()));
            return null;
        } catch (Exception exception) {
            System.err.println(String.format("Could not load submodel %s in old format due to error:", submodelFilepath));
            exception.printStackTrace();
            return null;
        }
        return submodel;
    }

    private void addUserPromptText(List<VariableOldFormat> variables, String userPromptText) {
        var lastVariable = variables.get(variables.size() - 1);
        lastVariable.userPromptText = userPromptText;
    }

    private VariableOldFormat readVariable(BufferedReader bufferedReader, String line) {
        var variable = new VariableOldFormat();
        variable.name = toPair(line).getValue();
        variable.kind = extractValue(bufferedReader);
        variable.type = extractValue(bufferedReader);
        variable.value = extractValue(bufferedReader);
        return variable;
    }

    private FunctionOldFormat readFunction(BufferedReader bufferedReader, String line) {
        var function = new FunctionOldFormat();
        function.name = toPair(line).getValue();
        function.kind = extractValue(bufferedReader);
        function.returnType = extractValue(bufferedReader);
        function.body = readBlockInclusive(bufferedReader, "End of Body.");
        return function;
    }

    private String readBlockInclusive(BufferedReader bufferedReader, String endLine) {
        var firstLine = extractValue(bufferedReader);
        var rest = readBlock(bufferedReader, endLine);
        if (rest.isEmpty()) {
            return firstLine;
        } else {
            return String.format("%s%n%s", firstLine, rest);
        }
    }

    private String readBlockExclusive(BufferedReader bufferedReader, String endLine) {
        discardLine(bufferedReader);
        return readBlock(bufferedReader, endLine);
    }

    private String readBlock(BufferedReader bufferedReader, String endLine) {
        var stringBuilder = new StringBuilder();
        boolean firstLine = true;
        do {
            String line = readLine(bufferedReader);

            if (line.equals(endLine)) {
                break;
            }

            if (!firstLine) {
                stringBuilder.append(System.lineSeparator());
            }

            stringBuilder.append(String.format("%s", line));
            firstLine = false;
        } while (true);
        return stringBuilder.toString();
    }

    private PlaceOldFormat readPlace(BufferedReader bufferedReader) {
        var place = new PlaceOldFormat();
        place.name = extractValue(bufferedReader);
        place.token = extractValue(bufferedReader);
        place.fluid = extractBoolean(bufferedReader);
        if (place.fluid) {
            throw new UnsupportedOperationException("Fluid places are currently not supported!");
        }
        place.xy = extractXY(bufferedReader);
        place.numberOfConnectedObjects = extractInt(bufferedReader);
        place.arcReferences = readArcReferences(bufferedReader, place.numberOfConnectedObjects);
        place.vInputArc = extractArcsNames(bufferedReader);
        place.vOutputArc = extractArcsNames(bufferedReader);
        place.label = readLabel(bufferedReader);
        return place;
    }

    private ImmediateTransitionOldFormat readImmediate(BufferedReader bufferedReader) {
        var immediate = new ImmediateTransitionOldFormat();
        immediate.name = extractValue(bufferedReader);
        readTransitionDimensionsAndXY(bufferedReader, immediate);
        immediate.guard = extractValue(bufferedReader);
        immediate.probability = extractValue(bufferedReader);
        immediate.choiceInput = extractValue(bufferedReader);
        immediate.numberOfConnectedObjects = extractInt(bufferedReader);
        immediate.arcReferences = readArcReferences(bufferedReader, immediate.numberOfConnectedObjects);
        immediate.vInputArc = extractArcsNames(bufferedReader);
        immediate.vOutputArc = extractArcsNames(bufferedReader);
        immediate.typeTransition = extractValue(bufferedReader);
        immediate.placeDependent = extractValue(bufferedReader);
        immediate.valueTransition = extractIntOrCatch(bufferedReader,
                String.format("Invalid value for priority (only integer numbers allowed) for immediate transition %s!", immediate.name),
                0);
        immediate.label = readLabel(bufferedReader);
        return immediate;
    }

    private TimedTransitionOldFormat readTimed(BufferedReader bufferedReader) {
        var timed = new TimedTransitionOldFormat();
        timed.name = extractValue(bufferedReader);
        readTransitionDimensionsAndXY(bufferedReader, timed);
        timed.numberOfConnectedObjects = extractInt(bufferedReader);
        timed.arcReferences = readArcReferences(bufferedReader, timed.numberOfConnectedObjects);
        timed.vInputArc = extractArcsNames(bufferedReader);
        timed.vOutputArc = extractArcsNames(bufferedReader);
        timed.typeTransition = extractValue(bufferedReader);
        timed.placeDependent = extractValue(bufferedReader);
        timed.valueTransition = extractValue(bufferedReader);
        timed.value1Transition = extractValue(bufferedReader);
        timed.value2Transition = extractValue(bufferedReader);
        timed.value3Transition = extractValue(bufferedReader);
        timed.label = readLabel(bufferedReader);
        timed.guard = extractValue(bufferedReader);
        timed.policy = extractValue(bufferedReader);
        timed.affected = extractValue(bufferedReader);
        timed.priority = extractValue(bufferedReader);
        timed.choiceInput = extractValue(bufferedReader);
        timed.distribution = extractValue(bufferedReader);
        return timed;
    }

    private void readTransitionDimensionsAndXY(BufferedReader bufferedReader, TransitionOldFormat oldTransition) {
        var line = readLine(bufferedReader);
        var pair = toPair(line);
        if (pair.getKey().equals("Width")) {
            oldTransition.width = Integer.parseInt(pair.getValue());
            oldTransition.height = extractInt(bufferedReader);
            oldTransition.xy = extractXY(bufferedReader);
        } else {
            oldTransition.xy = extractXY(pair.getValue());
        }
    }

    private ArcOldFormat readArc(BufferedReader bufferedReader) {
        var arc = new ArcOldFormat();
        arc.name = extractValue(bufferedReader);
        arc.twoXY = extractTwoXY(bufferedReader);
        arc.type = extractValue(bufferedReader);
        arc.multiplicity = extractValue(bufferedReader);
        arc.src = extractValue(bufferedReader);
        arc.dest = extractValue(bufferedReader);
        arc.points = extractArcPoints(bufferedReader);
        arc.isFluid = extractBoolean(bufferedReader);
        if (arc.isFluid) {
            throw new UnsupportedOperationException("Fluid arcs are currently not supported!");
        }
        arc.choiceInput = extractValue(bufferedReader);
        if (arc.type.equals("Regular")) {
            arc.typeIO = extractValue(bufferedReader);
        } else if (arc.type.equals("Inhibitor")) {
            arc.circles = extractCircles(bufferedReader);
        } else {
            throw new IllegalStateException("Unknown arc type");
        }
        arc.isFlushing = tryParseBoolean(bufferedReader);
        return arc;
    }

    private boolean tryParseBoolean(BufferedReader bufferedReader) {
        var line = readLine(bufferedReader);
        if (line.isBlank()) {
            return false;
        }
        return Boolean.parseBoolean(extractValue(line));
    }

    private List<String> extractArcsNames(BufferedReader bufferedReader) {
        var value = extractValue(bufferedReader);
        if (value.equals("null")) {
            return Collections.emptyList();
        }

        value = value.substring(1, value.length() - 1);
        var arcsNames = value.split(", ");
        return Arrays.asList(arcsNames);
    }

    private Circles extractCircles(BufferedReader bufferedReader) {
        var circles = new Circles();
        var value = extractValue(bufferedReader);
        var tokens = value.split(",");
        circles.circle1 = Integer.parseInt(tokens[0]);
        circles.circle2 = Integer.parseInt(tokens[1]);
        return circles;
    }

    private List<XY> extractArcPoints(BufferedReader bufferedReader) {
        var arcPoints = new ArrayList<XY>();
        var value = extractValue(bufferedReader);
        var tokens = value.substring(1, value.length() - 1).split(", ");
        for (var token : tokens) {
            var xyString = token.split("\\[")[1];
            xyString = xyString.substring(0, xyString.length() - 1);
            var xyTokens = xyString.split(",");
            var xy = new XY();
            xy.x = Integer.parseInt(xyTokens[0].split("=")[1]);
            xy.y = Integer.parseInt(xyTokens[1].split("=")[1]);
            arcPoints.add(xy);
        }
        return arcPoints;
    }

    private TwoXY extractTwoXY(BufferedReader bufferedReader) {
        var value = extractValue(bufferedReader);
        var tokens = value.split(",");
        var twoXY = new TwoXY();
        twoXY.p1.x = Integer.parseInt(tokens[0]);
        twoXY.p1.y = Integer.parseInt(tokens[1]);
        twoXY.p2.x = Integer.parseInt(tokens[2]);
        twoXY.p2.y = Integer.parseInt(tokens[3]);
        return twoXY;
    }

    private List<ArcOldFormatReference> readArcReferences(BufferedReader bufferedReader, int numberOfObjects) {
        var arcReferences = new ArrayList<ArcOldFormatReference>();
        for (int i = 0; i < numberOfObjects; i++) {
            arcReferences.add(readArcReference(bufferedReader));
        }
        return arcReferences;
    }

    private Double extractDouble(BufferedReader bufferedReader) {
        var value = extractValue(bufferedReader);
        if (value.equals("null")) {
            return null;
        } else {
            return Double.parseDouble(value);
        }
    }

    private XY extractXY(BufferedReader bufferedReader) {
        return extractXY(extractValue(bufferedReader));
    }

    private XY extractXY(String value) {
        var tokens = value.split(",");
        var xy = new XY();
        xy.x = Integer.parseInt(tokens[0]);
        xy.y = Integer.parseInt(tokens[1]);
        return xy;
    }

    private int extractIntOrCatch(BufferedReader bufferedReader, String message, int defaultValue) {
        try {
            return extractInt(bufferedReader);
        } catch (NumberFormatException numberFormatException) {
            System.out.println(String.format("Error: %s Using default value %s instead!", message, defaultValue));
            return defaultValue;
        }
    }

    private int extractInt(BufferedReader bufferedReader) {
        return Integer.parseInt(extractValue(bufferedReader));
    }

    private boolean extractBoolean(BufferedReader bufferedReader) {
        return Boolean.parseBoolean(extractValue(bufferedReader));
    }

    private ArcOldFormatReference readArcReference(BufferedReader bufferedReader) {
        var arcReference = new ArcOldFormatReference();
        arcReference.dest = extractValue(bufferedReader);
        arcReference.arc = extractValue(bufferedReader);
        return arcReference;
    }

    private LabelOldFormat readLabel(BufferedReader bufferedReader) {
        var label = new LabelOldFormat();
        discardLine(bufferedReader);
        label.xy = extractXY(bufferedReader);
        label.widthHeight = extractWidthHeight(bufferedReader);
        label.textwidth = extractInt(bufferedReader);
        return label;
    }

    private WidthHeight extractWidthHeight(BufferedReader bufferedReader) {
        var value = extractValue(bufferedReader);
        var tokens = value.split(",");
        var widthHeight = new WidthHeight();
        widthHeight.width = Integer.parseInt(tokens[0]);
        widthHeight.height = Integer.parseInt(tokens[1]);
        return widthHeight;
    }

    private void discardLine(BufferedReader bufferedReader) {
        readLine(bufferedReader);
    }

    private ProjectOldFormat loadOldFormatProject(String projectFilepath) {
        var project = new ProjectOldFormat();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(projectFilepath))) {
            project.modelName = extractValue(bufferedReader);
            project.owner = extractValue(bufferedReader);
            project.dateCreated = extractValue(bufferedReader);
            project.comment = readBlockExclusive(bufferedReader, "CommentEnd");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                var entry = toPair(line);
                if (entry.getKey().equals("SubModel")) {
                    project.submodelsNames.add(entry.getValue());
                }
            }
        } catch (Exception exception) {
            System.err.println(String.format("Could not load project %s in old format due to error:", projectFilepath));
            exception.printStackTrace();
            return null;
        }
        return project;
    }

    private String extractValue(BufferedReader bufferedReader) {
        return extractValue(readLine(bufferedReader));
    }

    private String extractValue(String line) {
        return toPair(line).getValue();
    }

    private String readLine(BufferedReader bufferedReader) {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    Entry<String, String> toPair(String line) {
        var tokens = line.split(":", 2);
        var key = tokens[0];
        var value = tokens[1];
        return Map.entry(key.stripTrailing(), value.stripLeading());
    }

}
