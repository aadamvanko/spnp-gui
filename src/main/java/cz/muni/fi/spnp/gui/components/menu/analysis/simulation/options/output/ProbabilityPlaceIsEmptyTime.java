package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

/**
 * Probability place is empty in time output option view model.
 */
public class ProbabilityPlaceIsEmptyTime extends OutputOptionTime {

    public ProbabilityPlaceIsEmptyTime() {
        super("Probability that the given place is empty at time t");

        initPlace();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ProbabilityPlaceIsEmptyTime();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addProbabilityPlaceIsEmpty(result, diagramViewModel, getPlace());

        tryAddingClosingBracket(result);
    }

}
