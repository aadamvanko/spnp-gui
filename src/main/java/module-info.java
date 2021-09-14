module cz.muni.fi {
    requires javafx.controls;
    requires javafx.fxml;
    requires spnp.core.models;
    requires spnp.core.transformators;
    requires org.controlsfx.controls;
    requires org.apache.commons.lang3;

    exports cz.muni.fi.spnp.gui;
    exports cz.muni.fi.spnp.gui.components.graph;
    exports cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;
    exports cz.muni.fi.spnp.gui.components.menu.view.includes;
    exports cz.muni.fi.spnp.gui.components.menu.view.variables;
    exports cz.muni.fi.spnp.gui.components.menu.view.defines;
    exports cz.muni.fi.spnp.gui.components.menu.view;
    exports cz.muni.fi.spnp.gui.storing.loaders;
    exports cz.muni.fi.spnp.gui.viewmodel;
    exports cz.muni.fi.spnp.gui.components.propertieseditor;
    exports cz.muni.fi.spnp.gui.components.menu.view.general;

    opens cz.muni.fi.spnp.gui to javafx.fxml;
    opens cz.muni.fi.spnp.gui.components.menu.view.includes to java.base;
    opens cz.muni.fi.spnp.gui.components.menu.view.variables to java.base;
    opens cz.muni.fi.spnp.gui.components.menu.view.defines to javafx.base;
    opens cz.muni.fi.spnp.gui.components.menu.view to javafx.fxml;
    opens cz.muni.fi.spnp.gui.storing.loaders to javafx.fxml;
    opens cz.muni.fi.spnp.gui.viewmodel to java.base;
    opens cz.muni.fi.spnp.gui.components.propertieseditor to javafx.fxml;
    opens cz.muni.fi.spnp.gui.components.menu.view.functions to javafx.base;
    opens cz.muni.fi.spnp.gui.components.menu.view.general to java.base;
}