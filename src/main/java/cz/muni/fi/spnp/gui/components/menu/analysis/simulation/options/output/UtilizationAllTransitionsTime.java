package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Utilization of all transition in time output option view model.
 */
public class UtilizationAllTransitionsTime extends OutputOptionTime {

    public UtilizationAllTransitionsTime() {
        super("Utilization for all transitions at time t");

        initTransition();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new UtilizationAllTransitionsTime();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        diagramViewModel.getTransitions().forEach(transitionViewModel -> addUtilizationTransition(result, diagramViewModel, transitionViewModel));

        tryAddingClosingBracket(result);
    }

}
