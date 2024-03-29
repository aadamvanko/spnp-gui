package cz.muni.fi.spnp.gui.components.mainwindow.fileoperations;

import cz.muni.fi.spnp.gui.components.mainwindow.Model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Saves specific parts of the model to the external file.
 */
public class ModelSaver extends ModelFileOperation {

    public void savePathsSPNP(Model model) {
        try (OutputStream output = new FileOutputStream(SETTINGS_FILENAME)) {
            Properties prop = new Properties();

            prop.setProperty(PATH_SPNP_KEY, model.getPathSPNP());
            prop.setProperty(PATH_SPNP_EXAMPLES_KEY, model.getPathSPNPExamples());
            prop.setProperty(PATH_PLOTS_LIBRARY_KEY, model.getPathPlotsLibrary());

            prop.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
