//package cz.muni.fi.spnp.gui.storing.savers;
//
//import cz.muni.fi.spnp.gui.storing.oldmodels.*;
//import cz.muni.fi.spnp.gui.viewmodel.*;
//import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
//import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;
//import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class DiagramConverter {
//
//    public Submodel convert(DiagramViewModel diagram) {
//        var submodel = new Submodel();
//        submodel.name = diagram.getName();
//        submodel.elements = convertElements(diagram);
//        submodel.includes = convertIncludes(diagram);
//        submodel.defines = convertDefines(diagram);
//        submodel.functions = convertFunctions(diagram);
//        submodel.variables = convertVariables(diagram);
//        return submodel;
//    }
//
//    private List<VariableOldFormat> convertVariables(DiagramViewModel diagramViewModel) {
//        return diagramViewModel.getVariables().stream()
//                .map(this::convertVariable)
//                .collect(Collectors.toList());
//    }
//
//    private VariableOldFormat convertVariable(VariableViewModel variableViewModel) {
//        return null;
//    }
//
//    private List<ElementOldFormat> convertElements(DiagramViewModel diagram) {
//        var connectableElements = diagram.getElements().stream()
//                .filter(e -> e instanceof ConnectableViewModel)
//                .map(e -> (ConnectableViewModel) e)
//                .collect(Collectors.toList());
//
//        var arcs = diagram.getElements().stream()
//                .filter(e -> e instanceof ArcViewModel)
//                .map(e -> (ArcViewModel) e)
//                .collect(Collectors.toList());
//
//        var oldElements = convertConnectableElements(connectableElements, arcs);
//        oldElements.addAll(convertArcs(arcs, oldElements));
//        return oldElements;
//    }
//
//    private String convertIncludes(DiagramViewModel diagram) {
//        return "";
//    }
//
//    private List<ElementOldFormat> convertConnectableElements(List<ConnectableViewModel> connectableElements, List<ArcViewModel> arcs) {
//        return connectableElements.stream()
//                .map(e -> convertConnectableElement(e, arcs))
//                .collect(Collectors.toList());
//    }
//
//    private ElementOldFormat convertConnectableElement(ConnectableViewModel element, List<ArcViewModel> arcs) {
//        if (element instanceof PlaceViewModel) {
//            return convertPlace((PlaceViewModel) element, arcs);
//        } else if (element instanceof ImmediateTransitionViewModel) {
//            return convertImmediateTransition((ImmediateTransitionViewModel) element, arcs);
//        } else if (element instanceof TimedTransitionViewModel) {
//            return convertTimedTransition((TimedTransitionViewModel) element, arcs);
//        } else {
//            throw new UnsupportedOperationException("Unknown connectable view model");
//        }
//    }
//
//    private ElementOldFormat convertPlace(PlaceViewModel place, List<ArcViewModel> arcs) {
//        var oldPlace = new PlaceOldFormat();
//        oldPlace.name = place.nameProperty().get();
//        oldPlace.token = place.numberOfTokensProperty().get();
//        oldPlace.fluid = false; // TODO maybe in the future
//        var outputArcs = findOutputArcs(place, arcs);
//        oldPlace.numberOfConnectedObjects = outputArcs.size();
//        oldPlace.arcReferences = convertArcsToReferences(outputArcs);
//        var inputArcs = findInputArcs(place, arcs);
//        oldPlace.vInputArc = getArcsNames(inputArcs);
//        oldPlace.vOutputArc = getArcsNames(outputArcs);
//        oldPlace.label = createLabel(place);
//        /*
//Place:
//Name: place0
//Token: 0
//Fluid: false
//X, Y: 44,44
//Number of connected objects: 2
//Dest: immediate2PlaceDependant
//Arc:arc1
//Dest: immediate1Constant
//Arc:arc0
//vInputArc: null
//vOutputArc: [arc0, arc1]
//Place label:
//X, Y: 43,76
//Width, Height: 41,20
//Textwidth: 0
//         */
//        return oldPlace;
//    }
//
//    private LabelOldFormat createLabel(ConnectableViewModel element) {
//        var label = new LabelOldFormat();
//        label.xy = new XY();
//        // TODO not important, label does not have separate position in my model
//        label.xy.x = (int) element.positionXProperty().get();
//        label.xy.y = (int) element.positionYProperty().get();
//        label.widthHeight = new WidthHeight();
//        label.widthHeight.width = -1;
//        label.widthHeight.height = -1;
//        label.textwidth = 0;
//        return label;
//    }
//
//    private List<String> getArcsNames(List<ArcViewModel> arcs) {
//        return arcs.stream()
//                .map(a -> a.nameProperty().get())
//                .collect(Collectors.toList());
//    }
//
//    private List<ArcOldFormatReference> convertArcsToReferences(List<ArcViewModel> outArcs) {
//        return outArcs.stream()
//                .map(a -> {
//                    var arcReference = new ArcOldFormatReference();
//                    arcReference.arc = a.nameProperty().get();
//                    arcReference.dest = a.getToViewModel().nameProperty().get();
//                    return arcReference;
//                })
//                .collect(Collectors.toList());
//    }
//
//    private List<ArcViewModel> findInputArcs(ConnectableViewModel to, List<ArcViewModel> arcs) {
//        return arcs.stream()
//                .filter(a -> a.getToViewModel() == to)
//                .collect(Collectors.toList());
//    }
//
//    private List<ArcViewModel> findOutputArcs(ConnectableViewModel from, List<ArcViewModel> arcs) {
//        return arcs.stream()
//                .filter(a -> a.getFromViewModel() == from)
//                .collect(Collectors.toList());
//    }
//
//    private ElementOldFormat convertImmediateTransition(ImmediateTransitionViewModel immediate, List<ArcViewModel> arcs) {
//        var oldImmediate = new ImmediateTransitionOldFormat();
//        oldImmediate.name = immediate.nameProperty().get();
//        // TODO fixed values generated as default from old gui
//        oldImmediate.width = 5;
//        oldImmediate.height = 32;
//        oldImmediate.xy = new XY();
//        oldImmediate.xy.x = (int) immediate.positionXProperty().get();
//        oldImmediate.xy.y = (int) immediate.positionYProperty().get();
//        oldImmediate.guard = immediate.guardFunctionProperty().get();
//        oldImmediate.probability = convertProbability(immediate);
//        oldImmediate.choiceInput = convertImmediateProbabilityToString(immediate.transitionProbabilityTypeProperty().get());
//        var outputArcs = findOutputArcs(immediate, arcs);
//        oldImmediate.numberOfConnectedObjects = outputArcs.size();
//        oldImmediate.arcReferences = convertArcsToReferences(outputArcs);
//        oldImmediate.vInputArc = getArcsNames(findInputArcs(immediate, arcs));
//        oldImmediate.vOutputArc = getArcsNames(findOutputArcs(immediate, arcs));
//        oldImmediate.typeTransition = "Immediate";
//        oldImmediate.placeDependent = stringOrNull(immediate.placeDependantProbabilityProperty().get());
//        oldImmediate.valueTransition = immediate.priorityProperty().get();
//        oldImmediate.label = createLabel(immediate);
//        /*
//        Immediate:
//Name: immediate1Constant
//Width: 5
//Height: 32
//X, Y: 218,84
//Guard: guard1
//Probability: 0.7
//Choice Input: Constant value
//Number of connected objects: 1
//Dest: place1
//Arc:arc2
//vInputArc: [arc0]
//vOutputArc: [arc2]
//Type Transition: Immediate
//Place Dependent: null
//Value Transition: 225
//Transition label:
//X, Y: 163,116
//Width, Height: 119,20
//Textwidth: 0
//         */
//        return oldImmediate;
//    }
//
//    private String stringOrNull(String s) {
//        if (s == null) {
//            return "null";
//        }
//        return s;
//    }
//
//    private String convertImmediateProbabilityToString(TransitionProbabilityType probabilityType) {
//        if (probabilityType == TransitionProbabilityType.CONSTANT) {
//            return "Constant value";
//        }
//        return convertProbabilityToString(probabilityType);
//    }
//
//    private String convertProbabilityToString(TransitionProbabilityType transitionProbabilityType) {
//        Map<TransitionProbabilityType, String> typesToStrings  = Map.of(
//            TransitionProbabilityType.CONSTANT, "Constant",
//            TransitionProbabilityType.PLACE_DEPENDANT, "Place dependent",
//            TransitionProbabilityType.FUNCTIONAL, "Function"
//        );
//        return typesToStrings.get(transitionProbabilityType);
//    }
//
//    private String convertProbability(ImmediateTransitionViewModel immediateTransition) {
//        var probabilityType = immediateTransition.transitionProbabilityTypeProperty().get();
//        switch (probabilityType) {
//            case CONSTANT:
//                return String.valueOf(immediateTransition.constantProbabilityProperty().get());
//
//            case PLACE_DEPENDANT:
//                return immediateTransition.placeDependantProbabilityProperty().get();
//
//            case FUNCTIONAL:
//                return immediateTransition.functionalProbabilityProperty().get();
//
//            default:
//                throw new IllegalStateException("Unknown probability type.");
//        }
//    }
//
//    private ElementOldFormat convertTimedTransition(TimedTransitionViewModel timed, List<ArcViewModel> arcs) {
//        var oldTimed = new ImmediateTransitionOldFormat();
//        oldTimed.name = timed.nameProperty().get();
//        // TODO fixed values generated as default from old gui
//        oldTimed.width = 5;
//        oldTimed.height = 32;
//        oldTimed.xy = new XY();
//        oldTimed.xy.x = (int) timed.positionXProperty().get();
//        oldTimed.xy.y = (int) timed.positionYProperty().get();
//        oldTimed.guard = timed.guardFunctionProperty().get();
//        oldTimed.probability = convertProbability(timed);
//        oldTimed.choiceInput = convertImmediateProbabilityToString(timed.transitionProbabilityTypeProperty().get());
//        var outputArcs = findOutputArcs(timed, arcs);
//        oldTimed.numberOfConnectedObjects = outputArcs.size();
//        oldTimed.arcReferences = convertArcsToReferences(outputArcs);
//        oldTimed.vInputArc = getArcsNames(findInputArcs(timed, arcs));
//        oldTimed.vOutputArc = getArcsNames(findOutputArcs(timed, arcs));
//        oldTimed.typeTransition = "Immediate";
//        oldTimed.placeDependent = stringOrNull(timed.placeDependantProbabilityProperty().get());
//        oldTimed.valueTransition = timed.priorityProperty().get();
//        oldTimed.label = createLabel(timed);
//        return oldTimed;
//    }
//
//    private List<ElementOldFormat> convertArcs(List<ArcViewModel> diagram, List<ElementOldFormat> oldElements) {
//        return List.of();
//    }
//
//    private String convertDefines(DiagramViewModel diagram) {
//        var defines = diagram.getDefines().stream()
//                .map(d -> String.format("%s %s", d.nameProperty().get(), d.expressionProperty().get()))
//                .collect(Collectors.toList());
//        return String.join(System.lineSeparator(), defines);
//    }
//
//    private List<FunctionOldFormat> convertFunctions(DiagramViewModel diagram) {
//        return List.of();
//    }
//}
