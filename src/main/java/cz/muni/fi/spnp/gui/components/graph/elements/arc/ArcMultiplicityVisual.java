package cz.muni.fi.spnp.gui.components.graph.elements.arc;

import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import javafx.scene.Node;
import javafx.scene.shape.Line;

public interface ArcMultiplicityVisual {

    Node getRoot();

    void update(Line line);

    void setVisible(boolean visible);

    void bindViewModel(ArcViewModel arcViewModel);

    void unbindViewModel(ArcViewModel unbindViewModel);

}
