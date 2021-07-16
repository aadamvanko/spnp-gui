package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.core.models.PetriNet;
import cz.muni.fi.spnp.core.transformators.spnp.code.FunctionSPNP;
import cz.muni.fi.spnp.core.transformators.spnp.code.SPNPCode;
import cz.muni.fi.spnp.core.transformators.spnp.options.SPNPOptions;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ViewModelUtils;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;

import java.util.HashSet;

public class DiagramMapper {

    private final DiagramViewModel diagramViewModel;
    private final IncludeMapper includeMapper;
    private final DefineMapper defineMapper;
    private final VariableMapper variableMapper;
    private final InputParameterMapper inputParameterMapper;
    private final FunctionMapper functionMapper;
    private final ElementMapper elementMapper;

    private PetriNet petriNet;
    private SPNPCode spnpCode;
    private SPNPOptions spnpOptions;

    public DiagramMapper(DiagramViewModel diagramViewModel) {
        this.diagramViewModel = diagramViewModel;

        includeMapper = new IncludeMapper();
        defineMapper = new DefineMapper();
        variableMapper = new VariableMapper();
        inputParameterMapper = new InputParameterMapper();
        functionMapper = new FunctionMapper();
        elementMapper = new ElementMapper(functionMapper.getFunctionsMapping());
    }

    public PetriNet createPetriNet() {
        if (petriNet != null) {
            return petriNet;
        }

        petriNet = new PetriNet();
        diagramViewModel.getFunctions().forEach(function -> petriNet.addFunction(functionMapper.map(function)));
        ViewModelUtils.onlyElements(PlaceViewModel.class, diagramViewModel.getElements()).forEach(place -> petriNet.addPlace(elementMapper.mapPlace(place)));
        ViewModelUtils.onlyElements(TransitionViewModel.class, diagramViewModel.getElements()).forEach(transition -> petriNet.addTransition(elementMapper.mapTransition(transition)));
        ViewModelUtils.onlyElements(ArcViewModel.class, diagramViewModel.getElements()).forEach(arc -> petriNet.addArc(elementMapper.mapArc(arc)));
        return petriNet;
    }

    public SPNPCode createSPNPCode() {
        if (spnpCode != null) {
            return spnpCode;
        }

        spnpCode = new SPNPCode();

        diagramViewModel.getIncludes().forEach(include -> spnpCode.addInclude(includeMapper.map(include)));
        diagramViewModel.getDefines().forEach(define -> spnpCode.addDefine(defineMapper.map(define)));
        diagramViewModel.getVariables().forEach(variable -> spnpCode.addVariable(variableMapper.map(variable)));

        var functionsMapping = functionMapper.getFunctionsMapping();
        spnpCode.setAssertFunction((FunctionSPNP<Integer>) functionsMapping.get(diagramViewModel.getFunctionByName("assert")));
        spnpCode.setAcInitFunction((FunctionSPNP<Void>) functionsMapping.get(diagramViewModel.getFunctionByName("ac_init")));
        spnpCode.setAcReachFunction((FunctionSPNP<Void>) functionsMapping.get(diagramViewModel.getFunctionByName("ac_reach")));
        spnpCode.setAcFinalFunction((FunctionSPNP<Void>) functionsMapping.get(diagramViewModel.getFunctionByName("ac_final")));

        return spnpCode;
    }

    public SPNPOptions createSPNPOptions() {
        if (spnpOptions != null) {
            return spnpOptions;
        }

        spnpOptions = new SPNPOptions(new HashSet<>(), new HashSet<>());

        // TODO options

        diagramViewModel.getInputParameters().forEach(inputParameter -> spnpOptions.getInputParameters().add(inputParameterMapper.map(inputParameter)));

        return spnpOptions;
    }
}
