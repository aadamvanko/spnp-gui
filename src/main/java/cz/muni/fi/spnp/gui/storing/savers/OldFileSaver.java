package cz.muni.fi.spnp.gui.storing.savers;

import cz.muni.fi.spnp.gui.storing.oldmodels.*;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static cz.muni.fi.spnp.gui.storing.OldFormatUtils.NULL_VALUE;

public class OldFileSaver {

    private final ProjectConverter projectConverter;
    private final ViewModelToOldFormatConverter viewModelToOldFormatConverter;

    public OldFileSaver() {
        this.projectConverter = new ProjectConverter();
        this.viewModelToOldFormatConverter = new ViewModelToOldFormatConverter();
    }

    public void saveProject(String directoryPath, ProjectViewModel project) {
        var directory = Path.of(directoryPath);
        writeProject(directory, project);
        saveSubmodels(directory, project);
    }

    private void writeProject(Path directory, ProjectViewModel project) {
        var projectFile = Path.of(directory.toString(), String.format("%s.rgl", project.getName()));
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(projectFile)) {
            var oldProject = projectConverter.convert(project);
            writeProjectInfo(oldProject, bufferedWriter);
            writeSubmodelsNames(oldProject, bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSubmodelsNames(ProjectOldFormat oldProject, BufferedWriter bufferedWriter) {
        for (int i = 0; i < oldProject.submodelsNames.size(); i++) {
            writeln(bufferedWriter, System.lineSeparator());
            writelnPairExtraSpace(bufferedWriter, "SubModel", oldProject.submodelsNames.get(i));
            writelnPairExtraSpace(bufferedWriter, "Type", "SRN Model");
        }
    }

    private void saveSubmodels(Path directory, ProjectViewModel project) {
        for (var diagram : project.getDiagrams()) {
            var diagramFile = Path.of(directory.toString(), String.format("%s_%s.srn", project.getName(), diagram.getName()));
            try (var bufferedWriter = Files.newBufferedWriter(diagramFile)) {
                saveSubmodel(diagram, bufferedWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveSubmodel(DiagramViewModel diagramViewModel, BufferedWriter bufferedWriter) {
        var submodel = viewModelToOldFormatConverter.convert(diagramViewModel);
        writeln(bufferedWriter, "Type: SRN Model");
        writeln(bufferedWriter, "");
        writeIncludes(submodel, bufferedWriter);
        writeln(bufferedWriter, "");
        writeDefines(submodel, bufferedWriter);
        writeSubmodelInfo(submodel, bufferedWriter);
        writeln(bufferedWriter, "");
        writeElements(submodel, bufferedWriter);
        writeln(bufferedWriter, "");
        writeVariables(submodel, bufferedWriter);
        writeFunctions(submodel, bufferedWriter);
    }

    private void writeIncludes(Submodel submodel, BufferedWriter bufferedWriter) {
        if (submodel.includes.isEmpty()) {
            return;
        }
        writeln(bufferedWriter, "Include:");
        writeln(bufferedWriter, submodel.includes);
        writeln(bufferedWriter, "IncludeEnd");
    }

    private void writeDefines(Submodel submodel, BufferedWriter bufferedWriter) {
        if (submodel.defines.isEmpty()) {
            return;
        }
        writeln(bufferedWriter, "Define:");
        writeln(bufferedWriter, submodel.defines);
        writeln(bufferedWriter, "DefineEnd");
    }

    private void writeSubmodelInfo(Submodel submodel, BufferedWriter bufferedWriter) {
        writeln(bufferedWriter, "SubModel:");
        writelnPair(bufferedWriter, "Name", submodel.name);
        writelnPair(bufferedWriter, "Number of places", String.valueOf(countElements(PlaceOldFormat.class, submodel.elements)));
        writelnPair(bufferedWriter, "Number of immediate trans", String.valueOf(countElements(ImmediateTransitionOldFormat.class, submodel.elements)));
        writelnPair(bufferedWriter, "Number of timed trans", String.valueOf(countElements(TimedTransitionOldFormat.class, submodel.elements)));
        writelnPair(bufferedWriter, "Number of inf trans", String.valueOf(0)); // not supported
        writelnPair(bufferedWriter, "Number of arcs", String.valueOf(countElements(ArcOldFormat.class, submodel.elements)));
        writelnPair(bufferedWriter, "Token displayed", String.valueOf(false));
    }

    private <T> long countElements(Class<T> targetClass, List<ElementOldFormat> oldElements) {
        return oldElements.stream()
                .filter(targetClass::isInstance)
                .count();
    }

    private void writeElements(Submodel submodel, BufferedWriter bufferedWriter) {
        for (int i = 0; i < submodel.elements.size(); i++) {
            writeElement(submodel.elements.get(i), bufferedWriter);
            if (i < submodel.elements.size() - 1) {
                writeln(bufferedWriter, "");
            }
        }
    }

    private void writeElement(ElementOldFormat elementOldFormat, BufferedWriter bufferedWriter) {
        if (elementOldFormat instanceof PlaceOldFormat) {
            writePlace((PlaceOldFormat) elementOldFormat, bufferedWriter);
        } else if (elementOldFormat instanceof ImmediateTransitionOldFormat) {
            writeImmediateTransition((ImmediateTransitionOldFormat) elementOldFormat, bufferedWriter);
        } else if (elementOldFormat instanceof TimedTransitionOldFormat) {
            writeTimedTransition((TimedTransitionOldFormat) elementOldFormat, bufferedWriter);
        } else if (elementOldFormat instanceof ArcOldFormat) {
            writeArc((ArcOldFormat) elementOldFormat, bufferedWriter);
        } else {
            throw new AssertionError("Unknown old format element " + elementOldFormat);
        }
    }

    /*
    Place:
Name: P0
Token: 0
Fluid: false
X, Y: 164,164
Number of connected objects: 2
Dest: T0
Arc:arc0
Dest: t0
Arc:arc1
vInputArc: null
vOutputArc: [arc0, arc1]
Place label:
X, Y: 173,196
Width, Height: 20,20
Textwidth: 0
    */

    private void writePlace(PlaceOldFormat oldPlace, BufferedWriter bufferedWriter) {
        writeln(bufferedWriter, "Place:");
        writelnPair(bufferedWriter, "Name", oldPlace.name);
        writelnPair(bufferedWriter, "Token", oldPlace.token);
        writelnPair(bufferedWriter, "Fluid", String.valueOf(oldPlace.fluid));
        writeXY(bufferedWriter, oldPlace.xy);
        writelnPair(bufferedWriter, "Number of connected objects", String.valueOf(oldPlace.numberOfConnectedObjects));
        writeArcReferences(bufferedWriter, oldPlace.arcReferences);
        writeVInputArc(bufferedWriter, oldPlace.vInputArc);
        writeVOutputArc(bufferedWriter, oldPlace.vOutputArc);
        writeln(bufferedWriter, "Place label:");
        writeLabel(bufferedWriter, oldPlace.label);
    }

    private void writeXY(BufferedWriter bufferedWriter, XY xy) {
        writelnPair(bufferedWriter, "X, Y", String.format("%d,%d", xy.x, xy.y));
    }

    private void writeArcReferences(BufferedWriter bufferedWriter, List<ArcOldFormatReference> arcReferences) {
        arcReferences.forEach(arcOldFormatReference -> writeArcReference(bufferedWriter, arcOldFormatReference));
    }

    private void writeArcReference(BufferedWriter bufferedWriter, ArcOldFormatReference arcOldFormatReference) {
        writelnPair(bufferedWriter, "Dest", arcOldFormatReference.dest);
        writelnPair(bufferedWriter, "Arc", arcOldFormatReference.arc);
    }

    private void writeVInputArc(BufferedWriter bufferedWriter, List<String> vInputArc) {
        writelnPair(bufferedWriter, "vInputArc", joinArcs(vInputArc));
    }

    private String joinArcs(List<String> vInputArc) {
        if (vInputArc.isEmpty()) {
            return NULL_VALUE;
        }
        return String.format("[%s]", String.join(", ", vInputArc));
    }

    private void writeVOutputArc(BufferedWriter bufferedWriter, List<String> vOutputArc) {
        writelnPair(bufferedWriter, "vOutputArc", joinArcs(vOutputArc));
    }

    private void writeLabel(BufferedWriter bufferedWriter, LabelOldFormat label) {
        writeXY(bufferedWriter, label.xy);
        writeWidthHeight(bufferedWriter, label.widthHeight);
        writelnPair(bufferedWriter, "Textwidth", String.valueOf(label.textwidth));
    }

    private void writeWidthHeight(BufferedWriter bufferedWriter, WidthHeight widthHeight) {
        writelnPair(bufferedWriter, "Width, Height", String.format("%d,%d", widthHeight.width, widthHeight.height));
    }

    private void writeImmediateTransition(ImmediateTransitionOldFormat oldImmediate, BufferedWriter bufferedWriter) {
//        oldImmediate.name = extractValue(bufferedReader);
//        readTransitionDimensionsAndXY(bufferedReader, oldImmediate);
//        oldImmediate.guard = extractValue(bufferedReader);
//        oldImmediate.probability = extractValue(bufferedReader);
//        oldImmediate.choiceInput = extractValue(bufferedReader);
//        oldImmediate.numberOfConnectedObjects = extractInt(bufferedReader);
//        oldImmediate.arcReferences = readArcReferences(bufferedReader, oldImmediate.numberOfConnectedObjects);
//        oldImmediate.vInputArc = extractArcsNames(bufferedReader);
//        oldImmediate.vOutputArc = extractArcsNames(bufferedReader);
//        oldImmediate.typeTransition = extractValue(bufferedReader);
//        oldImmediate.placeDependent = extractValue(bufferedReader);
//        oldImmediate.valueTransition = extractInt(bufferedReader);
//        oldImmediate.label = readLabel(bufferedReader);
//
        writeln(bufferedWriter, "Immediate:");
        writelnPair(bufferedWriter, "Name", oldImmediate.name);
        writeDimensions(bufferedWriter, oldImmediate);
        writeXY(bufferedWriter, oldImmediate.xy);
        writelnPair(bufferedWriter, "Guard", oldImmediate.guard);
        writelnPair(bufferedWriter, "Probability", oldImmediate.probability);
        writelnPair(bufferedWriter, "Choice Input", oldImmediate.choiceInput);
        writelnPair(bufferedWriter, "Number of connected objects", String.valueOf(oldImmediate.numberOfConnectedObjects));
        writeArcReferences(bufferedWriter, oldImmediate.arcReferences);
        writeVInputArc(bufferedWriter, oldImmediate.vInputArc);
        writeVOutputArc(bufferedWriter, oldImmediate.vOutputArc);
        writelnPair(bufferedWriter, "Type Transition", oldImmediate.typeTransition);
        writelnPair(bufferedWriter, "Place Dependent", oldImmediate.placeDependent);
        writelnPair(bufferedWriter, "Value Transition", String.valueOf(oldImmediate.valueTransition));
        writeln(bufferedWriter, "Transition label:");
        writeLabel(bufferedWriter, oldImmediate.label);
    }

    private void writeTimedTransition(TimedTransitionOldFormat oldTimed, BufferedWriter bufferedWriter) {
//        timed.name = extractValue(bufferedReader);
//        readTransitionDimensionsAndXY(bufferedReader, timed);
//        timed.numberOfConnectedObjects = extractInt(bufferedReader);
//        timed.arcReferences = readArcReferences(bufferedReader, timed.numberOfConnectedObjects);
//        timed.vInputArc = extractArcsNames(bufferedReader);
//        timed.vOutputArc = extractArcsNames(bufferedReader);
//        timed.typeTransition = extractValue(bufferedReader);
//        timed.placeDependent = extractValue(bufferedReader);
//        timed.valueTransition = extractValue(bufferedReader);
//        timed.value1Transition = extractValue(bufferedReader);
//        timed.value2Transition = extractValue(bufferedReader);
//        timed.value3Transition = extractValue(bufferedReader);
//        timed.label = readLabel(bufferedReader);
//        timed.guard = extractValue(bufferedReader);
//        timed.policy = extractValue(bufferedReader);
//        timed.affected = extractValue(bufferedReader);
//        timed.priority = extractValue(bufferedReader);
//        timed.choiceInput = extractValue(bufferedReader);
//        timed.distribution = extractValue(bufferedReader);
        writeln(bufferedWriter, "Timed:");
        writelnPair(bufferedWriter, "Name", oldTimed.name);
        writeDimensions(bufferedWriter, oldTimed);
        writeXY(bufferedWriter, oldTimed.xy);
        writelnPair(bufferedWriter, "Number of connected objects", String.valueOf(oldTimed.numberOfConnectedObjects));
        writeArcReferences(bufferedWriter, oldTimed.arcReferences);
        writeVInputArc(bufferedWriter, oldTimed.vInputArc);
        writeVOutputArc(bufferedWriter, oldTimed.vOutputArc);
        writelnPair(bufferedWriter, "Type Transition", oldTimed.typeTransition);
        writelnPair(bufferedWriter, "Place Dependent", oldTimed.placeDependent);
        writelnPair(bufferedWriter, "Value Transition", oldTimed.valueTransition);
        writelnPair(bufferedWriter, "Value1 Transition", oldTimed.value1Transition);
        writelnPair(bufferedWriter, "Value2 Transition", oldTimed.value2Transition);
        writelnPair(bufferedWriter, "Value3 Transition", oldTimed.value3Transition);
        writeln(bufferedWriter, "Transition label:");
        writeLabel(bufferedWriter, oldTimed.label);
        writelnPair(bufferedWriter, "Guard", oldTimed.guard);
        writelnPair(bufferedWriter, "Policy", oldTimed.policy);
        writelnPair(bufferedWriter, "Affected", oldTimed.affected);
        writelnPair(bufferedWriter, "Priority", oldTimed.priority);
        writelnPair(bufferedWriter, "Choice Input", oldTimed.choiceInput);
        writelnPair(bufferedWriter, "Distribution", oldTimed.distribution);
    }

    private void writeDimensions(BufferedWriter bufferedWriter, TransitionOldFormat oldTransition) {
        writelnPair(bufferedWriter, "Width", String.valueOf(oldTransition.width));
        writelnPair(bufferedWriter, "Height", String.valueOf(oldTransition.height));
    }

    private void writeArc(ArcOldFormat oldArc, BufferedWriter bufferedWriter) {
//        public TwoXY twoXY;
//        public String type;
//        public String multiplicity;
//        public String src;
//        public String dest;
//        public List<XY> points;
//        public boolean isFluid;
//        public String choiceInput;
//        public Circles circles;
//        public String typeIO;
//
        writeln(bufferedWriter, "Arc:");
        writelnPair(bufferedWriter, "Name", oldArc.name);
        writeTwoXY(bufferedWriter, oldArc.twoXY);
        writelnPair(bufferedWriter, "Type", oldArc.type);
        writelnPair(bufferedWriter, "Multiplicity", oldArc.multiplicity);
        writelnPair(bufferedWriter, "Src", oldArc.src);
        writelnPair(bufferedWriter, "Dest", oldArc.dest);
        writeArcPoints(bufferedWriter, oldArc);
        writelnPair(bufferedWriter, "Fluid Arc", String.valueOf(oldArc.isFluid));
        writelnPair(bufferedWriter, "Choice Input", oldArc.choiceInput);
        if (oldArc.type.equals("Regular")) {
            writelnPair(bufferedWriter, "TypeIO", oldArc.typeIO);
        } else { // Inhibitor
            writeCircles(bufferedWriter, oldArc.circles);
        }
        writelnPair(bufferedWriter, "IsFlushing", String.valueOf(oldArc.isFlushing));
    }

    private void writeCircles(BufferedWriter bufferedWriter, Circles circles) {
        writelnPair(bufferedWriter, "Circle1, Circle2", String.format("%d,%d", circles.circle1, circles.circle2));
    }

    private void writeArcPoints(BufferedWriter bufferedWriter, ArcOldFormat oldArc) {
        writelnPair(bufferedWriter, "ArcPoints", String.format("[%s]", joinArcPoints(oldArc)));
    }

    private String joinArcPoints(ArcOldFormat oldArc) {
        var formattedPoints = oldArc.points.stream()
                .map(xy -> String.format("java.awt.Point[x=%d,y=%d]", xy.x, xy.y))
                .collect(Collectors.toList());
        return String.join(", ", formattedPoints);
    }

    private void writeTwoXY(BufferedWriter bufferedWriter, TwoXY twoXY) {
        writelnPair(bufferedWriter, "X1, Y1, X2, Y2", String.format("%d,%d,%d,%d", twoXY.p1.x, twoXY.p1.y, twoXY.p2.x, twoXY.p2.y));
    }

    private void writeVariables(Submodel submodel, BufferedWriter bufferedWriter) {
        submodel.variables.forEach(variableOldFormat -> writeVariable(variableOldFormat, bufferedWriter));
    }

    private void writeVariable(VariableOldFormat variableOldFormat, BufferedWriter bufferedWriter) {
        writelnPair(bufferedWriter, "Variable", variableOldFormat.name);
        writelnPair(bufferedWriter, "Kind", variableOldFormat.kind);
        writelnPair(bufferedWriter, "Type", variableOldFormat.type);
        writelnPair(bufferedWriter, "Value", variableOldFormat.value);
        if (variableOldFormat.userPromptText != null) {
            writelnPair(bufferedWriter, "UserPromptText", variableOldFormat.userPromptText);
        }
    }

    private void writeFunctions(Submodel submodel, BufferedWriter bufferedWriter) {
        submodel.functions.forEach(functionOldFormat -> writeFunction(functionOldFormat, bufferedWriter));
    }

    private void writeFunction(FunctionOldFormat functionOldFormat, BufferedWriter bufferedWriter) {
        writelnPair(bufferedWriter, "Function", functionOldFormat.name);
        writelnPair(bufferedWriter, "Kind", functionOldFormat.kind);
        writelnPair(bufferedWriter, "Type", functionOldFormat.returnType);
        writeBlock(bufferedWriter, "Body", functionOldFormat.body, "End of Body.");
    }

    private void writeProjectInfo(ProjectOldFormat project, BufferedWriter bufferedWriter) {
        writelnPairExtraSpace(bufferedWriter, "Model Name", project.modelName);
        writelnPairExtraSpace(bufferedWriter, "Owner", project.owner);
        writelnPairExtraSpace(bufferedWriter, "Date Created", project.dateCreated);
        writeBlockExclusiveExtraSpace(bufferedWriter, "Comment", project.comment, "CommentEnd");
    }

    private void writeBlock(BufferedWriter bufferedWriter, String start, String content, String end) {
        writelnPair(bufferedWriter, start, content);
        writeln(bufferedWriter, end);
    }

    private void writeBlockExclusive(BufferedWriter bufferedWriter, String start, String content, String end) {
        writelnPair(bufferedWriter, start, "");
        writeln(bufferedWriter, content);
        writeln(bufferedWriter, end);
    }

    private void writeBlockExclusiveExtraSpace(BufferedWriter bufferedWriter, String start, String content, String end) {
        writelnPairExtraSpace(bufferedWriter, start, "");
        writeln(bufferedWriter, content);
        writeln(bufferedWriter, end);
    }

    private void writelnPair(BufferedWriter bufferedWriter, String key, String value) {
        writeln(bufferedWriter, String.format("%s: %s", key, value));
    }

    private void writelnPairExtraSpace(BufferedWriter bufferedWriter, String key, String value) {
        writeln(bufferedWriter, String.format("%s : %s", key, value));
    }

    private void writeln(BufferedWriter bufferedWriter, String text) {
        try {
            bufferedWriter.write(String.format("%s%n", text));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
