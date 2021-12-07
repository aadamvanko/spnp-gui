package cz.muni.fi.spnp.gui.components.diagramoutline;

import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.DisplayableViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.InhibitorArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.StandardArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.immediate.ImmediateTransitionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.components.menu.project.ProjectViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionCategoryViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Loader of the icons for the tree items.
 */
public class TreeItemsIconsLoader {

    private final int size;

    public TreeItemsIconsLoader(int size) {
        this.size = size;
    }

    public Node createIcon(DisplayableViewModel displayableViewModel) {
        if (displayableViewModel instanceof PlaceViewModel) {
            return createImageView("place");
        } else if (displayableViewModel instanceof TimedTransitionViewModel) {
            return createImageView("timed_transition");
        } else if (displayableViewModel instanceof ImmediateTransitionViewModel) {
            return createImageView("immediate_transition");
        } else if (displayableViewModel instanceof StandardArcViewModel) {
            return createImageView("standard_arc");
        } else if (displayableViewModel instanceof InhibitorArcViewModel) {
            return createImageView("inhibitor_arc");
        } else if (displayableViewModel instanceof DiagramViewModel) {
            return createImageView("diagram");
        } else if (displayableViewModel instanceof ProjectViewModel) {
            return createImageView("project");
        } else if (displayableViewModel instanceof FunctionViewModel) {
            return createImageView("function");
        } else if (displayableViewModel instanceof FunctionCategoryViewModel) {
            return createImageView("function_category");
        }
        return null;
    }

    private ImageView createImageView(String elementFile) {
        var filename = String.format("%s_%d.png", elementFile, size);
        var image = new Image(TreeItemsIconsLoader.class.getResourceAsStream(filename));
        var imageView = new ImageView(image);
        return imageView;
    }
}
