package cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.views;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.models.ArcViewModel;
import javafx.scene.Node;
import javafx.scene.shape.Line;

/**
 * Visual representation of the arc's multiplicity.
 */
public interface ArcMultiplicityVisual {

    /**
     * @return root control of the visual element
     */
    Node getRoot();

    /**
     * Allows update when arc moves.
     *
     * @param line line which is associated with the multiplicity
     */
    void update(Line line);

    /**
     * Allows to change the visibility.
     *
     * @param visible
     */
    void setVisible(boolean visible);

    /**
     * Bind view model.
     *
     * @param arcViewModel view model to bind
     */
    void bindViewModel(ArcViewModel arcViewModel);

    /**
     * Unbind view model.
     */
    void unbindViewModel();

}
