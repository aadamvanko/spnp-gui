package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.ArcViewModel;
import javafx.scene.Node;
import javafx.scene.shape.Line;

public interface ArcMultiplicityVisual {

    Node getRoot();

    void update(Line line);

    void setVisible(boolean visible);

    void bindViewModel(ArcViewModel arcViewModel);

    void unbindViewModel(ArcViewModel unbindViewModel);

}
