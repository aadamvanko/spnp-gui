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
import java.util.List;
import java.util.stream.Collectors;

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

    public PetriNet createPetriNet() throws MappingException {
        if (petriNet != null) {
            return petriNet;
        }

        petriNet = new PetriNet();
        diagramViewModel.getFunctions().forEach(function -> petriNet.addFunction(functionMapper.map(function)));
        addMappedPlaces(ViewModelUtils.onlyElements(PlaceViewModel.class, diagramViewModel.getElements()).collect(Collectors.toList()));
        addMappedTransitions(ViewModelUtils.onlyElements(TransitionViewModel.class, diagramViewModel.getElements()).collect(Collectors.toList()));
        addMappedArcs(ViewModelUtils.onlyElements(ArcViewModel.class, diagramViewModel.getElements()).collect(Collectors.toList()));
        return petriNet;
    }

    private void addMappedPlaces(List<PlaceViewModel> places) throws MappingException {
        for (var placeViewModel : places) {
            petriNet.addPlace(elementMapper.mapPlace(placeViewModel));
        }
    }

    private void addMappedTransitions(List<TransitionViewModel> transitions) throws MappingException {
        for (var transitionViewModel : transitions) {
            petriNet.addTransition(elementMapper.mapTransition(transitionViewModel));
        }
    }

    private void addMappedArcs(List<ArcViewModel> arcs) throws MappingException {
        for (var arcViewModel : arcs) {
            petriNet.addArc(elementMapper.mapArc(arcViewModel));
        }
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
