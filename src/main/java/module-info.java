module cz.muni.fi {
    requires javafx.controls;
    requires javafx.fxml;
    requires spnp.core.models;
    requires spnp.core.transformators;

    opens cz.muni.fi.spnp.gui to javafx.fxml;
    exports cz.muni.fi.spnp.gui;
    exports cz.muni.fi.spnp.gui.components.graph;
    exports cz.muni.fi.spnp.gui.components.menu.views.defines;
    opens cz.muni.fi.spnp.gui.components.menu.views.defines to javafx.fxml;
    exports cz.muni.fi.spnp.gui.components.menu.views;
    opens cz.muni.fi.spnp.gui.components.menu.views to javafx.fxml;
}