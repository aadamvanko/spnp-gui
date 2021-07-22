package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.gui.components.UIComponent;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class CodeView implements UIComponent {

    private final DiagramViewModel diagramViewModel;
    private final TextArea textArea;

    public CodeView(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;
        this.textArea = new TextArea("text comes here");
        textArea.setEditable(false);
    }

    @Override
    public Node getRoot() {
        return textArea;
    }

}
