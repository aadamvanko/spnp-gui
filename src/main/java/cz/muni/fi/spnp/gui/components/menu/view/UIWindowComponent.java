package cz.muni.fi.spnp.gui.components.menu.view;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class UIWindowComponent {

    protected Stage stage;

    public UIWindowComponent() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    public Stage getStage() {
        return stage;
    }

}
