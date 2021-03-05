package cz.muni.fi.spnp.gui.model;

import java.util.HashMap;
import java.util.Map;

public class Model {
    public Map<String, ProjectViewModel> projects;

    public Model() {
        projects = new HashMap<>();
    }
}
