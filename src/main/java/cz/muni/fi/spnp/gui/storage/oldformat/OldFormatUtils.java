package cz.muni.fi.spnp.gui.storage.oldformat;

import java.util.Set;

public abstract class OldFormatUtils {

    public static final String NULL_VALUE = "null";

    public static Boolean isRequiredFunction(String functionName) {
        // options and net are not included in the file but created from model
        var requiredFunctions = Set.of("assert", "ac_init", "ac_reach", "ac_final");
        return requiredFunctions.contains(functionName);
    }

    private OldFormatUtils() {
    }

}
