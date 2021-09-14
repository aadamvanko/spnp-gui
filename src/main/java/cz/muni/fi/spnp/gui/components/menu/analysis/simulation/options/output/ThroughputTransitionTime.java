package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class ThroughputTransitionTime extends OutputOptionTime {

    public ThroughputTransitionTime() {
        super("Throughput of the given transition at time t");

        initTransition();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addThroughputTransition(result, diagramViewModel, getTransition());

        tryAddingClosingBracket(result);
    }

}
