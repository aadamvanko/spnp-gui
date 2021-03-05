package cz.muni.fi.spnp.gui.menu;

import cz.muni.fi.spnp.gui.propertieseditor.Component;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class MenuView extends Component {

    private final MenuBar menuBar;

    public MenuView() {
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
