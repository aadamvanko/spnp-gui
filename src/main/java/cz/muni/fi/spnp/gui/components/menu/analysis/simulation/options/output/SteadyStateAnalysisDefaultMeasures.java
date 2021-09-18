package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class SteadyStateAnalysisDefaultMeasures extends OutputOptionSteadyState {

    public SteadyStateAnalysisDefaultMeasures() {
        super("Steady-state analysis, default measures");
    }

    @Override
    public OutputOptionViewModel cleanCopy() {
        return new SteadyStateAnalysisDefaultMeasures();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        super.addToResult(result, diagramViewModel);

        result.getLines().add("pr_std_average();");
    }

}
