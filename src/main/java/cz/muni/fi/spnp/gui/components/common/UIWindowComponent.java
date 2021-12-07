package cz.muni.fi.spnp.gui.components.common;

import cz.muni.fi.spnp.gui.App;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UIWindowComponent {

    protected Stage stage;

    public UIWindowComponent() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
    }

    public Stage getStage() {
        return stage;
    }

}
