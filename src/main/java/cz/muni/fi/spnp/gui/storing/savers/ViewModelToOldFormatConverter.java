package cz.muni.fi.spnp.gui.storing.savers;

import cz.muni.fi.spnp.core.models.arcs.ArcDirection;
import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.elements.PolicyAffectedType;
import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import cz.muni.fi.spnp.gui.components.graph.elements.place.PlaceView;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionReturnType;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.storing.OldFormatUtils;
import cz.muni.fi.spnp.gui.storing.oldmodels.*;
import cz.muni.fi.spnp.gui.viewmodel.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedDistributionType;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionOrientation;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cz.muni.fi.spnp.gui.storing.OldFormatUtils.NULL_VALUE;

public class ViewModelToOldFormatConverter {

    public Submodel convert(DiagramViewModel diagram) {
        var submodel = new Submodel();
        submodel.name = diagram.getName();
        submodel.includes = convertIncludes(diagram);
        submodel.defines = convertDefines(diagram);
        submodel.variables = convertAllVariables(diagram);
        submodel.functions = convertFunctions(diagram);
        submodel.elements = convertElements(diagram);
        return submodel;
    }

    private List<VariableOldFormat> convertAllVariables(DiagramViewModel diagram) {
        return Stream.concat(convertVariables(diagram).stream(), convertInputParameters(diagram).stream())
                .collect(Collectors.toList());
    }

    private List<VariableOldFormat> convertVariables(DiagramViewModel diagramViewModel) {
        return diagramViewModel.getVariables().stream()
                .map(this::convertVariable)
                .collect(Collectors.toList());
    }

    private VariableOldFormat convertVariable(VariableViewModel variableViewModel) {
        var oldVariable = new VariableOldFormat();
        oldVariable.name = variableViewModel.getName();
        oldVariable.kind = convertVariableKind(variableViewModel.getKind());
        oldVariable.type = convertVariableType(variableViewModel.getType());
        oldVariable.value = variableViewModel.getValue();
        oldVariable.userPromptText = null;
        return oldVariable;
    }

    private List<VariableOldFormat> convertInputParameters(DiagramViewModel diagramViewModel) {
        return diagramViewModel.getInputParameters().stream()
                .map(this::convertInputParameter)
                .collect(Collectors.toList());
    }

    private VariableOldFormat convertInputParameter(InputParameterViewModel inputParameterViewModel) {
        var oldVariable = new VariableOldFormat();
        oldVariable.name = inputParameterViewModel.getName();
        oldVariable.kind = "Parameter";
        oldVariable.type = convertVariableType(inputParameterViewModel.getType());
        oldVariable.value = "0";
        oldVariable.userPromptText = inputParameterViewModel.getUserPromptText();
        return oldVariable;
    }

    private String convertVariableKind(VariableType variableKind) {
        var variableTypeToString = new HashMap<VariableType, String>();
        variableTypeToString.put(VariableType.Global, "Global");
        variableTypeToString.put(VariableType.Parameter, "Param");
        return variableTypeToString.get(variableKind);
    }

    private String convertVariableType(VariableDataType variableDataType) {
        var variableDataTypeToString = new HashMap<VariableDataType, String>();
        variableDataTypeToString.put(VariableDataType.INT, "int");
        variableDataTypeToString.put(VariableDataType.DOUBLE, "double");
        return variableDataTypeToString.get(variableDataType);
    }

    private List<ElementOldFormat> convertElements(DiagramViewModel diagram) {
        var connectableElements = diagram.getElements().stream()
                .filter(e -> e instanceof ConnectableViewModel)
                .map(e -> (ConnectableViewModel) e)
                .collect(Collectors.toList());

        var arcs = diagram.getElements().stream()
                .filter(e -> e instanceof ArcViewModel)
                .map(e -> (ArcViewModel) e)
                .collect(Collectors.toList());

        var oldElements = convertConnectableElements(connectableElements, arcs);
        oldElements.addAll(convertArcs(arcs, oldElements));
        return oldElements;
    }

    private String convertIncludes(DiagramViewModel diagram) {
        return String.join(System.lineSeparator(), diagram.getIncludes().stream()
                .map(includeViewModel -> String.format("#include %s", includeViewModel.getPath()))
                .collect(Collectors.toList()));
    }

    private List<ElementOldFormat> convertConnectableElements(List<ConnectableViewModel> connectableElements, List<ArcViewModel> arcs) {
        return connectableElements.stream()
                .map(e -> convertConnectableElement(e, arcs))
                .collect(Collectors.toList());
    }

    private ElementOldFormat convertConnectableElement(ConnectableViewModel element, List<ArcViewModel> arcs) {
        if (element instanceof PlaceViewModel) {
            return convertPlace((PlaceViewModel) element, arcs);
        } else if (element instanceof ImmediateTransitionViewModel) {
            return convertImmediateTransition((ImmediateTransitionViewModel) element, arcs);
        } else if (element instanceof TimedTransitionViewModel) {
            return convertTimedTransition((TimedTransitionViewModel) element, arcs);
        } else {
            throw new UnsupportedOperationException("Unknown connectable view model");
        }
    }

    private ElementOldFormat convertPlace(PlaceViewModel place, List<ArcViewModel> arcs) {
        var oldPlace = new PlaceOldFormat();
        oldPlace.name = place.nameProperty().get();
        oldPlace.token = place.numberOfTokensProperty().get();
        oldPlace.fluid = false; // TODO maybe in the future
        var outputArcs = findOutputArcs(place, arcs);
        oldPlace.numberOfConnectedObjects = outputArcs.size();
        oldPlace.arcReferences = convertArcsToReferences(outputArcs);
        var inputArcs = findInputArcs(place, arcs);
        oldPlace.vInputArc = getArcsNames(inputArcs);
        oldPlace.vOutputArc = getArcsNames(outputArcs);
        oldPlace.xy = new XY();
        oldPlace.xy.x = (int) (place.getPositionX() - PlaceView.RADIUS);
        oldPlace.xy.y = (int) (place.getPositionY() - PlaceView.RADIUS);
        oldPlace.label = createLabel(place);
        /*
Place:
Name: place0
Token: 0
Fluid: false
X, Y: 44,44
Number of connected objects: 2
Dest: immediate2PlaceDependant
Arc:arc1
Dest: immediate1Constant
Arc:arc0
vInputArc: null
vOutputArc: [arc0, arc1]
Place label:
X, Y: 43,76
Width, Height: 41,20
Textwidth: 0
         */
        return oldPlace;
    }

    private XY createXYFromPosition(ConnectableViewModel connectableViewModel) {
        var xy = new XY();
        xy.x = (int) connectableViewModel.getPositionX();
        xy.y = (int) connectableViewModel.getPositionY();
        return xy;
    }

    private LabelOldFormat createLabel(ConnectableViewModel element) {
        var label = new LabelOldFormat();
        label.xy = new XY();
        // TODO not important, label does not have separate position in my model
        label.xy.x = (int) element.positionXProperty().get();
        label.xy.y = (int) element.positionYProperty().get();
        label.widthHeight = new WidthHeight();
        label.widthHeight.width = 20;
        label.widthHeight.height = 20;
        label.textwidth = 0;
        return label;
    }

    private List<String> getArcsNames(List<ArcViewModel> arcs) {
        return arcs.stream()
                .map(a -> a.nameProperty().get())
                .collect(Collectors.toList());
    }

    private List<ArcOldFormatReference> convertArcsToReferences(List<ArcViewModel> outArcs) {
        return outArcs.stream()
                .map(a -> {
                    var arcReference = new ArcOldFormatReference();
                    arcReference.arc = a.nameProperty().get();
                    arcReference.dest = a.getToViewModel().nameProperty().get();
                    return arcReference;
                })
                .collect(Collectors.toList());
    }

    private List<ArcViewModel> findInputArcs(ConnectableViewModel to, List<ArcViewModel> arcs) {
        return arcs.stream()
                .filter(a -> a.getToViewModel() == to)
                .collect(Collectors.toList());
    }

    private List<ArcViewModel> findOutputArcs(ConnectableViewModel from, List<ArcViewModel> arcs) {
        return arcs.stream()
                .filter(a -> a.getFromViewModel() == from)
                .collect(Collectors.toList());
    }

    private void convertDimensionsImmediate(ImmediateTransitionOldFormat oldImmediate, ImmediateTransitionViewModel immediate) {
        if (immediate.getOrientation() == TransitionOrientation.Vertical) {
            oldImmediate.width = 5;
            oldImmediate.height = 32;
        } else {
            oldImmediate.width = 32;
            oldImmediate.height = 5;
        }
    }

    private void convertDimensionsTimed(TimedTransitionOldFormat oldTimed, TimedTransitionViewModel timed) {
        if (timed.getOrientation() == TransitionOrientation.Vertical) {
            oldTimed.width = 14;
            oldTimed.height = 32;
        } else {
            oldTimed.width = 32;
            oldTimed.height = 14;
        }
    }

    private void convertXY(TransitionOldFormat oldTransition, TransitionViewModel transition) {
        oldTransition.xy = new XY();
        oldTransition.xy.x = (int) transition.positionXProperty().get();
        oldTransition.xy.y = (int) transition.positionYProperty().get();
    }

    private ElementOldFormat convertImmediateTransition(ImmediateTransitionViewModel immediate, List<ArcViewModel> arcs) {
        var outputArcs = findOutputArcs(immediate, arcs);

        var oldImmediate = new ImmediateTransitionOldFormat();
        oldImmediate.name = immediate.getName();
        convertDimensionsImmediate(oldImmediate, immediate);
        convertXY(oldImmediate, immediate);
        oldImmediate.guard = nameOrNull(immediate.getGuardFunction());
        oldImmediate.probability = convertProbability(immediate.getTransitionProbability());
        oldImmediate.choiceInput = convertImmediateProbabilityToString(immediate.getTransitionProbability().getEnumType());
        oldImmediate.numberOfConnectedObjects = outputArcs.size();
        oldImmediate.arcReferences = convertArcsToReferences(outputArcs);
        oldImmediate.vInputArc = getArcsNames(findInputArcs(immediate, arcs));
        oldImmediate.vOutputArc = getArcsNames(findOutputArcs(immediate, arcs));
        oldImmediate.typeTransition = "Immediate";
        oldImmediate.placeDependent = convertPlaceDependentProbability(immediate.getTransitionProbability());
        oldImmediate.valueTransition = immediate.priorityProperty().get();
        oldImmediate.label = createLabel(immediate);
        return oldImmediate;
    }

    private ElementOldFormat convertTimedTransition(TimedTransitionViewModel timed, List<ArcViewModel> arcs) {
        var outputArcs = findOutputArcs(timed, arcs);

        var oldTimed = new TimedTransitionOldFormat();
        oldTimed.name = timed.getName();
        convertDimensionsTimed(oldTimed, timed);
        convertXY(oldTimed, timed);
        oldTimed.numberOfConnectedObjects = outputArcs.size();
        oldTimed.arcReferences = convertArcsToReferences(outputArcs);
        oldTimed.vInputArc = getArcsNames(findInputArcs(timed, arcs));
        oldTimed.vOutputArc = getArcsNames(findOutputArcs(timed, arcs));
        oldTimed.typeTransition = "Timed";
        oldTimed.placeDependent = nameOrNull(timed.getTransitionDistribution().dependentPlaceProperty().get());
        oldTimed.valueTransition = getValue(timed.getTransitionDistribution(), 0);
        oldTimed.value1Transition = getValue(timed.getTransitionDistribution(), 1);
        oldTimed.value2Transition = getValue(timed.getTransitionDistribution(), 2);
        oldTimed.value3Transition = getValue(timed.getTransitionDistribution(), 3);
        oldTimed.label = createLabel(timed);
        oldTimed.guard = nameOrNull(timed.getGuardFunction());
        oldTimed.policy = convertPolicyAffectedType(timed.getPolicy());
        oldTimed.affected = convertPolicyAffectedType(timed.getAffected());
        oldTimed.priority = String.valueOf(timed.priorityProperty().get());
        oldTimed.choiceInput = convertDistributionType(timed.getTransitionDistribution().distributionTypeProperty().get());
        oldTimed.distribution = convertTimedDistributionType(timed.getTransitionDistribution().getEnumType());
        return oldTimed;
    }

    private String convertPolicyAffectedType(PolicyAffectedType policyAffectedType) {
        var typeToString = new HashMap<PolicyAffectedType, String>();
        typeToString.put(PolicyAffectedType.PreemptiveRepeatDifferent, "Preemptive repeat different");
        typeToString.put(PolicyAffectedType.PreemptiveRepeatIdentical, "Preemptive repeat identical");
        typeToString.put(PolicyAffectedType.PreemptiveResume, "Preemptive resume");
        return typeToString.get(policyAffectedType);
    }

    private String convertTimedDistributionType(TimedDistributionType timedDistributionType) {
        var timedTypeToString = new HashMap<TimedDistributionType, String>();
        timedTypeToString.put(TimedDistributionType.Beta, "Beta");
        timedTypeToString.put(TimedDistributionType.Binomial, "Binomial");
        timedTypeToString.put(TimedDistributionType.Cauchy, "Cauchy");
        timedTypeToString.put(TimedDistributionType.Constant, "Constant");
        timedTypeToString.put(TimedDistributionType.Erlang, "Erlang");
        timedTypeToString.put(TimedDistributionType.Exponential, "Exponential");
        timedTypeToString.put(TimedDistributionType.Gamma, "Gamma");
        timedTypeToString.put(TimedDistributionType.Geometric, "Geometric");
        timedTypeToString.put(TimedDistributionType.HyperExponential, "HyperExponential");
        timedTypeToString.put(TimedDistributionType.HypoExponential, "HypoExponential");
        timedTypeToString.put(TimedDistributionType.LogarithmicNormal, "LogNormal");
        timedTypeToString.put(TimedDistributionType.NegativeBinomial, "Negative binomial");
        timedTypeToString.put(TimedDistributionType.Pareto, "Pareto");
        timedTypeToString.put(TimedDistributionType.Poisson, "Poisson");
        timedTypeToString.put(TimedDistributionType.TruncatedNormal, "Truncated normal");
        timedTypeToString.put(TimedDistributionType.Uniform, "Uniform");
        timedTypeToString.put(TimedDistributionType.Weibull, "Weibull");
        return timedTypeToString.get(timedDistributionType);
    }

    private String convertDistributionType(TransitionDistributionType transitionDistributionType) {
        var distributionTypeToString = new HashMap<TransitionDistributionType, String>();
        distributionTypeToString.put(TransitionDistributionType.Constant, "Constant");
        distributionTypeToString.put(TransitionDistributionType.Functional, "Function");
        distributionTypeToString.put(TransitionDistributionType.PlaceDependent, "Place dependent");
        return distributionTypeToString.get(transitionDistributionType);
    }

    private String getValue(TransitionDistributionViewModel transitionDistributionViewModel, int index) {
        if (index >= transitionDistributionViewModel.getValues().size()) {
            return NULL_VALUE;
        }
        return transitionDistributionViewModel.getValues().get(index).get();
    }

    private String convertProbability(TransitionProbabilityViewModel transitionProbabilityViewModel) {
        switch (transitionProbabilityViewModel.getEnumType()) {
            case Constant:
                var constantProbability = (ConstantTransitionProbabilityViewModel) transitionProbabilityViewModel;
                return String.valueOf(constantProbability.getValue());

            case Functional:
                var functionalProbability = (FunctionalTransitionProbabilityViewModel) transitionProbabilityViewModel;
                return nameOrNull(functionalProbability.getFunction());

            case PlaceDependent:
                var placeDependentProbability = (PlaceDependentTransitionProbabilityViewModel) transitionProbabilityViewModel;
                return String.valueOf(placeDependentProbability.getValue());
        }
        throw new AssertionError("Unknown probability type " + transitionProbabilityViewModel);
    }

    private String convertPlaceDependentProbability(TransitionProbabilityViewModel transitionProbability) {
        if (transitionProbability.getEnumType() == TransitionProbabilityType.PlaceDependent) {
            return nameOrNull(((PlaceDependentTransitionProbabilityViewModel) transitionProbability).getDependentPlace());
        }
        return NULL_VALUE;
    }

    private String nameOrNull(DisplayableViewModel displayableViewModel) {
        if (displayableViewModel == null) {
            return NULL_VALUE;
        }
        return displayableViewModel.getName();
    }

    private String convertImmediateProbabilityToString(TransitionProbabilityType probabilityType) {
        if (probabilityType == TransitionProbabilityType.Constant) {
            return "Constant value";
        }
        return convertProbabilityToString(probabilityType);
    }

    private String convertProbabilityToString(TransitionProbabilityType transitionProbabilityType) {
        Map<TransitionProbabilityType, String> typesToStrings = Map.of(
                TransitionProbabilityType.Constant, "Constant",
                TransitionProbabilityType.PlaceDependent, "Place dependent",
                TransitionProbabilityType.Functional, "Function"
        );
        return typesToStrings.get(transitionProbabilityType);
    }

    private List<ElementOldFormat> convertArcs(List<ArcViewModel> oldArcs, List<ElementOldFormat> oldElements) {
        return oldArcs.stream()
                .map(arcViewModel -> convertArc(arcViewModel, oldElements))
                .collect(Collectors.toList());
    }

    private ArcOldFormat convertArc(ArcViewModel arc, List<ElementOldFormat> oldElements) {
        var oldArc = new ArcOldFormat();
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
        oldArc.name = arc.getName();
        oldArc.twoXY = createTwoXY(arc);
        oldArc.type = convertArcType(arc);
        oldArc.multiplicity = convertArcMultiplicity(arc);
        oldArc.src = arc.getFromViewModel().getName();
        oldArc.dest = arc.getToViewModel().getName();
        oldArc.points = convertDragPoints(arc);
        oldArc.isFluid = false; // TODO maybe in the future
        oldArc.choiceInput = convertArcMultiplicityType(arc.getMultiplicityType());
        oldArc.circles = createCircles(arc);
        oldArc.typeIO = convertArcDirection(arc.getArcDirection());
        oldArc.isFlushing = arc.isFlushing();
        return oldArc;
    }

    private String convertArcMultiplicity(ArcViewModel arc) {
        switch (arc.getMultiplicityType()) {
            case Constant:
                return arc.getMultiplicity();
            case Function:
                return arc.getMultiplicityFunction().getName();
        }
        throw new AssertionError("Unknown arc multiplicity type " + arc.getMultiplicityType());
    }

    private String convertArcDirection(ArcDirection arcDirection) {
        var directionToString = new HashMap<ArcDirection, String>();
        directionToString.put(ArcDirection.Input, "input");
        directionToString.put(ArcDirection.Output, "output");
        return directionToString.get(arcDirection);
    }

    private Circles createCircles(ArcViewModel arc) {
        var circles = new Circles();
        circles.circle1 = (int) arc.getToViewModel().getPositionX();
        circles.circle2 = (int) arc.getToViewModel().getPositionY();
        return circles;
    }

    private String convertArcMultiplicityType(ArcMultiplicityType multiplicityType) {
        var typeToString = new HashMap<ArcMultiplicityType, String>();
        typeToString.put(ArcMultiplicityType.Constant, "Constant");
        typeToString.put(ArcMultiplicityType.Function, "Function");
        return typeToString.get(multiplicityType);
    }

    private List<XY> convertDragPoints(ArcViewModel arcViewModel) {
        var start = createXYFromPosition(arcViewModel.getFromViewModel());
        var end = createXYFromPosition(arcViewModel.getToViewModel());
        var convertedDragPoints = arcViewModel.getDragPoints().stream()
                .map(this::convertDragPoint)
                .collect(Collectors.toList());
        var all = new ArrayList<XY>();
        all.add(start);
        all.addAll(1, convertedDragPoints);
        all.add(end);
        return all;
    }

    private XY convertDragPoint(DragPointViewModel dragPointViewModel) {
        var xy = new XY();
        xy.x = (int) dragPointViewModel.getPositionX();
        xy.y = (int) dragPointViewModel.getPositionY();
        return xy;
    }

    private String convertArcType(ArcViewModel arc) {
        if (arc instanceof StandardArcViewModel) {
            return "Regular";
        } else if (arc instanceof InhibitorArcViewModel) {
            return "Inhibitor";
        } else {
            throw new AssertionError("Unknown arc view model type " + arc);
        }
    }

    private TwoXY createTwoXY(ArcViewModel arc) {
        var twoXY = new TwoXY();
        twoXY.p1 = createXYFromPosition(arc.getFromViewModel());
        twoXY.p2 = createXYFromPosition(arc.getToViewModel());
        return twoXY;
    }

    private String convertDefines(DiagramViewModel diagram) {
        var defines = diagram.getDefines().stream()
                .map(d -> String.format("#define %s %s", d.nameProperty().get(), d.expressionProperty().get()))
                .collect(Collectors.toList());
        return String.join(System.lineSeparator(), defines);
    }

    private List<FunctionOldFormat> convertFunctions(DiagramViewModel diagram) {
        return diagram.getFunctions().stream()
                .map(this::convertFunction)
                .collect(Collectors.toList());
    }

    private FunctionOldFormat convertFunction(FunctionViewModel functionViewModel) {
        var oldFunction = new FunctionOldFormat();
        oldFunction.name = convertFunctionName(functionViewModel.getName());
        oldFunction.kind = convertFunctionKind(functionViewModel.getFunctionType());
        oldFunction.returnType = convertFunctionReturnType(functionViewModel.getReturnType());
        oldFunction.body = functionViewModel.getBody();
        return oldFunction;
    }

    private String convertFunctionName(String functionName) {
        if (OldFormatUtils.isRequiredFunction(functionName.toLowerCase())) {
            return capitalizeString(functionName);
        }
        return functionName;
    }

    private String capitalizeString(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private String convertFunctionKind(FunctionType functionType) {
        var functionKindToString = new HashMap<FunctionType, String>();
        functionKindToString.put(FunctionType.Other, "spnp"); // TODO change back to SPNP ???
        functionKindToString.put(FunctionType.Guard, "guard");
        // functionKindToString.put(FunctionType.Generic, "global"); // global is unused
        functionKindToString.put(FunctionType.Probability, "probability");
        functionKindToString.put(FunctionType.Generic, "generic");
        functionKindToString.put(FunctionType.Reward, "reward");
        functionKindToString.put(FunctionType.ArcCardinality, "cardinality");
        functionKindToString.put(FunctionType.Distribution, "distribution");
        functionKindToString.put(FunctionType.Halting, "halting");
        return functionKindToString.get(functionType);
    }

    private String convertFunctionReturnType(FunctionReturnType returnType) {
        var returnTypeToString = new HashMap<FunctionReturnType, String>();
        returnTypeToString.put(FunctionReturnType.VOID, "void");
        returnTypeToString.put(FunctionReturnType.DOUBLE, "double");
        returnTypeToString.put(FunctionReturnType.INT, "int");
        return returnTypeToString.get(returnType);
    }

}

