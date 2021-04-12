package cz.muni.fi.spnp.gui.components.menu.views.diagrams;

import cz.muni.fi.spnp.gui.model.Model;
import cz.muni.fi.spnp.gui.viewmodel.ProjectViewModel;
import javafx.util.StringConverter;

public class ProjectViewModelStringConverter extends StringConverter<ProjectViewModel> {
    private final Model model;

    public ProjectViewModelStringConverter(Model model) {
        this.model = model;
    }

    @Override
    public String toString(ProjectViewModel projectViewModel) {
        if (projectViewModel == null) {
            return null;
        }
        return projectViewModel.nameProperty().get();
    }

    @Override
    public ProjectViewModel fromString(String name) {
        return model.getProject(name);
    }
}
