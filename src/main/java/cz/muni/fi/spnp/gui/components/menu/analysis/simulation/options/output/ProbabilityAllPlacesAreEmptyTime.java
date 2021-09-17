package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class ProbabilityAllPlacesAreEmptyTime extends OutputOptionTime {

    public ProbabilityAllPlacesAreEmptyTime() {
        super("Probability that all places are empty in time t");
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new ProbabilityAllPlacesAreEmptyTime();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        diagramViewModel.getPlaces().forEach(placeViewModel -> addProbabilityPlaceIsEmpty(result, diagramViewModel, placeViewModel));

        tryAddingClosingBracket(result);
    }

}
