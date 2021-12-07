package cz.muni.fi.spnp.gui.storage.oldformat.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Submodel in old format.
 */
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
