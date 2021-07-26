package cz.muni.fi.spnp.gui.components.functions;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.diagramoutline.TreeItemsIconsLoader;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionCategoryViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DisplayableViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FunctionsCategoriesComponent extends ApplicationComponent {

    private final ListChangeListener<? super FunctionViewModel> onFunctionsChangedListener;
    private final TreeItemsIconsLoader treeItemsIconsLoader;
    private final Map<FunctionType, TreeItem<DisplayableViewModel>> categories;
    private TreeView<DisplayableViewModel> treeView;
    private TreeItem<DisplayableViewModel> treeItemRoot;
    private VBox root;

    public FunctionsCategoriesComponent(Model model) {
        super(model);

        this.onFunctionsChangedListener = this::onFunctionsChangedListener;
        treeItemsIconsLoader = new TreeItemsIconsLoader(16);

        createView();

        categories = new HashMap<>();
        Arrays.stream(FunctionType.values())
                .forEach(functionType -> categories.put(functionType, createItem(new FunctionCategoryViewModel(functionType.toString()))));
        categories.values().forEach(treeItemCategory -> treeItemRoot.getChildren().add(treeItemCategory));

        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChangedListener);
    }

    public void onSelectedDiagramChangedListener(ObservableValue<? extends DiagramViewModel> observableValue,
                                                 DiagramViewModel oldDiagramViewModel, DiagramViewModel newDiagramViewModel) {
        if (oldDiagramViewModel != null) {
            oldDiagramViewModel.getFunctions().removeListener(this.onFunctionsChangedListener);
        }
        if (newDiagramViewModel != null) {
            newDiagramViewModel.getFunctions().addListener(this.onFunctionsChangedListener);
            newDiagramViewModel.getFunctions().forEach(this::addFunction);
        }
    }

    private void addFunction(FunctionViewModel functionViewModel) {
        var functionTreeItem = createItem(functionViewModel);
        var categoryTreeItem = categories.get(functionViewModel.getFunctionType());
        categoryTreeItem.getChildren().add(functionTreeItem);
    }

    private void createView() {
        treeView = new TreeView<>();
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        treeItemRoot = createItem(new DisplayableViewModel("PROJECTS ROOT"));
        treeView.setRoot(treeItemRoot);
        treeView.setShowRoot(false);
        treeItemRoot.setExpanded(true);

        treeView.setCellFactory(tv -> {
            var cell = new TreeCell<DisplayableViewModel>() {

                @Override
                protected void updateItem(DisplayableViewModel item, boolean empty) {
                    super.updateItem(item, empty);
                    textProperty().unbind();
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        textProperty().bind(item.nameProperty());
                        setGraphic(treeItemsIconsLoader.createIcon(item));
                    }
                }
            };

            cell.setOnMouseClicked(mouseEvent -> {
                var sourceItem = cell.getItem();
                if (sourceItem instanceof FunctionViewModel && mouseEvent.getClickCount() == 2) {
                    // TODO show function view in edit mode
                }
            });

            return cell;
        });

        root = new VBox();

        var labelHeader = new Label("Functions");
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
        root.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        root.setStyle("-fx-border-color: gray");
        VBox.setVgrow(root, Priority.ALWAYS);
    }

    private TreeItem<DisplayableViewModel> createItem(DisplayableViewModel displayableViewModel) {
        TreeItem<DisplayableViewModel> item = new TreeItem<>(displayableViewModel);
        item.setExpanded(true);
        return item;
    }

    public void onFunctionsChangedListener(ListChangeListener.Change<? extends FunctionViewModel> functionsChange) {
        while (functionsChange.next()) {
            functionsChange.getRemoved().forEach(removedViewModel -> {
                categories.values().forEach(categoryTreeItem ->
                        categoryTreeItem.getChildren().removeIf(functionTreeItem -> functionsChange.getRemoved().contains(functionTreeItem.getValue()))
                );
            });

            functionsChange.getAddedSubList().forEach(this::addFunction);
        }
    }

    @Override
    public Node getRoot() {
        return root;
    }
}
