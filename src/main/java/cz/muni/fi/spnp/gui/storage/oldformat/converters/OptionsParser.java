package cz.muni.fi.spnp.gui.storage.oldformat.converters;

import cz.muni.fi.spnp.core.transformators.spnp.options.ConstantValue;
import cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.ConstantValueOptionViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.DoubleOptionViewModel;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.IntegerOptionViewModel;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OptionsParser {

    private final Map<OptionKey, String> optionKeysValues;

    public OptionsParser(FunctionViewModel optionsFunction) {
        optionKeysValues = new HashMap<>();

        Arrays.stream(optionsFunction.getBody().split(System.lineSeparator()))
                .map(String::strip)
                .forEach(this::parseOptionKeyValue);
    }

    private void parseOptionKeyValue(String line) {
        if (!lineContainsOption(line)) {
            return;
        }

        parseOptionFromLine(line);
    }

    private boolean lineContainsOption(String line) {
        return line.startsWith("iopt(") || line.startsWith("fopt(");
    }

    private void parseOptionFromLine(String line) {
        String optionKey;
        String optionValue;
        try {
            var optionCall = line.split(";")[0];
            optionKey = optionCall.split("\\(")[1].split(",")[0].strip();
            optionValue = optionCall.split(",")[1].split("\\)")[0].strip();
        } catch (Exception exception) {
            System.out.println("Unparseable options line " + line + "!");
            return;
        }

        try {
            addOptionKeyValue(OptionKey.valueOf(optionKey), optionValue);
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Unknown option " + optionKey + "!");
        }
    }

    private void addOptionKeyValue(OptionKey optionKey, String optionValue) {
        if (optionKeysValues.containsKey(optionKey)) {
            System.out.println("Two values for option " + optionKey + "! Using the first one.");
        } else {
            optionKeysValues.put(optionKey, optionValue);
        }
    }

    public void parseOption(ConstantValueOptionViewModel constantValueOption) {
        if (!optionKeysValues.containsKey(constantValueOption.getOptionKey())) {
            return;
        }

        var optionValue = optionKeysValues.get(constantValueOption.getOptionKey());
        try {
            constantValueOption.valueProperty().set(ConstantValue.valueOf(optionValue));
            constantValueOption.useProperty().set(true);
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Unknown constant value " + optionValue + " for option " + constantValueOption.getOptionKey() + "!");
        }
    }

    public void parseOption(IntegerOptionViewModel integerOption) {
        if (!optionKeysValues.containsKey(integerOption.getOptionKey())) {
            return;
        }

        var optionValue = optionKeysValues.get(integerOption.getOptionKey());
        try {
            integerOption.valueProperty().set(Integer.parseInt(optionValue));
            integerOption.useProperty().set(true);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Invalid integer value " + optionValue + " for option " + integerOption.getOptionKey() + "!");
        }
    }

    public void parseOption(DoubleOptionViewModel doubleOption) {
        if (!optionKeysValues.containsKey(doubleOption.getOptionKey())) {
            return;
        }

        var optionValue = optionKeysValues.get(doubleOption.getOptionKey());
        try {
            doubleOption.valueProperty().set(Double.parseDouble(optionValue));
            doubleOption.useProperty().set(true);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Invalid double value " + optionValue + " for option " + doubleOption.getOptionKey() + "!");
        }
    }

}
