package cz.muni.fi.spnp.gui.components;

import cz.muni.fi.spnp.gui.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class ViewContainer extends ApplicationComponent {

    protected final String title;
    protected VBox root;

    public ViewContainer(Model model, String title) {
        super(model);

        this.title = title;

        createView();
    }

    private void createView() {
        root = new VBox();

        var labelHeader = new Label(title);
        labelHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
        var fillPane = new Pane();
        HBox.setHgrow(fillPane, Priority.ALWAYS);
        var buttonAddFunction = new Button("+");
        buttonAddFunction.setAlignment(Pos.CENTER_RIGHT);
        var hboxHeader = new HBox();
        hboxHeader.setAlignment(Pos.BASELINE_CENTER);
        hboxHeader.setStyle("-fx-padding: 2px");
        hboxHeader.getChildren().addAll(labelHeader, fillPane, buttonAddFunction);

        root.getChildren().add(hboxHeader);
        root.setStyle("-fx-border-color: gray");
        VBox.setVgrow(root, Priority.ALWAYS);
    }

    @Override
    public Node getRoot() {
        return root;
    }

}
