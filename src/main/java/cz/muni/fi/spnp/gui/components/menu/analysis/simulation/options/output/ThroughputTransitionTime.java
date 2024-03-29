package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Throughput of transition in time output option view model.
 */
public class ThroughputTransitionTime extends OutputOptionTime {

    public ThroughputTransitionTime() {
        super("Throughput of the given transition at time t");

        initTransition();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ThroughputTransitionTime();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addThroughputTransition(result, diagramViewModel, getTransition());

        tryAddingClosingBracket(result);
    }

}
