package cz.muni.fi.spnp.gui.components.graph.canvas;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Scale;

public class ZoomableScrollPane extends ScrollPane {

    private final Group zoomGroup;
    private final Scale scaleTransform;
    private final double delta = 0.1;
    private double scaleValue = 1.0;
    private final BooleanProperty firstRendering;
    private final VoidFunction layoutChangedHandler;
    private boolean shouldCallLayoutChangedHandler;

    public ZoomableScrollPane(Node content, VoidFunction layoutChangedHandler) {
        this.layoutChangedHandler = layoutChangedHandler;

        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(content);
        setContent(contentGroup);

        scaleTransform = new Scale(scaleValue, scaleValue, 0, 0);
        zoomGroup.getTransforms().add(scaleTransform);
        firstRendering = new SimpleBooleanProperty(false);
    }

    public boolean getFirstRendering() {
        return firstRendering.get();
    }

    public BooleanProperty firstRenderingProperty() {
        return firstRendering;
    }

    public Group getZoomGroup() {
        return zoomGroup;
    }

    public double getScaleValue() {
        return scaleValue;
    }

    public void zoomTo(double scaleValue) {
        this.scaleValue = scaleValue;
        double oldHValue = getHvalue();
        double oldVValue = getVvalue();
        scaleTransform.setX(scaleValue);
        scaleTransform.setY(scaleValue);
        setHvalue(oldHValue);
        setVvalue(oldVValue);
    }

    /**
     *
     * @param minimizeOnly
     *            If the content fits already into the viewport, then we don't
     *            zoom if this parameter is true.
     */
    public void zoomToFit(boolean minimizeOnly) {
        double scaleX = getViewportBounds().getWidth() / getContent().getBoundsInLocal().getWidth();
        double scaleY = getViewportBounds().getHeight() / getContent().getBoundsInLocal().getHeight();

        // consider current scale (in content calculation)
        scaleX *= scaleValue;
        scaleY *= scaleValue;

        // distorted zoom: we don't want it => we search the minimum scale
        // factor and apply it
        double scale = Math.min(scaleX, scaleY);

        // check precondition
        if (minimizeOnly) {

            // check if zoom factor would be an enlargement and if so, just set
            // it to 1
            if (Double.compare(scale, 1) > 0) {
                scale = 1;
            }
        }
        // apply zoom
        zoomTo(scale);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        if (!getFirstRendering()) {
            firstRenderingProperty().set(true);
        }

        if (shouldCallLayoutChangedHandler) {
            layoutChangedHandler.call();
        }
    }

    public boolean isShouldCallLayoutChangedHandler() {
        return shouldCallLayoutChangedHandler;
    }

    public void setShouldCallLayoutChangedHandler(boolean shouldCallLayoutChangedHandler) {
        this.shouldCallLayoutChangedHandler = shouldCallLayoutChangedHandler;
    }

}
