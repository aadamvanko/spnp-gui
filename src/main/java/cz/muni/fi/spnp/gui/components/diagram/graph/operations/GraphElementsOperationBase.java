package cz.muni.fi.spnp.gui.components.diagram.graph.operations;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.ArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.DragPointViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TransitionProbabilityType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TransitionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate.FunctionalTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate.PlaceDependentTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Clipboard;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelCopyFactory;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelUtils;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Base class for the operations working with the diagram's elements (copy, cut, paste, ...).
 */
public abstract class GraphElementsOperationBase implements GraphElementsOperation {

    protected final Model model;
    protected final DiagramViewModel diagramViewModel;

    public GraphElementsOperationBase(Model model, DiagramViewModel diagramViewModel) {
        this.model = model;
        this.diagramViewModel = diagramViewModel;
    }

    protected List<ElementViewModel> filterOutDragPoints(List<ElementViewModel> selected) {
        return selected.stream()
                .filter(elementViewModel -> !(elementViewModel instanceof DragPointViewModel))
                .collect(Collectors.toList());
    }

    protected List<ElementViewModel> filterOutDisconnectedArcs(List<ElementViewModel> elements) {
        return elements.stream()
                .filter(elementViewModel -> !isDisconnectedArc(elements, elementViewModel))
                .collect(Collectors.toList());
    }

    protected boolean isDisconnectedArc(List<ElementViewModel> elements, ElementViewModel elementViewModel) {
        if (elementViewModel instanceof ArcViewModel) {
            var arcViewModel = (ArcViewModel) elementViewModel;
            var containsFrom = elements.contains(arcViewModel.getFromViewModel());
            var containsTo = elements.contains(arcViewModel.getToViewModel());
            var connected = containsTo && containsFrom;
            var disconnected = !connected;
            return disconnected;
        }
        return false;
    }

    protected List<ElementViewModel> createViewModelsCopies(List<ElementViewModel> selectedWithoutDragPoints) {
        var viewModelCopyFactory = new ViewModelCopyFactory();
        var copiesPlaces = ViewModelUtils.onlyElements(PlaceViewModel.class, selectedWithoutDragPoints).map(viewModelCopyFactory::createCopy);
        var copiesTransitions = ViewModelUtils.onlyElements(TransitionViewModel.class, selectedWithoutDragPoints).map(viewModelCopyFactory::createCopy);
        var copiesArcs = ViewModelUtils.onlyElements(ArcViewModel.class, selectedWithoutDragPoints).map(viewModelCopyFactory::createCopy);
        return Stream.concat(Stream.concat(copiesPlaces, copiesTransitions), copiesArcs).collect(Collectors.toList());
    }

    protected void removeUncutPlaceReferences(List<ElementViewModel> elements) {
        for (var element : elements) {
            if (element instanceof ImmediateTransitionViewModel) {
                var immediateTransition = (ImmediateTransitionViewModel) element;
                if (immediateTransition.getTransitionProbability().getEnumType() == TransitionProbabilityType.PlaceDependent) {
                    var placeDependentProbability = (PlaceDependentTransitionProbabilityViewModel) immediateTransition.getTransitionProbability();
                    if (!elements.contains(placeDependentProbability.getDependentPlace())) {
                        element.removePlaceReference(placeDependentProbability.getDependentPlace());
                    }
                }
            } else if (element instanceof TimedTransitionViewModel) {
                var timedTransition = (TimedTransitionViewModel) element;
                var dependentPlace = timedTransition.getTransitionDistribution().dependentPlaceProperty().get();
                if (!elements.contains(dependentPlace)) {
                    element.removePlaceReference(dependentPlace);
                }
            }
        }
    }

    protected Collection<FunctionViewModel> redirectFunctionReferences(List<ElementViewModel> elements, DiagramViewModel sourceDiagram,
                                                                       Clipboard.OperationType operationType) {
        var functionsMapping = new HashMap<FunctionViewModel, FunctionViewModel>();
        var elementsFunctions = new ArrayList<FunctionViewModel>();

        for (var element : elements) {
            if (element instanceof ArcViewModel) {
                var arcViewModel = (ArcViewModel) element;
                if (arcViewModel.isFlushing()) {
                    if (operationType == Clipboard.OperationType.CUT) {
                        var multiplicityFunction = arcViewModel.getMultiplicityFunction();
                        elementsFunctions.add(multiplicityFunction);
                        sourceDiagram.getFunctions().remove(multiplicityFunction); // this removes also all function references in elements
                        arcViewModel.multiplicityFunctionProperty().set(multiplicityFunction); // thus, we need to set it again
                    } else if (operationType == Clipboard.OperationType.COPY) {
                        var viewModelCopyFactory = new ViewModelCopyFactory();
                        var multiplicityFunctionCopy = viewModelCopyFactory.createCopy(arcViewModel.getMultiplicityFunction());
                        elementsFunctions.add(multiplicityFunctionCopy);
                        arcViewModel.multiplicityFunctionProperty().set(multiplicityFunctionCopy);
                    }
                } else {
                    arcViewModel.multiplicityFunctionProperty().set(getOrCopy(functionsMapping, arcViewModel.getMultiplicityFunction()));
                }
            } else if (element instanceof ImmediateTransitionViewModel) {
                var immediateTransition = (ImmediateTransitionViewModel) element;
                immediateTransition.guardFunctionProperty().set(getOrCopy(functionsMapping, immediateTransition.getGuardFunction()));
                if (immediateTransition.getTransitionProbability().getEnumType() == TransitionProbabilityType.Functional) {
                    var functionalProbability = (FunctionalTransitionProbabilityViewModel) immediateTransition.getTransitionProbability();
                    functionalProbability.functionProperty().set(getOrCopy(functionsMapping, functionalProbability.getFunction()));
                }
            } else if (element instanceof TimedTransitionViewModel) {
                var timedTransition = (TimedTransitionViewModel) element;
                timedTransition.guardFunctionProperty().set(getOrCopy(functionsMapping, timedTransition.getGuardFunction()));
                var distribution = timedTransition.getTransitionDistribution();
                for (var functionProperty : distribution.getFunctions()) {
                    functionProperty.set(getOrCopy(functionsMapping, functionProperty.get()));
                }
            }
        }

        elementsFunctions.addAll(functionsMapping.values());
        return elementsFunctions;
    }

    protected FunctionViewModel getOrCopy(HashMap<FunctionViewModel, FunctionViewModel> functionsMapping, FunctionViewModel function) {
        if (function == null) {
            return null;
        }

        if (functionsMapping.containsKey(function)) {
            return functionsMapping.get(function);
        }
        var viewModelCopyFactory = new ViewModelCopyFactory();
        var functionCopy = viewModelCopyFactory.createCopy(function);
        functionsMapping.put(function, functionCopy);
        return functionCopy;
    }

}
