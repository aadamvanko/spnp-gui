package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

import java.util.ArrayList;
import java.util.List;

public class OutputOptionsResult {

    public List<FunctionViewModel> functions;
    public List<String> lines;

    public OutputOptionsResult() {
        this.functions = new ArrayList<>();
        this.lines = new ArrayList<>();
        lines.add("// Output options");
    }

}
