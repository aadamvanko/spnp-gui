package cz.muni.fi.spnp.gui.components.statusbar;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class StatusBarComponent extends ApplicationComponent {

    private final HBox hbox;
    private final TextArea textArea;

    public StatusBarComponent(Model model) {
        super(model);

        textArea = new TextArea();
        textArea.setEditable(false);
        HBox.setHgrow(textArea, Priority.ALWAYS);
        VBox.setVgrow(textArea, Priority.ALWAYS);

        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setScrollTop(Double.MAX_VALUE);
        });

        var buttonClear = new Button();
        buttonClear.setTooltip(new Tooltip("Clear output window"));
        buttonClear.setOnAction(this::onButtonCloseHandler);
        buttonClear.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("clear_18.png"))));
        buttonClear.setPadding(Insets.EMPTY);

        var buttonTop = new Button();
        buttonTop.setTooltip(new Tooltip("Move to top"));
        buttonTop.setOnAction(this::onButtonTopHandler);
        buttonTop.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("top_18.png"))));
        buttonTop.setPadding(Insets.EMPTY);

        var buttonBottom = new Button();
        buttonBottom.setTooltip(new Tooltip("Move to bottom"));
        buttonBottom.setOnAction(this::onButtonBottomHandler);
        buttonBottom.setGraphic(new ImageView(new Image(this.getClass().getResourceAsStream("bottom_18.png"))));
        buttonBottom.setPadding(Insets.EMPTY);

        var buttonsPanel = new VBox(buttonClear, buttonTop, buttonBottom);
        buttonsPanel.setPadding(new Insets(2));
        buttonsPanel.setSpacing(3);

        hbox = new HBox(new Group(buttonsPanel), textArea);
    }

    private void onButtonCloseHandler(ActionEvent actionEvent) {
        textArea.setText("");
    }

    private void onButtonTopHandler(ActionEvent actionEvent) {
        textArea.setScrollTop(Double.MIN_VALUE);
    }

    private void onButtonBottomHandler(ActionEvent actionEvent) {
        textArea.setScrollTop(Double.MAX_VALUE);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    @Override
    public Node getRoot() {
        return hbox;
    }

}
