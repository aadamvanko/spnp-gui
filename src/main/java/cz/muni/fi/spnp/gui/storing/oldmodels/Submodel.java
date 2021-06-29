package cz.muni.fi.spnp.gui.storing.oldmodels;

import java.util.ArrayList;
import java.util.List;

public class Submodel {
    public String name;
    public String includes;
    public String defines;
    public List<ElementOldFormat> elements;
    public List<FunctionOldFormat> functions;
    public List<VariableOldFormat> variables;

    public Submodel() {
        includes = "";
        defines = "";
        elements = new ArrayList<>();
        functions = new ArrayList<>();
        variables = new ArrayList<>();
    }
}
