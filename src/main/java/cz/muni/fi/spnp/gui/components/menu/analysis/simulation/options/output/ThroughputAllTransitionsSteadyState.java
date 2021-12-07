package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

public class ThroughputAllTransitionsSteadyState extends OutputOptionSteadyState {

    public ThroughputAllTransitionsSteadyState() {
        super("Throughput of all transitions in steady-state");
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ThroughputAllTransitionsSteadyState();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        diagramViewModel.getTransitions().forEach(transitionViewModel -> addThroughputTransition(result, diagramViewModel, transitionViewModel));
    }

}
