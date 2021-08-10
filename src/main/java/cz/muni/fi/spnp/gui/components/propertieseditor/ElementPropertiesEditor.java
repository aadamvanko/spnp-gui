package cz.muni.fi.spnp.gui.components.propertieseditor;

import cz.muni.fi.spnp.gui.components.UIComponent;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public abstract class ElementPropertiesEditor implements UIComponent {

    protected final GridPane gridPane;
    protected final Label nameLabel;
    protected final TextField nameTextField;

    protected ElementViewModel viewModel;
    protected DiagramViewModel diagramViewModel;

    public ElementPropertiesEditor() {
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(5, 10, 5, 5));

        nameLabel = new Label("Name");
        nameTextField = new TextField();
        GridPane.setHgrow(nameTextField, Priority.ALWAYS);

        addRow(nameLabel, nameTextField);
    }

    protected void addRow(Node left, Node right) {
        if (right != nameTextField) {
            if (right instanceof TextField) {
                ((TextField) right).minWidthProperty().bind(nameTextField.widthProperty());
            } else if (right instanceof ChoiceBox) {
                ((ChoiceBox) right).minWidthProperty().bind(nameTextField.widthProperty());
            }
        }
        gridPane.addRow(gridPane.getRowCount(), left, right);
    }


    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }

    public void unbindDiagramViewModel() {
        this.diagramViewModel = null;
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

    protected ElementViewModel getViewModel() {
        return viewModel;
    }

}
