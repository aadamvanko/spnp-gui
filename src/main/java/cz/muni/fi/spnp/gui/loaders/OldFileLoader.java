package cz.muni.fi.spnp.gui.loaders;

import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;

class ProjectOldFormat {
    public String modelName;
    public String owner;
    public String dateCreated;
    public String comment;
    public List<String> submodelsNames;

    public ProjectOldFormat() {
        submodelsNames = new ArrayList<>();
    }
}

class XY {
    public int x;
    public int y;
}

class TwoXY {
    public XY p1;
    public XY p2;

    public TwoXY() {
        p1 = new XY();
        p2 = new XY();
    }
}

class WidthHeight {
    public int width;
    public int height;
}

class ElementOldFormat {
    public String name;
}

class ConnectableOldFormat extends ElementOldFormat {
    public int numberOfConnectedObjects;
    public List<ArcOldFormatReference> arcReferences;
    public List<String> vInputArc;
    public List<String> vOutputArc;
    public XY xy;
    public LabelOldFormat label;

    public ConnectableOldFormat() {
        arcReferences = new ArrayList<>();
    }
}

class FunctionOldFormat {
    public String name;
    public String kind;
    public String returnType;
    public String body;
}

class SubmodelOldFormat {
    public String name;
    public String includes;
    public String defines;
    public List<ElementOldFormat> elements;
    public List<FunctionOldFormat> functions;

    public SubmodelOldFormat() {
        elements = new ArrayList<>();
        functions = new ArrayList<>();
    }
}

class ArcOldFormatReference {
    public String dest;
    public String arc;
}

class LabelOldFormat {
    public XY xy;
    public WidthHeight widthHeight;
    public String textwidth;
}

class PlaceOldFormat extends ConnectableOldFormat {
    public int token;
    public boolean fluid;
}

class TransitionOldFormat extends ConnectableOldFormat {
}

class ImmediateTransitionOldFormat extends TransitionOldFormat {
    public int width;
    public int height;
    public String guard;
    public String probability;
    public String choiceInput;
    public String typeTransition;
    public String placeDependent;
    public double valueTransition;
}

class TimedTransitionOldFormat extends ConnectableOldFormat {
    // TODO
}

class Circles {
    public int circle1;
    public int circle2;
}

class ArcOldFormat extends ElementOldFormat {
    public TwoXY twoXY;
    public String type;
    public int multiplicity;
    public String src;
    public String dest;
    public List<XY> points;
    public boolean isFluid;
    public String choiceInput;

    public Circles circles;
    public String typeIO;
}

public class OldFileLoader {
    private final Notifications notificiations;
    private final SubmodelConverter submodelConverter;

    public OldFileLoader(Notifications notifications) {
        this.notificiations = notifications;
        this.submodelConverter = new SubmodelConverter(notifications);
    }

    public ProjectViewModel loadProject(String projectFilepath) {
        var oldProject = loadOldFormatProject(projectFilepath);
        var projectViewModel = loadProject(projectFilepath, oldProject);
        return projectViewModel;
    }

    private ProjectViewModel loadProject(String projectFilepath, ProjectOldFormat oldProject) {
        var project = new ProjectViewModel(notificiations, oldProject.modelName);
        for (var submodelName : oldProject.submodelsNames) {
            var projectFolder = Path.of(projectFilepath).getParent();
            var submodelFilename = String.format("%s_%s.srn", oldProject.modelName, submodelName);
            var submodelFilepath = Path.of(projectFolder.toString(), submodelFilename);
            var submodel = loadSubmodel(submodelFilepath);
            submodel.name = submodelName;
            var diagram = submodelConverter.convert(submodel, project);
            project.addDiagram(diagram);
        }
        return project;
    }

    private SubmodelOldFormat loadSubmodel(Path submodelFilepath) {
        var submodel = new SubmodelOldFormat();
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
                } else if (line.equals("Arc:")) {
                    submodel.elements.add(readArc(bufferedReader));
                } else if (line.startsWith("Function:")) {
                    submodel.functions.add(readFunction(bufferedReader, line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return submodel;
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
        return String.format("%s%n%s", extractValue(bufferedReader), readBlock(bufferedReader, endLine));
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
        place.token = extractInt(bufferedReader);
        place.fluid = extractBoolean(bufferedReader);
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
        immediate.width = extractInt(bufferedReader);
        immediate.height = extractInt(bufferedReader);
        immediate.xy = extractXY(bufferedReader);
        immediate.guard = extractValue(bufferedReader);
        immediate.probability = extractValue(bufferedReader);
        immediate.choiceInput = extractValue(bufferedReader);
        immediate.numberOfConnectedObjects = extractInt(bufferedReader);
        immediate.arcReferences = readArcReferences(bufferedReader, immediate.numberOfConnectedObjects);
        immediate.vInputArc = extractArcsNames(bufferedReader);
        immediate.vOutputArc = extractArcsNames(bufferedReader);
        immediate.typeTransition = extractValue(bufferedReader);
        immediate.placeDependent = extractValue(bufferedReader);
        immediate.valueTransition = extractDouble(bufferedReader);
        immediate.label = readLabel(bufferedReader);
        return immediate;
    }

    private ArcOldFormat readArc(BufferedReader bufferedReader) {
        var arc = new ArcOldFormat();
        arc.name = extractValue(bufferedReader);
        arc.twoXY = extractTwoXY(bufferedReader);
        arc.type = extractValue(bufferedReader);
        arc.multiplicity = extractInt(bufferedReader);
        arc.src = extractValue(bufferedReader);
        arc.dest = extractValue(bufferedReader);
        arc.points = extractArcPoints(bufferedReader);
        arc.isFluid = extractBoolean(bufferedReader);
        arc.choiceInput = extractValue(bufferedReader);
        if (arc.type.equals("Regular")) {
            arc.typeIO = extractValue(bufferedReader);
        } else if (arc.type.equals("Inhibitor")) {
            arc.circles = extractCircles(bufferedReader);
        } else {
            throw new IllegalStateException("Unknown arc type");
        }
        return arc;
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

    private double extractDouble(BufferedReader bufferedReader) {
        return Double.parseDouble(extractValue(bufferedReader));
    }

    private XY extractXY(BufferedReader bufferedReader) {
        var value = extractValue(bufferedReader);
        var tokens = value.split(",");
        var xy = new XY();
        xy.x = Integer.parseInt(tokens[0]);
        xy.y = Integer.parseInt(tokens[1]);
        return xy;
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
        label.textwidth = extractValue(bufferedReader);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return project;
    }

    private String extractValue(BufferedReader bufferedReader) {
        return toPair(readLine(bufferedReader)).getValue();
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
