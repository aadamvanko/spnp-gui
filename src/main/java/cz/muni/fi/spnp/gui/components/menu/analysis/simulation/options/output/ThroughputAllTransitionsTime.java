package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

public class ThroughputAllTransitionsTime extends OutputOptionTime {

    public ThroughputAllTransitionsTime() {
        super("Throughput of all transitions at time t");
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ThroughputAllTransitionsTime();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        diagramViewModel.getTransitions().forEach(transitionViewModel -> addThroughputTransition(result, diagramViewModel, transitionViewModel));

        tryAddingClosingBracket(result);
    }

}
