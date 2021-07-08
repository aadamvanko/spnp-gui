package cz.muni.fi.spnp.gui.mappers;

import cz.muni.fi.spnp.core.models.PetriNet;
import cz.muni.fi.spnp.core.transformators.spnp.code.SPNPCode;
import cz.muni.fi.spnp.core.transformators.spnp.options.SPNPOptions;
import cz.muni.fi.spnp.gui.viewmodel.ArcViewModel;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class DiagramMapper {

    private final IncludeMapper includeMapper;
    private final DefineMapper defineMapper;
    private final VariableMapper variableMapper;
    private final InputParameterMapper inputParameterMapper;
    private final FunctionMapper functionMapper;
    private final ElementMapper elementMapper;

    public DiagramMapper() {
        includeMapper = new IncludeMapper();
        defineMapper = new DefineMapper();
        variableMapper = new VariableMapper();
        inputParameterMapper = new InputParameterMapper();
        functionMapper = new FunctionMapper();
        elementMapper = new ElementMapper(functionMapper.getFunctionsMapping());
    }

    public PetriNet createPetriNet(DiagramViewModel diagramViewModel) {
        PetriNet petriNet = new PetriNet();
        diagramViewModel.getFunctions().forEach(function -> petriNet.addFunction(functionMapper.map(function)));
        onlyElements(PlaceViewModel.class, diagramViewModel.getElements()).forEach(place -> petriNet.addPlace(elementMapper.mapPlace(place)));
        onlyElements(TransitionViewModel.class, diagramViewModel.getElements()).forEach(transition -> petriNet.addTransition(elementMapper.mapTransition(transition)));
        onlyElements(ArcViewModel.class, diagramViewModel.getElements()).forEach(arc -> petriNet.addArc(elementMapper.mapArc(arc)));
        return petriNet;
    }

    private <T> Stream<T> onlyElements(Class<T> viewModelClass, List<ElementViewModel> elements) {
        return elements.stream()
                .filter(element -> viewModelClass.isInstance(element))
                .map(viewModelClass::cast);
    }

    public SPNPCode createSPNPCode(DiagramViewModel diagramViewModel) {
        SPNPCode spnpCode = new SPNPCode();

        return spnpCode;
    }

    public SPNPOptions createSPNPOptions(DiagramViewModel diagramViewModel) {
        var spnpOptions = new SPNPOptions(Set.of(), Set.of());

        return spnpOptions;
    }
}
