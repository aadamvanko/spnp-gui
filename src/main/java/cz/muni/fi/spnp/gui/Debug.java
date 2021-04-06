package cz.muni.fi.spnp.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public abstract class Debug {
    public static void addRedBg(Region region) {
        addBg(region, Color.RED);
    }

    public static void addBlueBg(Region region) {
        addBg(region, Color.BLUE);
    }

    public static void addGreenBg(Region region) {
        addBg(region, Color.GREEN);
    }

    public static void addYellowBg(Region region) {
        addBg(region, Color.YELLOW);
    }

    public static void addBg(Region region, Color color) {
        region.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
