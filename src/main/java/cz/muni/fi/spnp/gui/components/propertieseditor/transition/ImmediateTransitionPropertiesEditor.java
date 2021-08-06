package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.ImmediateTransitionViewModel;

public class ImmediateTransitionPropertiesEditor extends TransitionPropertiesEditor {

    public ImmediateTransitionPropertiesEditor() {
        createView();
    }

    private void createView() {

    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        var immediateTransitionViewModel = (ImmediateTransitionViewModel) viewModel;
    }

    @Override
    public void unbindViewModel() {
        var immediateTransitionViewModel = (ImmediateTransitionViewModel) viewModel;

        super.unbindViewModel();
    }

}
