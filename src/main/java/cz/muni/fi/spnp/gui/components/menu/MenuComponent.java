package cz.muni.fi.spnp.gui.components.menu;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.components.menu.views.define.DefinesView;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.notifications.SelectedDiagramChangeListener;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuComponent extends ApplicationComponent implements SelectedDiagramChangeListener {

    private final MenuBar menuBar;
    private final DefinesView definesView;
    private final MenuItem menuItemViewDefines;

    public MenuComponent(Model model, Notifications notifications) {
        super(model, notifications);

        menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        menuBar.getMenus().add(menuFile);

        Menu menuEdit = new Menu("Edit");
        menuBar.getMenus().add(menuEdit);

        Menu menuView = new Menu("View");

        definesView = new DefinesView();
        menuItemViewDefines = new MenuItem("Defines");
        menuItemViewDefines.setDisable(true);
        menuItemViewDefines.setOnAction(this::onViewDefinesClick);
        menuView.getItems().add(menuItemViewDefines);

        menuBar.getMenus().add(menuView);

        Menu menuTools = new Menu("Tools");
        menuBar.getMenus().add(menuTools);

        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().add(menuHelp);

        notifications.addSelectedDiagramChangeListener(this);
    }

    private void onViewDefinesClick(ActionEvent actionEvent) {
        definesView.getStage().showAndWait();
    }

    @Override
    public Node getRoot() {
        return menuBar;
    }

    @Override
    public void onSelectedDiagramChanged(DiagramViewModel diagramViewModel) {
        if (diagramViewModel != null) {
            menuItemViewDefines.setDisable(false);
        }

        definesView.setDiagramViewModel(diagramViewModel);
    }
}
