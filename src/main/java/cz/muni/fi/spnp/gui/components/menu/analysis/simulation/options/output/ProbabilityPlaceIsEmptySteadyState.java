package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Probability place is empty in steady state output option view model.
 */
public class ProbabilityPlaceIsEmptySteadyState extends OutputOptionSteadyState {

    public ProbabilityPlaceIsEmptySteadyState() {
        super("Probability that the given place is empty in steady-state");

        initPlace();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ProbabilityPlaceIsEmptySteadyState();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addProbabilityPlaceIsEmpty(result, diagramViewModel, getPlace());
    }

}
