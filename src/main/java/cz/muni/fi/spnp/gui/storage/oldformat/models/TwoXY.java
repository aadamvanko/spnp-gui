package cz.muni.fi.spnp.gui.storage.oldformat.models;

/**
 * Tuple with two 2D positions.
 */
public class TwoXY {
    public XY p1;
    public XY p2;

    public TwoXY() {
        p1 = new XY();
        p2 = new XY();
    }
}
