package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

public class UtilizationTransitionSteadyState extends OutputOptionSteadyState {

    public UtilizationTransitionSteadyState() {
        super("Utilization for a given transition in steady-state");

        initTransition();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new UtilizationTransitionSteadyState();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addUtilizationTransition(result, diagramViewModel, getTransition());
    }

}
