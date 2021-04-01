package cz.muni.fi.spnp.gui.components.menu;

import cz.muni.fi.spnp.gui.components.ApplicationComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class MenuComponent extends ApplicationComponent {

    private final MenuBar menuBar;

    public MenuComponent(Model model, Notifications notifications) {
        super(model, notifications);

        menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        menuBar.getMenus().add(menuFile);

        Menu menuEdit = new Menu("Edit");
        menuBar.getMenus().add(menuEdit);

        Menu menuView = new Menu("View");
        menuBar.getMenus().add(menuView);

        Menu menuTools = new Menu("Tools");
        menuBar.getMenus().add(menuTools);

        Menu menuHelp = new Menu("Help");
        menuBar.getMenus().add(menuHelp);
    }

    @Override
    public Node getRoot() {
        return menuBar;
    }
}
