package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.UIComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewMode;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;

public class DiagramView implements UIComponent {

    private final Model model;
    private final ChangeListener<DiagramViewMode> onViewModeChangedListener;
    private final GraphView graphView;
    private final CodeView codeView;
    private final Group content;
    private DiagramViewModel diagramViewModel;

    public DiagramView(Notifications notifications, Model model, DiagramViewModel diagramViewModel) {
        this.model = model;
        this.diagramViewModel = diagramViewModel;
        this.onViewModeChangedListener = this::onViewModeChangedListener;
        this.graphView = new GraphView(notifications, model, diagramViewModel);
        this.codeView = new CodeView(diagramViewModel);
        this.content = new Group();
        content.getChildren().add(graphView.getZoomableScrollPane());

        bindViewModel();
    }

    private void onViewModeChangedListener(ObservableValue<? extends DiagramViewMode> observableValue, DiagramViewMode oldValue, DiagramViewMode newValue) {
        if (newValue == DiagramViewMode.GRAPH) {
            content.getChildren().set(0, graphView.getZoomableScrollPane());
        } else {
            content.getChildren().set(0, codeView.getRoot());
        }
    }

    public DiagramViewModel getDiagramViewModel() {
        return diagramViewModel;
    }

    private void bindViewModel() {
        diagramViewModel.viewModeProperty().addListener(this.onViewModeChangedListener);
    }

    public void unbindViewModels() {
        diagramViewModel.viewModeProperty().removeListener(this.onViewModeChangedListener);

        diagramViewModel = null;
    }

    @Override
    public Node getRoot() {
        return content;
    }
}
