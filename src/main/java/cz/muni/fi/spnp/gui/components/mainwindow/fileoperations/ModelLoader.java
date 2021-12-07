package cz.muni.fi.spnp.gui.components.mainwindow.fileoperations;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Loads specific parts of the model from the external file.
 */
public class ModelLoader extends ModelFileOperation {

    public void loadSPNPPaths(Model model) {
        if (!Files.exists(Path.of(SETTINGS_FILENAME))) {
            System.out.println("Settings file does not exist. Using default settings.");
            return;
        }

        try (InputStream input = new FileInputStream(SETTINGS_FILENAME)) {
            Properties properties = new Properties();
            properties.load(input);

            model.pathSPNPProperty().set(properties.getProperty(PATH_SPNP_KEY));
            model.pathSPNPExamplesProperty().set(properties.getProperty(PATH_SPNP_EXAMPLES_KEY));
            model.pathPlotsLibraryProperty().set(properties.getProperty(PATH_PLOTS_LIBRARY_KEY));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
