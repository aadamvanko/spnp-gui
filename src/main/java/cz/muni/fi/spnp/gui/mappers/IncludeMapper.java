package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.core.transformators.spnp.code.Include;
import cz.muni.fi.spnp.gui.components.menu.view.includes.IncludeViewModel;

/**
 * Maps includes to the general model includes.
 */
public class IncludeMapper {

    public Include map(IncludeViewModel includeViewModel) {
        return new Include(
                includeViewModel.getPath()
        );
    }

}
