package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class ExpectedNumberOfTokensPlaceTime extends OutputOptionTime {

    public ExpectedNumberOfTokensPlaceTime() {
        super("Expected # of tokens of a given place at time t");

        initPlace();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        addExpectedNumberOfTokensPlace(result, diagramViewModel, getPlace());

        tryAddingClosingBracket(result);
    }

}
