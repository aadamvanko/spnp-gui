package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import cz.muni.fi.spnp.gui.components.menu.view.UIWindowComponent;
import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;

public class CodePreviewView extends UIWindowComponent {

    private final Model model;
    private final DiagramViewModel diagramViewModel;

    public CodePreviewView(Model model, DiagramViewModel diagramViewModel) {
        this.model = model;
        this.diagramViewModel = diagramViewModel;

        createView();
    }

    private void createView() {
        stage.setTitle("Code preview");
    }

}
