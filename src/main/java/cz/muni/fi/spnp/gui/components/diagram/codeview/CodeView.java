package cz.muni.fi.spnp.gui.components.diagram.codeview;

import cz.muni.fi.spnp.core.models.PetriNet;
import cz.muni.fi.spnp.core.transformators.spnp.SPNPTransformator;
import cz.muni.fi.spnp.gui.components.common.UIComponent;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.Model;
import cz.muni.fi.spnp.gui.mappers.DiagramMapper;
import cz.muni.fi.spnp.gui.mappers.MappingException;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

public class CodeView implements UIComponent {

    private final Model model;
    private DiagramViewModel diagramViewModel;
    private TextArea textArea;

    public CodeView(Model model, DiagramViewModel diagramViewModel) {
        this.model = model;
        this.diagramViewModel = diagramViewModel;

        createView();
    }

    private void createView() {
        textArea = new TextArea("text comes here");
        textArea.setEditable(false);
    }

    @Override
    public Node getRoot() {
        return textArea;
    }

    public void prepare() {
        diagramViewModel.needsCodeRefreshProperty().set(false);

        var diagramMapper = new DiagramMapper(model, diagramViewModel);
        PetriNet petriNet = null;
        try {
            petriNet = diagramMapper.createPetriNet();
        } catch (MappingException e) {
            var errorMessage = String.format("Cannot generate CSPL source code because there is at least 1 error:%nElement: %s%nError: %s", e.getElementName(), e.getMessage().split(":")[1]);
            textArea.setText(errorMessage);
            textArea.setStyle("-fx-text-fill: red;");
            return;
        }
        var spnpCode = diagramMapper.createSPNPCode();
        var spnpOptions = diagramMapper.createSPNPOptions();
        var transformator = new SPNPTransformator(spnpCode, spnpOptions);
        var sourceCode = transformator.transform(petriNet);
        textArea.setStyle("-fx-text-fill: black;");
        textArea.setText(sourceCode);
    }

    public void unbindViewModels() {
        diagramViewModel = null;
    }

}
