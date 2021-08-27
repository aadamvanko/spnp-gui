package cz.muni.fi.spnp.gui.components;

import cz.muni.fi.spnp.gui.model.Model;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import static java.lang.Double.MAX_VALUE;

public abstract class ViewContainer extends ApplicationComponent {

    protected final String title;
    protected TitledPane root;
    protected Button buttonAdd;

    public ViewContainer(Model model, String title) {
        super(model);

        this.title = title;

        createView();
    }

    private void createView() {
        root = new TitledPane();

        var labelHeader = new Label(title);
        labelHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
        var fillPane = new Pane();
        HBox.setHgrow(fillPane, Priority.ALWAYS);
        buttonAdd = new Button("+");
        buttonAdd.setAlignment(Pos.CENTER_RIGHT);
        var hboxHeader = new HBox();
        hboxHeader.setAlignment(Pos.BASELINE_CENTER);
        hboxHeader.setStyle("-fx-padding: 2px");
        hboxHeader.getChildren().addAll(labelHeader, fillPane, buttonAdd);

        root.contentProperty().set(hboxHeader);
        root.setText(title);
        root.setExpanded(true);
        VBox.setVgrow(root, Priority.ALWAYS);
        HBox.setHgrow(root, Priority.ALWAYS);
        root.setMaxHeight(MAX_VALUE);
        root.expandedProperty().addListener(this::onExpandedChangeListener);
    }

    private void onExpandedChangeListener(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            VBox.setVgrow(root, Priority.ALWAYS);
        } else {
            VBox.setVgrow(root, Priority.NEVER);
        }
    }

    @Override
    public Node getRoot() {
        return root;
    }

}
