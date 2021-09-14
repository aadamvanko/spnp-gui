package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public final class EmptyOutputOption extends OutputOptionViewModel {

    public EmptyOutputOption() {
        super("ONLY FOR COPYING");
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
    }

}
