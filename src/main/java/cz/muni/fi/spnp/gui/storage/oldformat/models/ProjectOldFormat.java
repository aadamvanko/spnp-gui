package cz.muni.fi.spnp.gui.storage.oldformat.models;

import java.util.ArrayList;
import java.util.List;

public class ProjectOldFormat {
    public String modelName;
    public String owner;
    public String dateCreated;
    public String comment;
    public List<String> submodelsNames;

    public ProjectOldFormat() {
        submodelsNames = new ArrayList<>();
    }
}
