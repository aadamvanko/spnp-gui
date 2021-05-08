package cz.muni.fi.spnp.gui.loaders;

import cz.muni.fi.spnp.gui.components.menu.views.defines.DefineViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubmodelConverter {

    private final Notifications notifications;

    public SubmodelConverter(Notifications notifications) {
        this.notifications = notifications;
    }

    public DiagramViewModel convert(SubmodelOldFormat submodel, ProjectViewModel projectViewModel) {
        var elements = convertElements(submodel.elements);
        var defines = convertDefines(submodel.defines);
        var functions = convertFunctions(submodel.functions);
        var diagram = new DiagramViewModel(notifications, projectViewModel, elements, defines, functions);
        diagram.nameProperty().set(submodel.name);
        return diagram;
    }

    private List<ElementViewModel> convertElements(List<ElementOldFormat> oldElements) {
        var elements = new ArrayList<ElementViewModel>();

        var oldConnectables = oldElements.stream().filter(oe -> oe instanceof ConnectableOldFormat)
                .map(oe -> (ConnectableOldFormat) oe).collect(Collectors.toList());
        oldConnectables.forEach(oe -> elements.add(convertConnectable(oe)));

        var oldElementsArcs = oldElements.stream().filter(oe -> oe instanceof ArcOldFormat).collect(Collectors.toList());
        oldElementsArcs.forEach(oe -> elements.add(convertArc((ArcOldFormat) oe, oldConnectables, elements)));

        return elements;
    }

    private ElementViewModel convertConnectable(ConnectableOldFormat oldConnectable) {
        if (oldConnectable instanceof PlaceOldFormat) {
            var oldPlace = (PlaceOldFormat) oldConnectable;
            return new PlaceViewModel(oldPlace.name, oldPlace.xy.x, oldPlace.xy.y, oldPlace.token);
        } else if (oldConnectable instanceof ImmediateTransitionOldFormat) {
            var oldImmediate = (ImmediateTransitionOldFormat) oldConnectable;
            // TODO priority from double to int missing
            return new ImmediateTransitionViewModel(oldImmediate.name, oldImmediate.xy.x, oldImmediate.xy.y, 0);
        } else if (oldConnectable instanceof TimedTransitionOldFormat) {
            var oldTimed = (TimedTransitionOldFormat) oldConnectable;
            // TODO
        }
        return null;
    }

    private ElementViewModel convertArc(ArcOldFormat oldArc, List<ConnectableOldFormat> oldConnectables, List<ElementViewModel> elements) {
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

    private ElementViewModel findElementForOld(List<ElementViewModel> elements, ConnectableOldFormat oldConnectable) {
        for (var element : elements) {
            var placeTypeEquality = oldConnectable instanceof PlaceOldFormat && element instanceof PlaceViewModel;
            var transitionTypeEquality = oldConnectable instanceof TransitionOldFormat && element instanceof TransitionViewModel;
            if (element.nameProperty().get().equals(oldConnectable.name) && (placeTypeEquality || transitionTypeEquality)) {
                return element;
            }
        }
        return null;
    }

    private List<DefineViewModel> convertDefines(String oldDefines) {
        return List.of();
    }

    private List<FunctionViewModel> convertFunctions(List<FunctionOldFormat> oldFunctions) {
        return List.of();
    }
}
