package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.core.models.PetriNet;
import cz.muni.fi.spnp.core.transformators.spnp.code.FunctionSPNP;
import cz.muni.fi.spnp.core.transformators.spnp.code.SPNPCode;
import cz.muni.fi.spnp.core.transformators.spnp.options.*;
import cz.muni.fi.spnp.core.transformators.spnp.parameters.InputParameter;
import cz.muni.fi.spnp.gui.components.diagram.DiagramViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.arc.viewmodels.ArcViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.place.PlaceViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.TransitionViewModel;
import cz.muni.fi.spnp.gui.components.mainwindow.ViewModelUtils;
import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static cz.muni.fi.spnp.core.transformators.spnp.options.OptionKey.*;

/**
 * Maps diagram to the general SRN model.
 */
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

        var inputParameters = new HashSet<InputParameter>();
        diagramViewModel.getInputParameters().forEach(inputParameter -> inputParameters.add(inputParameterMapper.map(inputParameter)));

        var allOptions = new ArrayList<Option>();
        if (diagramViewModel.getSimulationOptions().getIOP_SIMULATION().getValue() == ConstantValue.VAL_NO) {
            allOptions.add(new ConstantTypeOption(IOP_SIMULATION, ConstantValue.VAL_NO));
            allOptions.addAll(mapAnalysisOptions(diagramViewModel.getAnalysisOptions()));
        } else {
            allOptions.addAll(mapSimulationOptions(diagramViewModel.getSimulationOptions()));
        }
        allOptions.addAll(mapIntermediateOptions(diagramViewModel.getIntermediateOptions()));
        allOptions.addAll(mapMiscellaneousOptions(diagramViewModel.getMiscellaneousOptions()));

        return new SPNPOptions(inputParameters, allOptions);
    }

    private Collection<? extends Option> mapSimulationOptions(SimulationOptionsViewModel simulationOptions) {
        var options = new ArrayList<Option>();

        addOptionIfUsed(options, IOP_SIMULATION, simulationOptions.getIOP_SIMULATION());
        addOptionIfUsed(options, IOP_SIM_RUNS, simulationOptions.getIOP_SIM_RUNS());
        addOptionIfUsed(options, IOP_SIM_RUNMETHOD, simulationOptions.getIOP_SIM_RUNMETHOD());
        addOptionIfUsed(options, IOP_SIM_SEED, simulationOptions.getIOP_SIM_SEED());
        addOptionIfUsed(options, IOP_SIM_CUMULATIVE, simulationOptions.getIOP_SIM_CUMULATIVE());
        addOptionIfUsed(options, IOP_SIM_STD_REPORT, simulationOptions.getIOP_SIM_STD_REPORT());
        addOptionIfUsed(options, IOP_SPLIT_LEVEL_DOWN, simulationOptions.getIOP_SPLIT_LEVEL_DOWN());
        addOptionIfUsed(options, IOP_SPLIT_PRESIM, simulationOptions.getIOP_SPLIT_PRESIM());
        addOptionIfUsed(options, IOP_SPLIT_NUMBER, simulationOptions.getIOP_SPLIT_NUMBER());
        addOptionIfUsed(options, IOP_SPLIT_RESTART_FINISH, simulationOptions.getIOP_SPLIT_RESTART_FINISH());
        addOptionIfUsed(options, IOP_SPLIT_PRESIM_RUNS, simulationOptions.getIOP_SPLIT_PRESIM_RUNS());
        addOptionIfUsed(options, FOP_SIM_LENGTH, simulationOptions.getFOP_SIM_LENGTH());
        addOptionIfUsed(options, FOP_SIM_CONFIDENCE, simulationOptions.getFOP_SIM_CONFIDENCE());
        addOptionIfUsed(options, FOP_SIM_ERROR, simulationOptions.getFOP_SIM_ERROR());

        return options;
    }

    private Collection<? extends Option> mapAnalysisOptions(AnalysisOptionsViewModel analysisOptions) {
        var options = new ArrayList<Option>();

        addOptionIfUsed(options, IOP_MC, analysisOptions.getIOP_MC());
        addOptionIfUsed(options, IOP_SSMETHOD, analysisOptions.getIOP_SSMETHOD());
        addOptionIfUsed(options, IOP_SSDETECT, analysisOptions.getIOP_SSDETECT());
        addOptionIfUsed(options, FOP_SSPRES, analysisOptions.getFOP_SSPRES());
        addOptionIfUsed(options, IOP_TSMETHOD, analysisOptions.getIOP_TSMETHOD());
        addOptionIfUsed(options, IOP_CUMULATIVE, analysisOptions.getIOP_CUMULATIVE());
        addOptionIfUsed(options, IOP_SENSITIVITY, analysisOptions.getIOP_SENSITIVITY());
        addOptionIfUsed(options, IOP_ITERATIONS, analysisOptions.getIOP_ITERATIONS());
        addOptionIfUsed(options, FOP_PRECISION, analysisOptions.getFOP_PRECISION());

        return options;
    }

    private void addOptionIfUsed(List<Option> options, OptionKey optionKey, ConstantValueOptionViewModel constantValueOptionViewModel) {
        if (constantValueOptionViewModel.useProperty().get()) {
            options.add(new ConstantTypeOption(optionKey, constantValueOptionViewModel.getValue()));
        }
    }

    private void addOptionIfUsed(List<Option> options, OptionKey optionKey, IntegerOptionViewModel integerOptionViewModel) {
        if (integerOptionViewModel.useProperty().get()) {
            options.add(new IntegerTypeOption(optionKey, integerOptionViewModel.getValue()));
        }
    }

    private void addOptionIfUsed(List<Option> options, OptionKey optionKey, DoubleOptionViewModel doubleOptionViewModel) {
        if (doubleOptionViewModel.useProperty().get()) {
            options.add(new DoubleTypeOption(optionKey, doubleOptionViewModel.getValue()));
        }
    }

    private Collection<? extends Option> mapIntermediateOptions(IntermediateOptionsViewModel intermediateOptions) {
        var options = new ArrayList<Option>();

        addOptionIfUsed(options, IOP_PR_RSET, intermediateOptions.getIOP_PR_RSET());
        addOptionIfUsed(options, IOP_PR_RGRAPH, intermediateOptions.getIOP_PR_RGRAPH());
        addOptionIfUsed(options, IOP_PR_MARK_ORDER, intermediateOptions.getIOP_PR_MARK_ORDER());
        addOptionIfUsed(options, IOP_PR_MERG_MARK, intermediateOptions.getIOP_PR_MERG_MARK());
        addOptionIfUsed(options, IOP_PR_FULL_MARK, intermediateOptions.getIOP_PR_FULL_MARK());
        addOptionIfUsed(options, IOP_USENAME, intermediateOptions.getIOP_USENAME());
        addOptionIfUsed(options, IOP_PR_MC, intermediateOptions.getIOP_PR_MC());
        addOptionIfUsed(options, IOP_PR_DERMC, intermediateOptions.getIOP_PR_DERMC());
        addOptionIfUsed(options, IOP_PR_MC_ORDER, intermediateOptions.getIOP_PR_MC_ORDER());
        addOptionIfUsed(options, IOP_PR_PROB, intermediateOptions.getIOP_PR_PROB());
        addOptionIfUsed(options, IOP_PR_PROBDTMC, intermediateOptions.getIOP_PR_PROBDTMC());
        addOptionIfUsed(options, IOP_PR_DOT, intermediateOptions.getIOP_PR_DOT());

        return options;
    }

    private Collection<? extends Option> mapMiscellaneousOptions(MiscellaneousOptionsViewModel miscellaneousOptions) {
        var options = new ArrayList<Option>();

        addOptionIfUsed(options, IOP_ELIMINATION, miscellaneousOptions.getIOP_ELIMINATION());
        addOptionIfUsed(options, IOP_OK_ABSMARK, miscellaneousOptions.getIOP_OK_ABSMARK());
        addOptionIfUsed(options, IOP_OK_VANLOOP, miscellaneousOptions.getIOP_OK_VANLOOP());
        addOptionIfUsed(options, IOP_OK_TRANS_M0, miscellaneousOptions.getIOP_OK_TRANS_M0());
        addOptionIfUsed(options, IOP_OK_VAN_M0, miscellaneousOptions.getIOP_OK_VAN_M0());
        addOptionIfUsed(options, FOP_ABS_RET_M0, miscellaneousOptions.getFOP_ABS_RET_M0());
        addOptionIfUsed(options, IOP_DEBUG, miscellaneousOptions.getIOP_DEBUG());
        addOptionIfUsed(options, FOP_FLUID_EPSILON, miscellaneousOptions.getFOP_FLUID_EPSILON());
        addOptionIfUsed(options, FOP_TIME_EPSILON, miscellaneousOptions.getFOP_TIME_EPSILON());

        return options;

    }

}
