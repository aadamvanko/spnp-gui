package cz.muni.fi.spnp.gui.components.graph.operations;

import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionProbabilityType;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.FunctionalTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.PlaceDependentTransitionProbabilityViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    protected Collection<FunctionViewModel> redirectFunctionReferences(List<ElementViewModel> elements, DiagramViewModel sourceDiagram) {
        var functionsMapping = new HashMap<FunctionViewModel, FunctionViewModel>();
        var elementsFunctions = new ArrayList<FunctionViewModel>();

        for (var element : elements) {
            if (element instanceof ArcViewModel) {
                var arcViewModel = (ArcViewModel) element;
                if (arcViewModel.isFlushing()) {
                    elementsFunctions.add(arcViewModel.getMultiplicityFunction());
                    sourceDiagram.getFunctions().remove(arcViewModel.getMultiplicityFunction());
                } else {
                    arcViewModel.multiplicityFunctionProperty().set(getOrCopy(functionsMapping, arcViewModel.getMultiplicityFunction()));
                }
            } else if (element instanceof ImmediateTransitionViewModel) {
                var immediateTransition = (ImmediateTransitionViewModel) element;
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
