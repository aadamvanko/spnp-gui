module cz.muni.fi {
    requires javafx.controls;
    requires javafx.fxml;
    requires spnp.core.models;
    requires spnp.core.transformators;
    requires org.controlsfx.controls;

    exports cz.muni.fi.spnp.gui;
    exports cz.muni.fi.spnp.gui.components.graph;
    exports cz.muni.fi.spnp.gui.components.menu.views.includes;
    exports cz.muni.fi.spnp.gui.components.menu.views.variables;
    exports cz.muni.fi.spnp.gui.components.menu.views.defines;
    exports cz.muni.fi.spnp.gui.components.menu.views;
    exports cz.muni.fi.spnp.gui.storing.loaders;
    exports cz.muni.fi.spnp.gui.viewmodel;
    exports cz.muni.fi.spnp.gui.components.propertieseditor;

    opens cz.muni.fi.spnp.gui to javafx.fxml;
    opens cz.muni.fi.spnp.gui.components.menu.views.includes to java.base;
    opens cz.muni.fi.spnp.gui.components.menu.views.variables to java.base;
    opens cz.muni.fi.spnp.gui.components.menu.views.defines to javafx.base;
    opens cz.muni.fi.spnp.gui.components.menu.views to javafx.fxml;
    opens cz.muni.fi.spnp.gui.storing.loaders to javafx.fxml;
    opens cz.muni.fi.spnp.gui.viewmodel to java.base;
    opens cz.muni.fi.spnp.gui.components.propertieseditor to javafx.fxml;
    opens cz.muni.fi.spnp.gui.components.menu.views.functions to javafx.base;
    exports cz.muni.fi.spnp.gui.components.menu.views.general;
    opens cz.muni.fi.spnp.gui.components.menu.views.general to java.base;
}