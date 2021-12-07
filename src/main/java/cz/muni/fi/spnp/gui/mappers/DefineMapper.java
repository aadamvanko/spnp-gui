package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.core.transformators.spnp.code.Define;
import cz.muni.fi.spnp.gui.components.menu.view.defines.DefineViewModel;

/**
 * Maps define view model to the general define class.
 */
public class DefineMapper {

    public Define map(DefineViewModel defineViewModel) {
        return new Define(
                defineViewModel.getName(),
                defineViewModel.getExpression()
        );
    }

}
