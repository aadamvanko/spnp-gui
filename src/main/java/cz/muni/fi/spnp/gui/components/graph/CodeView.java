package cz.muni.fi.spnp.gui.components.graph;

import cz.muni.fi.spnp.core.transformators.spnp.SPNPTransformator;
import cz.muni.fi.spnp.gui.components.UIComponent;
import cz.muni.fi.spnp.gui.mappers.DiagramMapper;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class CodeView implements UIComponent {

    private DiagramViewModel diagramViewModel;
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

    public void prepare() {
        var diagramMapper = new DiagramMapper(diagramViewModel);
        var petriNet = diagramMapper.createPetriNet();
        var spnpCode = diagramMapper.createSPNPCode();
        var spnpOptions = diagramMapper.createSPNPOptions();
        var transformator = new SPNPTransformator(spnpCode, spnpOptions);
        var sourceCode = transformator.transform(petriNet);
        textArea.setText(sourceCode);
    }

    public void unbindViewModels() {
        diagramViewModel = null;
    }

}
