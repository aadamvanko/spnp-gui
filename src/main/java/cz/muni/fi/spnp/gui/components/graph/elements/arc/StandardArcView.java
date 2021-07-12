package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElementView;

import java.util.Objects;

public class StandardArcView extends ArcView {

    public StandardArcView(ConnectableGraphElementView from, ConnectableGraphElementView to) {
        super(Objects.requireNonNull(from), Objects.requireNonNull(to));
        createView();
    }

    private void createView() {
        ending = new ArcEndingArrow(lines.get(0));
        groupSymbols.getChildren().add(ending.getShape());
    }
}
