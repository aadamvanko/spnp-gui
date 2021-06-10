package cz.muni.fi.spnp.gui.storing.oldmodels;

import java.util.ArrayList;
import java.util.List;

public class ConnectableOldFormat extends ElementOldFormat {
    public int numberOfConnectedObjects;
    public List<ArcOldFormatReference> arcReferences;
    public List<String> vInputArc;
    public List<String> vOutputArc;
    public XY xy;
    public LabelOldFormat label;

    public ConnectableOldFormat() {
        arcReferences = new ArrayList<>();
    }
}
