package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class ThroughputTransitionSteadyState extends OutputOptionSteadyState {

    public ThroughputTransitionSteadyState() {
        super("Throughput of the given transition in steady-state");

        initTransition();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ThroughputTransitionSteadyState();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addThroughputTransition(result, diagramViewModel, getTransition());
    }

}
