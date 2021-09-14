package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class UtilizationAllTransitionsSteadyState extends OutputOptionSteadyState {

    public UtilizationAllTransitionsSteadyState() {
        super("Utilization for all transitions in steady-state");

        initTransition();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        diagramViewModel.getTransitions().forEach(transitionViewModel -> addUtilizationTransition(result, diagramViewModel, transitionViewModel));
    }

}
