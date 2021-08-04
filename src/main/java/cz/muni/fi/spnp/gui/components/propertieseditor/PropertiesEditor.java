package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.components.UIComponent;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public abstract class PropertiesEditor implements UIComponent {

    protected final GridPane gridPane;
    protected final Label nameLabel;
    protected final TextField nameTextField;
    protected ElementViewModel viewModel;

    public PropertiesEditor() {
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(5, 10, 5, 5));

        nameLabel = new Label("Name");
        nameTextField = new TextField();
        GridPane.setHgrow(nameTextField, Priority.ALWAYS);

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);
    }

    public void bindViewModel(ElementViewModel viewModel) {
        this.viewModel = viewModel;
        nameTextField.textProperty().bindBidirectional(viewModel.nameProperty());
    }

    public void unbindViewModel() {
        nameTextField.textProperty().unbindBidirectional(viewModel.nameProperty());
        this.viewModel = null;
    }

    @Override
    public Node getRoot() {
        return gridPane;
    }

}
