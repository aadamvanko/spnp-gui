package cz.muni.fi.spnp.gui.components.functions;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.gui.components.common.TreeViewContainer;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.DisplayableViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelCopyFactory;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionCategoryViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionView;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionsView;
import cz.muni.fi.spnp.gui.components.menu.view.general.ItemViewMode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FunctionsCategoriesComponent extends TreeViewContainer<FunctionViewModel> {

    private final ListChangeListener<? super FunctionViewModel> onFunctionsChangedListener;
    private final Map<FunctionType, TreeItem<DisplayableViewModel>> categories;
    private final ChangeListener<FunctionType> onFunctionTypeChangedListener;

    public FunctionsCategoriesComponent(Model model) {
        super(model, "Functions");

        this.onFunctionsChangedListener = this::onFunctionsChangedListener;
        this.onFunctionTypeChangedListener = this::onFunctionTypeChangedListener;

        categories = new HashMap<>();
        Arrays.stream(FunctionType.values())
                .forEach(functionType -> categories.put(functionType, createItem(new FunctionCategoryViewModel(functionType.toString()))));
        categories.values().forEach(treeItemCategory -> treeItemRoot.getChildren().add(treeItemCategory));

        model.selectedDiagramProperty().addListener(this::onSelectedDiagramChangedListener);
    }

    public void onSelectedDiagramChangedListener(ObservableValue<? extends DiagramViewModel> observableValue,
                                                 DiagramViewModel oldDiagramViewModel, DiagramViewModel newDiagramViewModel) {
        categories.values().forEach(categoryTreeItem -> categoryTreeItem.getChildren().clear());

        if (oldDiagramViewModel != null) {
            oldDiagramViewModel.getFunctions().removeListener(this.onFunctionsChangedListener);
            oldDiagramViewModel.getFunctions().forEach(this::removeFunctionTypeListener);
        }

        if (newDiagramViewModel != null) {
            newDiagramViewModel.getFunctions().addListener(this.onFunctionsChangedListener);
            newDiagramViewModel.getFunctions().forEach(functionViewModel -> {
                addItemToTree(functionViewModel);
                addFunctionTypeListener(functionViewModel);
            });
        }
    }

    private void addFunctionTypeListener(FunctionViewModel functionViewModel) {
        functionViewModel.functionTypeProperty().addListener(this.onFunctionTypeChangedListener);
    }

    private void removeFunctionTypeListener(FunctionViewModel functionViewModel) {
        functionViewModel.functionTypeProperty().removeListener(this.onFunctionTypeChangedListener);
    }

    private void onFunctionTypeChangedListener(ObservableValue<? extends FunctionType> observableValue, FunctionType oldValue, FunctionType newValue) {
        System.out.println("You should not see this message from functions categories component! Changing function category must be implemented also here!");
    }

    public void onFunctionsChangedListener(ListChangeListener.Change<? extends FunctionViewModel> functionsChange) {
        while (functionsChange.next()) {
            functionsChange.getRemoved().forEach(removedFunction -> {
                categories.values().forEach(categoryTreeItem ->
                        categoryTreeItem.getChildren().removeIf(functionTreeItem -> functionsChange.getRemoved().contains(functionTreeItem.getValue()))
                );
                removeFunctionTypeListener(removedFunction);
            });

            functionsChange.getAddedSubList().forEach(functionViewModel -> {
                addItemToTree(functionViewModel);
                addFunctionTypeListener(functionViewModel);
            });
        }
    }

    @Override
    protected void addItemToTree(FunctionViewModel functionViewModel) {
        var functionTreeItem = createItem(functionViewModel);
        var categoryTreeItem = categories.get(functionViewModel.getFunctionType());
        categoryTreeItem.getChildren().add(functionTreeItem);
    }

    @Override
    protected EventHandler<? super MouseEvent> getOnItemMouseClickHandler() {
        return mouseEvent -> {
            var sourceItem = ((TreeCell<DisplayableViewModel>) mouseEvent.getSource()).getItem();
            if (sourceItem instanceof FunctionViewModel && mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
                var viewModelCopyFactory = new ViewModelCopyFactory();
                var selectedFunction = (FunctionViewModel) sourceItem;
                var copy = viewModelCopyFactory.createCopy(selectedFunction);
                var functionView = new FunctionView(model.selectedDiagramProperty().get(), copy, selectedFunction, ItemViewMode.EDIT);
                functionView.getStage().setTitle("Edit Function");
                functionView.getStage().showAndWait();
            } else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
                var functionsView = new FunctionsView();
                functionsView.bindDiagramViewModel(model.selectedDiagramProperty().get());
                functionsView.getStage().showAndWait();
            }
        };
    }

}
