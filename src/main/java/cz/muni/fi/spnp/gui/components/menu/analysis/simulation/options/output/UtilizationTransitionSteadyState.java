package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class UtilizationTransitionSteadyState extends OutputOptionSteadyState {

    public UtilizationTransitionSteadyState() {
        super("Utilization for a given transition in steady-state");

        initTransition();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addUtilizationTransition(result, diagramViewModel, getTransition());
    }

}
