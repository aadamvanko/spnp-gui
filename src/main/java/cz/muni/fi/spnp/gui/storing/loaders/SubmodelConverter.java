package cz.muni.fi.spnp.gui.storing.loaders;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.components.menu.views.defines.DefineViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionReturnType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.includes.IncludeViewModel;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.storing.oldmodels.*;
import cz.muni.fi.spnp.gui.viewmodel.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.DistributionType;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SubmodelConverter {

    private final Notifications notifications;

    public SubmodelConverter(Notifications notifications) {
        this.notifications = notifications;
    }

    public DiagramViewModel convert(Submodel submodel, ProjectViewModel projectViewModel) {
        var includes = convertIncludes(submodel.includes);
        var defines = convertDefines(submodel.defines);
        var functions = convertFunctions(submodel.functions);
        var elements = convertElements(submodel.elements, functions);

        var diagram = new DiagramViewModel(notifications, projectViewModel, elements, includes, defines, functions);
        diagram.nameProperty().set(submodel.name);
        return diagram;
    }

    private List<ElementViewModel> convertElements(List<ElementOldFormat> oldElements, List<FunctionViewModel> functions) {
        var oldPlaces = oldElements.stream()
                .filter(oe -> oe instanceof PlaceOldFormat)
                .map(op -> (PlaceOldFormat) op)
                .collect(Collectors.toList());

        var places = oldPlaces.stream()
                .map(op -> (PlaceViewModel) convertPlace(op))
                .collect(Collectors.toList());

        var oldTransitions = oldElements.stream()
                .filter(oe -> oe instanceof TransitionOldFormat)
                .map(oe -> (TransitionOldFormat) oe)
                .collect(Collectors.toList());

        var transitions = oldTransitions.stream()
                .map(ot -> (TransitionViewModel) convertTransition(ot, places, functions))
                .collect(Collectors.toList());

        var oldConnectables = Stream.concat(oldPlaces.stream(), oldTransitions.stream()).collect(Collectors.toList());

        var oldArcs = oldElements.stream()
                .filter(oe -> oe instanceof ArcOldFormat)
                .collect(Collectors.toList());

        var connectables = Stream.concat(places.stream(), transitions.stream()).collect(Collectors.toList());
        var arcs = oldArcs.stream()
                .map(oa -> (ArcViewModel) convertArc((ArcOldFormat) oa, oldConnectables, connectables))
                .collect(Collectors.toList());

        return Stream.concat(connectables.stream(), arcs.stream())
                .collect(Collectors.toList());
    }

    private ElementViewModel convertPlace(PlaceOldFormat oldPlace) {
        var place = new PlaceViewModel();
        place.nameProperty().set(oldPlace.name);
        place.positionXProperty().set(oldPlace.xy.x);
        place.positionYProperty().set(oldPlace.xy.y);
        place.numberOfTokensProperty().set(oldPlace.token);
        return place;
    }

    private ElementViewModel convertTransition(TransitionOldFormat oldTransition, List<PlaceViewModel> places, List<FunctionViewModel> functions) {
        if (oldTransition instanceof ImmediateTransitionOldFormat) {
            var oldImmediate = (ImmediateTransitionOldFormat) oldTransition;
            return convertImmediateTransition(oldImmediate, places, functions);
        } else if (oldTransition instanceof TimedTransitionOldFormat) {
            var oldTimed = (TimedTransitionOldFormat) oldTransition;
            return convertTimedTransition(oldTimed, places, functions);
        }
        throw new IllegalStateException(oldTransition.getClass().toString());
    }

    private ElementViewModel convertImmediateTransition(ImmediateTransitionOldFormat oldImmediate, List<PlaceViewModel> places, List<FunctionViewModel> functions) {
        var immediate = new ImmediateTransitionViewModel();
        immediate.nameProperty().set(oldImmediate.name);
        immediate.positionXProperty().set(oldImmediate.xy.x);
        immediate.positionYProperty().set(oldImmediate.xy.y);
        immediate.priorityProperty().set(oldImmediate.valueTransition);
        immediate.setTransitionProbability(convertProbability(oldImmediate, places, functions));
        return immediate;
    }

    private ElementViewModel convertTimedTransition(TimedTransitionOldFormat oldTimed, List<PlaceViewModel> places, List<FunctionViewModel> functions) {
        var timed = new TimedTransitionViewModel();
        timed.nameProperty().set(oldTimed.name);
        timed.positionXProperty().set(oldTimed.xy.x);
        timed.positionYProperty().set(oldTimed.xy.y);
        timed.priorityProperty().set(convertPriority(oldTimed.priority));
        timed.transitionDistributionTypeProperty().set(getTransitionDistributionType(oldTimed.distribution));
        timed.distributionTypeProperty().set(DistributionType.Beta);
        return timed;
    }

    private TransitionProbabilityViewModel convertProbability(ImmediateTransitionOldFormat oldImmediate, List<PlaceViewModel> places, List<FunctionViewModel> functions) {
        switch (oldImmediate.choiceInput) {
            case "Constant value":
                var constantProbability = new ConstantTransitionProbabilityViewModel();
                constantProbability.valueProperty().set(Double.parseDouble(oldImmediate.probability));
                return constantProbability;

            case "Place dependent":
                var placeDependentProbability = new PlaceDependentTransitionProbabilityViewModel();
                placeDependentProbability.valueProperty().set(Double.parseDouble(oldImmediate.probability));
                placeDependentProbability.setDependentPlace(findPlaceViewModel(places, oldImmediate.placeDependent));
                return placeDependentProbability;

            case "Function":
                var functionalProbability = new FunctionalTransitionProbabilityViewModel();
                functionalProbability.setFunction(findFunctionViewModel(functions, oldImmediate.probability));
                return functionalProbability;

            default:
                throw new AssertionError(oldImmediate.choiceInput);
        }
    }

    private FunctionViewModel findFunctionViewModel(List<FunctionViewModel> functions, String name) {
        return functions.stream()
                .filter(f -> f.nameProperty().get().equals(name))
                .collect(Collectors.toList())
                .get(0);
    }

    private PlaceViewModel findPlaceViewModel(List<PlaceViewModel> places, String name) {
        return places.stream()
                .filter(p -> p.nameProperty().get().equals(name))
                .collect(Collectors.toList())
                .get(0);
    }

    private int convertPriority(String priority) {
        if (priority.equals("null")) {
            return 0;
        } else {
            return Integer.parseInt(priority);
        }
    }

    private TransitionDistributionType getTransitionDistributionType(String oldDistributionType) {
        return null;
    }

    private ElementViewModel convertArc(ArcOldFormat oldArc, List<ConnectableOldFormat> oldConnectables, List<ConnectableViewModel> elements) {
        var oldSource = oldConnectables.stream()
                .filter(oldConnectable -> oldConnectable.name.equals(oldArc.src) && oldConnectable.arcReferences.stream().anyMatch(ar -> ar.arc.equals(oldArc.name)))
                .collect(Collectors.toList())
                .get(0);
        var oldDestination = oldConnectables.stream()
                .filter(oldConnectable -> oldConnectable.name.equals(oldArc.dest) && oldConnectable.vInputArc.contains(oldArc.name))
                .collect(Collectors.toList())
                .get(0);

        var source = findElementForOld(elements, oldSource);
        var destination = findElementForOld(elements, oldDestination);
        var dragMarks = convertPointsToDragMarks(oldArc.points.subList(1, oldArc.points.size() - 1));

        if (oldArc.type.equals("Regular")) {
            return new StandardArcViewModel(oldArc.name, source, destination, dragMarks);
        } else if (oldArc.type.equals("Inhibitor")) {
            return new InhibitorArcViewModel(oldArc.name, source, destination, dragMarks);
        } else {
            throw new IllegalStateException("Unknown arc type " + oldArc.type);
        }
    }

    private List<ArcDragMarkViewModel> convertPointsToDragMarks(List<XY> points) {
        return points.stream().map(xy -> new ArcDragMarkViewModel(xy.x, xy.y)).collect(Collectors.toList());
    }

    private ElementViewModel findElementForOld(List<ConnectableViewModel> elements, ConnectableOldFormat oldConnectable) {
        for (var element : elements) {
            var placeTypeEquality = oldConnectable instanceof PlaceOldFormat && element instanceof PlaceViewModel;
            var transitionTypeEquality = oldConnectable instanceof TransitionOldFormat && element instanceof TransitionViewModel;
            if (element.nameProperty().get().equals(oldConnectable.name) && (placeTypeEquality || transitionTypeEquality)) {
                return element;
            }
        }
        return null;
    }

    private List<IncludeViewModel> convertIncludes(String includes) {
        var lines = includes.split(System.lineSeparator());
        return Stream.of(lines)
                .filter(line -> !line.isBlank())
                .map(this::lineToInclude)
                .collect(Collectors.toList());
    }

    private IncludeViewModel lineToInclude(String line) {
        var stripped = line.strip();
        var tokens = stripped.split("\\s+", 2);
        return new IncludeViewModel(tokens[1]);
    }

    private List<DefineViewModel> convertDefines(String oldDefines) {
        var lines = oldDefines.split(System.lineSeparator());
        return Stream.of(lines)
                .filter(line -> !line.isBlank())
                .map(this::lineToDefine)
                .collect(Collectors.toList());
    }

    private DefineViewModel lineToDefine(String line) {
        var stripped = line.strip();
        var tokens = stripped.split("\\s+", 3);
        return new DefineViewModel(tokens[1], tokens[2]);
    }

    private List<FunctionViewModel> convertFunctions(List<FunctionOldFormat> oldFunctions) {
        return oldFunctions.stream()
                .map(old -> new FunctionViewModel(old.name, convertFunctionKind(old.kind), old.body, convertFunctionReturnType(old.returnType)))
                .collect(Collectors.toList());
    }

    private FunctionReturnType convertFunctionReturnType(String returnType) {
        var stringToReturnType = new HashMap<String, FunctionReturnType>();
        stringToReturnType.put("void", FunctionReturnType.VOID);
        stringToReturnType.put("double", FunctionReturnType.DOUBLE);
        stringToReturnType.put("int", FunctionReturnType.INT);
        return stringToReturnType.get(returnType);
    }

    private FunctionType convertFunctionKind(String kind) {
        var stringToFunctionType = new HashMap<String, FunctionType>();
        stringToFunctionType.put("spnp", FunctionType.Other); // TODO change back to SPNP ???
        stringToFunctionType.put("guard", FunctionType.Guard);
        stringToFunctionType.put("global", FunctionType.Generic); // ???
        stringToFunctionType.put("probability", FunctionType.Probability);
        stringToFunctionType.put("generic", FunctionType.Generic);
        stringToFunctionType.put("reward", FunctionType.Reward);
        stringToFunctionType.put("cardinality", FunctionType.ArcCardinality);
        stringToFunctionType.put("distribution", FunctionType.Distribution);
        stringToFunctionType.put("halting", FunctionType.Halting);
        return stringToFunctionType.get(kind);
    }
}
