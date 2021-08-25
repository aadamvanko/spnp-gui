package cz.muni.fi.spnp.gui.components.menu.views.general;

import cz.muni.fi.spnp.gui.components.menu.views.DialogMessages;
import cz.muni.fi.spnp.gui.components.menu.views.UIWindowComponent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public abstract class GeneralItemView<TViewModel> extends UIWindowComponent {

    protected final TViewModel viewModel;
    private final GridPane gridPane;
    private final ItemViewMode itemViewMode;
    protected TextField firstTextField;
    protected List<BidirectionalBinding> bindings;
    protected Button buttonOk;
    protected Button buttonCancel;
    protected ObservableList<TViewModel> sourceCollection;

    public GeneralItemView(TViewModel viewModel, ItemViewMode itemViewMode) {
        this.viewModel = viewModel;
        this.itemViewMode = itemViewMode;
        bindings = new ArrayList<>();

        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        var buttonsPanel = new HBox();
        buttonsPanel.setSpacing(5);
        buttonOk = new Button("Ok");
        buttonsPanel.getChildren().add(buttonOk);

        buttonOk.setOnMouseClicked(mouseEvent -> {
            if (!isValidViewModel()) {
                return;
            }

            if (itemViewMode == ItemViewMode.ADD) {
                if (sourceCollection.contains(viewModel)) {
                    DialogMessages.showError("Conflicting name!");
                    return;
                } else {
                    sourceCollection.add(viewModel);
                }
            }
            // TODO prevent editing name to same

            unbindProperties();
            stage.close();
        });

        buttonCancel = new Button("Cancel");
        buttonsPanel.getChildren().add(buttonCancel);

        buttonCancel.setOnMouseClicked(mouseEvent -> {
            sourceCollection = null;
            unbindProperties();
            stage.close();
        });

        var vbox = new VBox();
        vbox.getChildren().add(gridPane);
        vbox.getChildren().add(buttonsPanel);
        vbox.setPadding(new Insets(5));
        vbox.setSpacing(5);

        var scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setMinWidth(300);
        stage.setResizable(false);
    }

    private void unbindProperties() {
        bindings.forEach(BidirectionalBinding::unbind);
        bindings.clear();
    }

    protected boolean isValidViewModel() {
        // TODO abstract may be better because some fields can be empty sometimes (define's expression, ...)
        return !anyBlankValues();
    }

    protected void addRowTextField(String labelText, StringProperty dataSource) {
        Label label = new Label(labelText);
        TextField textField = new TextField();
        textField.textProperty().bindBidirectional(dataSource);
        GridPane.setHgrow(textField, Priority.ALWAYS);
        gridPane.addRow(gridPane.getRowCount(), label, textField);
        bindings.add(new BidirectionalBinding<>(textField.textProperty(), dataSource));

        if (firstTextField == null) {
            firstTextField = textField;
        }
    }

    protected <TEnum extends Enum<TEnum>> void addRowEnumChoiceBox(String labelText, ObjectProperty<TEnum> dataSource, Class<TEnum> enumClass) {
        Label label = new Label(labelText);
        var enumValues = FXCollections.observableArrayList(enumClass.getEnumConstants());
        ChoiceBox<TEnum> choiceBox = new ChoiceBox<>(enumValues);
        choiceBox.valueProperty().bindBidirectional(dataSource);
        GridPane.setHgrow(choiceBox, Priority.ALWAYS);
        if (firstTextField != null) {
            choiceBox.prefWidthProperty().bind(firstTextField.widthProperty());
        }
        gridPane.addRow(gridPane.getRowCount(), label, choiceBox);
        bindings.add(new BidirectionalBinding<>(choiceBox.valueProperty(), dataSource));
    }

    public void setSourceCollection(ObservableList<TViewModel> sourceCollection) {
        this.sourceCollection = sourceCollection;
    }

    public TViewModel getViewModel() {
        return viewModel;
    }

    protected boolean anyBlankValues() {
        return bindings.stream()
                .filter(binding -> binding.second instanceof StringProperty)
                .anyMatch(binding -> ((StringProperty) binding.second).get().isBlank());
    }

}
