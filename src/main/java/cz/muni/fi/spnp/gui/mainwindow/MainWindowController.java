package cz.muni.fi.spnp.gui.mainwindow;

import cz.muni.fi.spnp.gui.elementsoutline.ElementsOutineView;
import cz.muni.fi.spnp.gui.functions.FunctionsCategoriesView;
import cz.muni.fi.spnp.gui.graph.GraphView;
import cz.muni.fi.spnp.gui.graph.elements.arc.ArcController;
import cz.muni.fi.spnp.gui.graph.elements.arc.InhibitorArcController;
import cz.muni.fi.spnp.gui.graph.elements.arc.StandardArcController;
import cz.muni.fi.spnp.gui.graph.elements.place.PlaceController;
import cz.muni.fi.spnp.gui.graph.elements.transition.ImmediateTransitionController;
import cz.muni.fi.spnp.gui.graph.elements.transition.TimedTransitionController;
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
    private GraphView graphView;

    public MainWindowController() {
        borderPane = new BorderPane();

        borderPane.setTop(createTopPanel());
        borderPane.setLeft(createLeftPanel());
        borderPane.setBottom(createBottomPanel());
        borderPane.setRight(createRightPanel());
        borderPane.setCenter(createCenterPanel());
    }

    private Node createCenterPanel() {
        PlaceController pc1 = new PlaceController(100, 100);
        PlaceController pc2 = new PlaceController(500, 100);
        PlaceController pc4 = new PlaceController(100, 200);
        TimedTransitionController ttc1 = new TimedTransitionController(300, 100);
        ArcController ac1 = new StandardArcController(pc1, ttc1);
        ArcController ac2 = new StandardArcController(ttc1, pc2);
        ImmediateTransitionController itc1 = new ImmediateTransitionController(300, 200);
        ArcController ac3 = new InhibitorArcController(pc4, itc1);
        PlaceController pc3 = new PlaceController(100, 500);

        graphView = new GraphView();
        pc1.addToParent(graphView);
        pc2.addToParent(graphView);
        ttc1.addToParent(graphView);
        ac1.addToParent(graphView);
        ac2.addToParent(graphView);
        itc1.addToParent(graphView);
        pc3.addToParent(graphView);
        pc4.addToParent(graphView);
        ac3.addToParent(graphView);

        return graphView.getZoomableScrollPane();
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
