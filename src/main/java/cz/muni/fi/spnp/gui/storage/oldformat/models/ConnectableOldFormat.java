package cz.muni.fi.spnp.gui.storage.oldformat.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Connectable element in old format.
 */
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
