package cz.muni.fi.spnp.gui.components.mainwindow;

import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the clipboard and holds the copied/cut elements form the graph editor.
 */
public class Clipboard {

    private final List<FunctionViewModel> functions;
    private final List<ElementViewModel> elements;
    private OperationType operationType;

    public Clipboard() {
        functions = new ArrayList<>();
        elements = new ArrayList<>();
        operationType = OperationType.COPY;
    }

    public List<FunctionViewModel> getFunctions() {
        return functions;
    }

    public List<ElementViewModel> getElements() {
        return elements;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public enum OperationType {
        COPY,
        CUT
    }

}
