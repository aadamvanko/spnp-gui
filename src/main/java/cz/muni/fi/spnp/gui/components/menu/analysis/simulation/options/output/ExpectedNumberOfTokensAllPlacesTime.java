package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;

public class ExpectedNumberOfTokensAllPlacesTime extends OutputOptionTime {

    public ExpectedNumberOfTokensAllPlacesTime() {
        super("Expected # of tokens of all places at time t");
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ExpectedNumberOfTokensAllPlacesTime();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        diagramViewModel.getPlaces().forEach(placeViewModel -> addExpectedNumberOfTokensPlace(result, diagramViewModel, placeViewModel));

        tryAddingClosingBracket(result);
    }

}
