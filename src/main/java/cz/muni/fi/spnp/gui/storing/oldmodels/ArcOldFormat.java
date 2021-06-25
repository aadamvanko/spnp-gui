package cz.muni.fi.spnp.gui.storing.oldmodels;

import java.util.List;

public class ArcOldFormat extends ElementOldFormat {
    public TwoXY twoXY;
    public String type;
    public String multiplicity;
    public String src;
    public String dest;
    public List<XY> points;
    public boolean isFluid;
    public String choiceInput;

    public Circles circles;
    public String typeIO;
}
