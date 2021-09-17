package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

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
