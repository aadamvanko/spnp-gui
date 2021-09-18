package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

import java.util.ArrayList;
import java.util.List;

public class OutputOptionsResult {

    private final String loopTimeVariableName;
    private final List<FunctionViewModel> functions;
    private final List<String> lines;

    public OutputOptionsResult(String loopTimeVariableName) {
        this.loopTimeVariableName = loopTimeVariableName;

        this.functions = new ArrayList<>();
        this.lines = new ArrayList<>();
        lines.add("// Output options");
    }

    public String getLoopTimeVariableName() {
        return loopTimeVariableName;
    }

    public List<FunctionViewModel> getFunctions() {
        return functions;
    }

    public List<String> getLines() {
        return lines;
    }

}
