module cz.muni.fi {
    requires javafx.controls;
    requires javafx.fxml;
    requires spnp.core.models;
    requires spnp.core.transformators;

    opens cz.muni.fi.spnp.gui to javafx.fxml;
    exports cz.muni.fi.spnp.gui;
    exports cz.muni.fi.spnp.gui.components.graph;
    exports cz.muni.fi.spnp.gui.components.menu.views.includes;
    opens cz.muni.fi.spnp.gui.components.menu.views.includes to java.base;
    exports cz.muni.fi.spnp.gui.components.menu.views.variables;
    opens cz.muni.fi.spnp.gui.components.menu.views.variables to java.base;
    exports cz.muni.fi.spnp.gui.components.menu.views.defines;
    opens cz.muni.fi.spnp.gui.components.menu.views.defines to javafx.base;
    exports cz.muni.fi.spnp.gui.components.menu.views;
    opens cz.muni.fi.spnp.gui.components.menu.views to javafx.fxml;
    exports cz.muni.fi.spnp.gui.storing.loaders;
    opens cz.muni.fi.spnp.gui.storing.loaders to javafx.fxml;
    exports cz.muni.fi.spnp.gui.viewmodel;
    opens cz.muni.fi.spnp.gui.viewmodel to java.base;
}