package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views;

import cz.muni.fi.spnp.gui.components.diagram.graph.GraphView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.ConnectableGraphElementView;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.StandardArcViewModel;

import java.util.Objects;

public class StandardArcView extends ArcView {

    public StandardArcView(GraphView graphView, StandardArcViewModel standardArcViewModel, ConnectableGraphElementView from, ConnectableGraphElementView to) {
        super(graphView, standardArcViewModel, Objects.requireNonNull(from), Objects.requireNonNull(to));

        createView();
        bindViewModel();
    }

    private void createView() {
        ending = new ArcEndingArrow(lines.get(0));
        groupSymbols.getChildren().add(ending.getShape());
    }
}
