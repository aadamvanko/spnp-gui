package cz.muni.fi.spnp.gui.mainwindow;

import cz.muni.fi.spnp.gui.graph.*;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainWindowController {

    private BorderPane borderPane;

    public MainWindowController() {
        borderPane = new BorderPane();

        borderPane.setTop(createTopPanel());
        borderPane.setLeft(createLeftPanel());
        borderPane.setBottom(createBottomPanel());
        borderPane.setRight(createRightPanel());
        borderPane.setCenter(createCenterPanel());
    }

    private Node createCenterPanel() {
        HBox hboxGraphToolbar = new HBox();
        hboxGraphToolbar.getChildren().add(new Button("cursor"));
        hboxGraphToolbar.getChildren().add(new Button("mover"));

        Graph graph = new Graph();
        addGraphComponents(graph);
        Layout layout = new RandomLayout(graph);
        layout.execute();

        VBox vbox = new VBox();
        vbox.getChildren().add(hboxGraphToolbar);
        vbox.getChildren().add(graph.getScrollPane());
        return vbox;
    }

    private void addGraphComponents(Graph graph) {

        Model model = graph.getModel();

        graph.beginUpdate();

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

        graph.endUpdate();

    }

    private Node createRightPanel() {
        TableView tableViewProperties = new TableView();
        tableViewProperties.setPlaceholder(new Label("No rows to display"));

        TreeItem treeItemCategoryGeneral = new TreeItem("General");
        treeItemCategoryGeneral.getChildren().add(new TreeItem("general_function_1"));
        treeItemCategoryGeneral.getChildren().add(new TreeItem("general_function_2"));

        TreeItem treeItemCategoryGuard = new TreeItem("Guard");
        treeItemCategoryGuard.getChildren().add(new TreeItem("guard_function_1"));
        treeItemCategoryGuard.getChildren().add(new TreeItem("guard_function_2"));

        TreeView treeViewFunctions = new TreeView();
        TreeItem treeItemRoot = new TreeItem("Functions");
        treeItemRoot.getChildren().add(treeItemCategoryGeneral);
        treeItemRoot.getChildren().add(treeItemCategoryGuard);
        treeViewFunctions.setRoot(treeItemRoot);


        VBox vbox = new VBox();
        vbox.getChildren().add(tableViewProperties);
        vbox.getChildren().add(treeViewFunctions);
        return vbox;
    }

    private Node createBottomPanel() {
        TextArea textArea = new TextArea();
        textArea.setPrefRowCount(1);
        textArea.setEditable(false);
        textArea.setText("All went good");
        return textArea;
    }

    private Node createLeftPanel() {
        TreeItem treeItemProject1 = new TreeItem("Project 1");
        treeItemProject1.getChildren().add(new TreeItem("Diagram 1"));
        treeItemProject1.getChildren().add(new TreeItem("Diagram 2"));
        treeItemProject1.getChildren().add(new TreeItem("Diagram 3"));


        TreeItem treeItemProject2 = new TreeItem("Project 2");
        treeItemProject2.getChildren().add(new TreeItem("Diagram 4"));
        treeItemProject2.getChildren().add(new TreeItem("Diagram 5"));

        TreeView treeViewProjects = new TreeView();
        TreeItem treeItemRoot = new TreeItem("Projects");
        treeItemRoot.getChildren().add(treeItemProject1);
        treeItemRoot.getChildren().add(treeItemProject2);
        treeViewProjects.setRoot(treeItemRoot);

        TreeItem treeItemDiagram1Elements = new TreeItem("Diagram 1");
        treeItemDiagram1Elements.getChildren().add(new TreeItem("Node A"));
        treeItemDiagram1Elements.getChildren().add(new TreeItem("Node B"));
        treeItemDiagram1Elements.getChildren().add(new TreeItem("Transition 1"));
        treeItemDiagram1Elements.getChildren().add(new TreeItem("Transition 2"));

        TreeView treeViewGraphElements = new TreeView();
        TreeItem treeItemRootGraphElements = new TreeItem("Diagram graph elements");
        treeItemRootGraphElements.getChildren().add(treeItemDiagram1Elements);
        treeViewGraphElements.setRoot(treeItemRootGraphElements);

        VBox vbox = new VBox();
        vbox.getChildren().add(treeViewProjects);
        vbox.getChildren().add(treeViewGraphElements);
        return vbox;
    }

    private Node createTopPanel() {
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        Menu menuTools = new Menu("Tools");
        Menu menuHelp = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menuFile);
        menuBar.getMenus().add(menuEdit);
        menuBar.getMenus().add(menuView);
        menuBar.getMenus().add(menuTools);
        menuBar.getMenus().add(menuHelp);

        Button buttonNewProject = new Button("new project");

        HBox quickActionsBar = new HBox();
        quickActionsBar.getChildren().add(buttonNewProject);

        VBox vbox = new VBox();
//        vbox.getChildren().add(new Separator(Orientation.HORIZONTAL));
        vbox.getChildren().add(menuBar);
        vbox.getChildren().add(quickActionsBar);
        return vbox;
    }

    public BorderPane getRoot() {
        return borderPane;
    }

}
