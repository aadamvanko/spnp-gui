package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.components.graph.GraphView;
import cz.muni.fi.spnp.gui.components.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.viewmodel.StandardArcViewModel;

import java.util.Objects;

public class StandardArcView extends ArcView {

    public StandardArcView(GraphView graphView, StandardArcViewModel standardArcViewModel, ConnectableGraphElementView from, ConnectableGraphElementView to) {
        super(graphView, standardArcViewModel, Objects.requireNonNull(from), Objects.requireNonNull(to));

        createView();
    }

    private void createView() {
        ending = new ArcEndingArrow(lines.get(0));
        groupSymbols.getChildren().add(ending.getShape());
    }
}
