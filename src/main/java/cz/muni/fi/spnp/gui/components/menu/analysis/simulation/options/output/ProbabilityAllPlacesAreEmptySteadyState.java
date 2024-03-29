package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Probability all places are empty in steady state output option view model.
 */
public class ProbabilityAllPlacesAreEmptySteadyState extends OutputOptionSteadyState {

    public ProbabilityAllPlacesAreEmptySteadyState() {
        super("Probability that all places are empty in steady-state");
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ProbabilityAllPlacesAreEmptySteadyState();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        diagramViewModel.getPlaces().forEach(placeViewModel -> addProbabilityPlaceIsEmpty(result, diagramViewModel, placeViewModel));
    }

}
