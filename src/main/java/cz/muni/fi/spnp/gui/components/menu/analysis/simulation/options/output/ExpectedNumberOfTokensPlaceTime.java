package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

public class ExpectedNumberOfTokensPlaceTime extends OutputOptionTime {

    public ExpectedNumberOfTokensPlaceTime() {
        super("Expected # of tokens of a given place at time t");

        initPlace();
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ExpectedNumberOfTokensPlaceTime();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addExpectedNumberOfTokensPlace(result, diagramViewModel, getPlace());

        tryAddingClosingBracket(result);
    }

}
