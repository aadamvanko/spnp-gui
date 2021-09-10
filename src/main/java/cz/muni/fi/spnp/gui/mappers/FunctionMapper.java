package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.core.transformators.spnp.code.FunctionSPNP;
import cz.muni.fi.spnp.gui.components.menu.view.functions.FunctionViewModel;

import java.util.HashMap;
import java.util.Map;

public class FunctionMapper {

    private final Map<FunctionViewModel, FunctionSPNP<?>> functionsMapping;

    public FunctionMapper() {
        functionsMapping = new HashMap<>();
    }

    public Map<FunctionViewModel, FunctionSPNP<?>> getFunctionsMapping() {
        return functionsMapping;
    }

    public FunctionSPNP<?> map(FunctionViewModel functionViewModel) {
        var functionSPNP = mapFunction(functionViewModel);
        functionsMapping.put(functionViewModel, functionSPNP);
        return functionSPNP;
    }

    private FunctionSPNP<?> mapFunction(FunctionViewModel functionViewModel) {
        switch (functionViewModel.getReturnType()) {
            case VOID:
                return new FunctionSPNP<>(
                        functionViewModel.getName(),
                        functionViewModel.getFunctionType(),
                        functionViewModel.getBody(),
                        Void.class
                );

            case INT:
                return new FunctionSPNP<>(
                        functionViewModel.getName(),
                        functionViewModel.getFunctionType(),
                        functionViewModel.getBody(),
                        Integer.class
                );

            case DOUBLE:
                return new FunctionSPNP<>(
                        functionViewModel.getName(),
                        functionViewModel.getFunctionType(),
                        functionViewModel.getBody(),
                        Double.class
                );
        }
        throw new AssertionError("Unsupported function return type " + functionViewModel.getReturnType());
    }

}
