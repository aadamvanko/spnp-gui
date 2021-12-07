package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TransitionViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Steady state output option view model.
 */
public abstract class OutputOptionSteadyState extends OutputOptionViewModel {

    public OutputOptionSteadyState(String title) {
        super(title);
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        addSolve(result);
    }

    protected void addExpectedNumberOfTokensPlace(OutputOptionsResult result, DiagramViewModel diagramViewModel, PlaceViewModel placeViewModel) {
        var outFunc = addPlaceTokensFunction(result, diagramViewModel, placeViewModel);
        result.getLines().add(String.format("pr_expected(\"Expected # of tokens of the place %s in steady-state\", %s);", placeViewModel.getName(), outFunc.getName()));
    }

    protected void addThroughputTransition(OutputOptionsResult result, DiagramViewModel diagramViewModel, TransitionViewModel transition) {
        var outFunc = addTransitionRateFunction(result, diagramViewModel, transition);
        result.getLines().add(String.format("pr_expected(\"Throughput of the transition %s in steady-state\", %s);", transition.getName(), outFunc.getName()));
    }


    protected void addUtilizationTransition(OutputOptionsResult result, DiagramViewModel diagramViewModel, TransitionViewModel transition) {
        var outFunc = addTransitionEnabledFunction(result, diagramViewModel, transition);
        result.getLines().add(String.format("pr_expected(\"Utilization for the transition %s in steady-state\", %s);", transition.getName(), outFunc.getName()));
    }

    protected void addProbabilityPlaceIsEmpty(OutputOptionsResult result, DiagramViewModel diagramViewModel, PlaceViewModel place) {
        var outFunc = addPlaceIsEmptyFunction(result, diagramViewModel, place);
        result.getLines().add(String.format("pr_expected(\"Probability that the place %s is empty in steady-state\", %s); ", place.getName(), outFunc.getName()));
    }

}
