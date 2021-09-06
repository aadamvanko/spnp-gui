package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PropertiesEditorPlaceholder extends ElementPropertiesEditor {

    private final VBox vbox;

    public PropertiesEditorPlaceholder() {
        vbox = new VBox();
        vbox.getChildren().add(new Label("No element to edit."));
        vbox.setAlignment(Pos.CENTER);
    }

    @Override
    protected Class<?> getElementClassForDuplicity() {
        return null;
    }

    @Override
    public Node getRoot() {
        return vbox;
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
    }

    @Override
    public void unbindViewModel() {
    }

}
