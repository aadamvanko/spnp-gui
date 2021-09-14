package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class ProbabilityPlaceIsEmptySteadyState extends OutputOptionSteadyState {

    public ProbabilityPlaceIsEmptySteadyState() {
        super("Probability that the given place is empty in steady-state");

        initPlace();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addProbabilityPlaceIsEmpty(result, diagramViewModel, getPlace());
    }

}
