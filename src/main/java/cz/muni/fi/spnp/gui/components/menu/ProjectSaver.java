package cz.muni.fi.spnp.gui.components.menu;

import cz.muni.fi.spnp.gui.storing.savers.OldFileSaver;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

public class ProjectSaver {

    private final ProjectViewModel projectViewModel;
    private final Window window;

    public ProjectSaver(Window window, ProjectViewModel projectViewModel) {
        this.window = window;
        this.projectViewModel = projectViewModel;
    }

    public void save() {
        var directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select save directory for project " + projectViewModel.getName());
        var file = directoryChooser.showDialog(window);
        if (file != null) {
            var oldFileSaver = new OldFileSaver();
            oldFileSaver.saveProject(file.getPath(), projectViewModel);
        }

    }

}
