package cz.muni.fi.spnp.gui.mainwindow;

import cz.muni.fi.spnp.gui.elementsoutline.ElementsOutineView;
import cz.muni.fi.spnp.gui.functions.FunctionsCategoriesView;
import cz.muni.fi.spnp.gui.graph.*;
import cz.muni.fi.spnp.gui.menu.MenuView;
import cz.muni.fi.spnp.gui.projects.ProjectsView;
import cz.muni.fi.spnp.gui.propertieseditor.PropertiesEditorView;
import cz.muni.fi.spnp.gui.quickactions.QuickActionsView;
import cz.muni.fi.spnp.gui.statusbar.StatusBarView;
import cz.muni.fi.spnp.gui.toolbar.ToolbarView;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainWindowController {

    private final BorderPane borderPane;
    private MenuView menuView;
    private ProjectsView projectsView;
    private QuickActionsView quickActionsView;
    private ElementsOutineView elementsOutlineView;
    private FunctionsCategoriesView functionsCategoriesView;
    private StatusBarView statusBarView;
    private ToolbarView toolbarView;
    private PropertiesEditorView propertiesEditorView;

    public MainWindowController() {
        borderPane = new BorderPane();

        borderPane.setTop(createTopPanel());
        borderPane.setLeft(createLeftPanel());
        borderPane.setBottom(createBottomPanel());
        borderPane.setRight(createRightPanel());
        borderPane.setCenter(createCenterPanel());
    }

    private Node createCenterPanel() {
        VBox vbox = new VBox();

        toolbarView = new ToolbarView();
        vbox.getChildren().add(toolbarView.getRoot());

        GraphView graphView = new GraphView();
        addGraphComponents(graphView);
        Layout layout = new RandomLayout(graphView);
        layout.execute();
        vbox.getChildren().add(graphView.getRoot());

        return vbox;
    }

    private void addGraphComponents(GraphView graphView) {

        Model model = graphView.getModel();

        graphView.beginUpdate();

        model.addCell("Cell A", CellType.RECTANGLE);
        model.addCell("Cell B", CellType.RECTANGLE);
        model.addCell("Cell C", CellType.RECTANGLE);
        model.addCell("Cell D", CellType.TRIANGLE);
        model.addCell("Cell E", CellType.TRIANGLE);
        model.addCell("Cell F", CellType.RECTANGLE);
        model.addCell("Cell G", CellType.RECTANGLE);

        model.addEdge("Cell A", "Cell B");
        model.addEdge("Cell A", "Cell C");
        model.addEdge("Cell B", "Cell C");
        model.addEdge("Cell C", "Cell D");
        model.addEdge("Cell B", "Cell E");
        model.addEdge("Cell D", "Cell F");
        model.addEdge("Cell D", "Cell G");

        graphView.endUpdate();

    }

    private Node createRightPanel() {
        VBox vbox = new VBox();
        propertiesEditorView = new PropertiesEditorView();
        vbox.getChildren().add(propertiesEditorView.getRoot());

        functionsCategoriesView = new FunctionsCategoriesView();
        vbox.getChildren().add(functionsCategoriesView.getRoot());
        return vbox;
    }

    private Node createBottomPanel() {
        statusBarView = new StatusBarView();
        return statusBarView.getRoot();
    }

    private Node createLeftPanel() {
        VBox vbox = new VBox();

        projectsView = new ProjectsView();
        vbox.getChildren().add(projectsView.getRoot());

        elementsOutlineView = new ElementsOutineView();
        vbox.getChildren().add(elementsOutlineView.getRoot());
        return vbox;
    }

    private Node createTopPanel() {
        VBox vbox = new VBox();
        menuView = new MenuView();
        vbox.getChildren().add(menuView.getRoot());

        quickActionsView = new QuickActionsView();
        vbox.getChildren().add(quickActionsView.getRoot());
        return vbox;
    }

    public BorderPane getRoot() {
        return borderPane;
    }

}
