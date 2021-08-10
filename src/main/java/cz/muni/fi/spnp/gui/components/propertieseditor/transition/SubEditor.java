package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class SubEditor<TViewModel> {

    private final List<PropertiesEditorRow> rows;
    protected DiagramViewModel diagramViewModel;
    protected TViewModel viewModel;

    public SubEditor() {
        this.rows = new ArrayList<>();
    }

    protected void addRow(Node left, Node right) {
        rows.add(new PropertiesEditorRow(left, right));
    }

    public List<PropertiesEditorRow> getRows() {
        return rows;
    }

    public DiagramViewModel getDiagramViewModel() {
        return diagramViewModel;
    }

    public TViewModel getViewModel() {
        return viewModel;
    }

    public void bindDiagramViewModel(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
    }

    public void unbindDiagramViewModel() {
        this.diagramViewModel = null;
    }

    public void bindViewModel(TViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void unbindViewModel() {
        this.viewModel = null;
    }

}
