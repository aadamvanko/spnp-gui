package cz.muni.fi.spnp.gui.storing.loaders;

import cz.muni.fi.spnp.core.models.functions.FunctionType;
import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.variables.VariableType;
import cz.muni.fi.spnp.gui.components.menu.views.defines.DefineViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionReturnType;
import cz.muni.fi.spnp.gui.components.menu.views.functions.FunctionViewModel;
import cz.muni.fi.spnp.gui.components.menu.views.includes.IncludeViewModel;
import cz.muni.fi.spnp.gui.notifications.Notifications;
import cz.muni.fi.spnp.gui.storing.oldmodels.*;
import cz.muni.fi.spnp.gui.viewmodel.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedDistributionType;
import cz.muni.fi.spnp.gui.viewmodel.transition.immediate.*;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.fourvalues.HypoExponentialDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue.ConstantTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue.ExponentialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues.BinomialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues.HyperExponentialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues.NegativeBinomialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues.*;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SubmodelConverter {

    private final Notifications notifications;

    public SubmodelConverter(Notifications notifications) {
        this.notifications = notifications;
    }

    public DiagramViewModel convert(Submodel submodel, ProjectViewModel projectViewModel) {
        var includes = convertIncludes(submodel.includes);
        var defines = convertDefines(submodel.defines);
        var variables = convertVariables(submodel.variables);
        var inputParameters = convertInputParamters(submodel.variables);
        var functions = convertFunctions(submodel.functions);
        var elements = convertElements(submodel.elements, functions);

        var diagram = new DiagramViewModel(notifications, projectViewModel, elements, includes, defines, variables, inputParameters, functions);
        diagram.nameProperty().set(submodel.name);
        return diagram;
    }

    private List<VariableViewModel> convertVariables(List<VariableOldFormat> oldVariables) {
        return oldVariables
                .stream()
                .filter(Predicate.not(this::isInputParameter))
                .map(this::convertVariable)
                .collect(Collectors.toList());
    }

    private VariableViewModel convertVariable(VariableOldFormat oldVariable) {
        var variable = new VariableViewModel();
        variable.nameProperty().set(oldVariable.name);
        variable.kindProperty().set(convertVariableKind(oldVariable.kind));
        variable.typeProperty().set(convertVariableType(oldVariable.type));
        variable.valueProperty().setValue(oldVariable.value);
        return variable;
    }

    private List<InputParameterViewModel> convertInputParamters(List<VariableOldFormat> oldVariables) {
        return oldVariables
                .stream()
                .filter(this::isInputParameter)
                .map(this::convertInputParameter)
                .collect(Collectors.toList());
    }

    private boolean isInputParameter(VariableOldFormat oldVariable) {
        return oldVariable.userPromptText != null;
    }

    private InputParameterViewModel convertInputParameter(VariableOldFormat oldVariable) {
        assert oldVariable.kind.equals("Parameter") : "Input parameter must have kind Parameter";
        var inputParameter = new InputParameterViewModel();
        inputParameter.nameProperty().set(oldVariable.name);
        inputParameter.userPromptTextProperty().set(oldVariable.userPromptText);
        return inputParameter;
    }

    private VariableType convertVariableKind(String kind) {
        var stringToVariableType = new HashMap<String, VariableType>();
        stringToVariableType.put("Global", VariableType.Global);
        stringToVariableType.put("Parameter", VariableType.Parameter);
        return stringToVariableType.get(kind);
    }

    private VariableDataType convertVariableType(String type) {
        var stringToVariableDataType = new HashMap<String, VariableDataType>();
        stringToVariableDataType.put("int", VariableDataType.INT);
        stringToVariableDataType.put("double", VariableDataType.DOUBLE);
        return stringToVariableDataType.get(type);
    }

    private List<ElementViewModel> convertElements(List<ElementOldFormat> oldElements, List<FunctionViewModel> functions) {
        var oldPlaces = oldElements.stream()
                .filter(oe -> oe instanceof PlaceOldFormat)
                .map(op -> (PlaceOldFormat) op)
                .collect(Collectors.toList());

        var places = oldPlaces.stream()
                .map(op -> (PlaceViewModel) convertPlace(op))
                .collect(Collectors.toList());

        var oldTransitions = oldElements.stream()
                .filter(oe -> oe instanceof TransitionOldFormat)
                .map(oe -> (TransitionOldFormat) oe)
                .collect(Collectors.toList());

        var transitions = oldTransitions.stream()
                .map(ot -> (TransitionViewModel) convertTransition(ot, places, functions))
                .collect(Collectors.toList());

        var oldConnectables = Stream.concat(oldPlaces.stream(), oldTransitions.stream()).collect(Collectors.toList());

        var oldArcs = oldElements.stream()
                .filter(oe -> oe instanceof ArcOldFormat)
                .collect(Collectors.toList());

        var connectables = Stream.concat(places.stream(), transitions.stream()).collect(Collectors.toList());
        var arcs = oldArcs.stream()
                .map(oa -> (ArcViewModel) convertArc((ArcOldFormat) oa, oldConnectables, connectables, functions))
                .collect(Collectors.toList());

        return Stream.concat(connectables.stream(), arcs.stream())
                .collect(Collectors.toList());
    }

    private ElementViewModel convertPlace(PlaceOldFormat oldPlace) {
        var place = new PlaceViewModel();
        place.nameProperty().set(oldPlace.name);
        place.positionXProperty().set(oldPlace.xy.x);
        place.positionYProperty().set(oldPlace.xy.y);
        place.numberOfTokensProperty().set(oldPlace.token);
        return place;
    }

    private ElementViewModel convertTransition(TransitionOldFormat oldTransition, List<PlaceViewModel> places, List<FunctionViewModel> functions) {
        if (oldTransition instanceof ImmediateTransitionOldFormat) {
            var oldImmediate = (ImmediateTransitionOldFormat) oldTransition;
            return convertImmediateTransition(oldImmediate, places, functions);
        } else if (oldTransition instanceof TimedTransitionOldFormat) {
            var oldTimed = (TimedTransitionOldFormat) oldTransition;
            return convertTimedTransition(oldTimed, places, functions);
        }
        throw new IllegalStateException(oldTransition.getClass().toString());
    }

    private ElementViewModel convertImmediateTransition(ImmediateTransitionOldFormat oldImmediate, List<PlaceViewModel> places, List<FunctionViewModel> functions) {
        var immediate = new ImmediateTransitionViewModel();
        immediate.nameProperty().set(oldImmediate.name);
        immediate.positionXProperty().set(oldImmediate.xy.x);
        immediate.positionYProperty().set(oldImmediate.xy.y);
        immediate.priorityProperty().set(oldImmediate.valueTransition);
        immediate.setTransitionProbability(convertImmediateProbability(oldImmediate, places, functions));
        return immediate;
    }

    private ElementViewModel convertTimedTransition(TimedTransitionOldFormat oldTimed, List<PlaceViewModel> places, List<FunctionViewModel> functions) {
        var timed = new TimedTransitionViewModel();
        timed.nameProperty().set(oldTimed.name);
        timed.positionXProperty().set(oldTimed.xy.x);
        timed.positionYProperty().set(oldTimed.xy.y);
        timed.priorityProperty().set(convertPriority(oldTimed.priority));
        timed.timedDistributionTypeProperty().set(convertDistributionType(oldTimed.distribution));
        timed.setTransitionDistribution(createTransitionDistribution(timed.timedDistributionTypeProperty().get(), oldTimed, places, functions));
        return timed;
    }

    private TransitionDistributionViewModel createTransitionDistribution(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed, List<PlaceViewModel> places, List<FunctionViewModel> functions) {
        var probability = convertTimedProbability(oldTimed.choiceInput);
        switch (probability) {
            case Constant:
                return createConstantDistributionViewModel(timedDistributionType, oldTimed);
            case Functional:
                return createFunctionalDistributionViewModel(timedDistributionType, oldTimed, functions);
            case PlaceDependent:
                return createPlaceDependentDistributionViewModel(timedDistributionType, oldTimed, places);
        }
        throw new AssertionError("Unknown probability " + probability);
    }

    private TransitionDistributionViewModel createConstantDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed) {
        switch (timedDistributionType.getNumberOfValuesType()) {
            case ONE:
                return createConstantOneValueDistributionViewModel(timedDistributionType, oldTimed);
            case TWO:
                return createConstantTwoValuesDistributionViewModel(timedDistributionType, oldTimed);
            case THREE:
                return createConstantThreeValuesDistributionViewModel(timedDistributionType, oldTimed);
            case FOUR:
                return createConstantFourValuesDistributionViewModel(timedDistributionType, oldTimed);
        }
        throw new AssertionError("Unknown number of values " + timedDistributionType.getNumberOfValuesType());
    }

    private TransitionDistributionViewModel createConstantOneValueDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed) {
        var first = oldTimed.valueTransition;

        switch (timedDistributionType) {
            case Constant:
                return new ConstantTransitionDistributionViewModel(first);
            case Exponential:
                return new ExponentialTransitionDistributionViewModel(first);
        }
        throw new AssertionError("Unknown single value transition distribution type " + timedDistributionType);
    }

    private TransitionDistributionViewModel createConstantTwoValuesDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed) {
        var first = oldTimed.valueTransition;
        var second = oldTimed.value1Transition;

        switch (timedDistributionType) {
            case Beta:
                return new BetaTransitionDistributionViewModel(first, second);
            case Cauchy:
                return new CauchyTransitionDistributionViewModel(first, second);
            case Erlang:
                return new ErlangTransitionDistributionViewModel(first, second);
            case Gamma:
                return new GammaTransitionDistributionViewModel(first, second);
            case Geometric:
                return new GeometricTransitionDistributionViewModel(first, second);
            case LogarithmicNormal:
                return new LogarithmicNormalTransitionDistributionViewModel(first, second);
            case Pareto:
                return new ParetoTransitionDistributionViewModel(first, second);
            case Poisson:
                return new PoissonTransitionDistributionViewModel(first, second);
            case TruncatedNormal:
                return new TruncatedNormalTransitionDistributionViewModel(first, second);
            case Uniform:
                return new UniformTransitionDistributionViewModel(first, second);
            case Weibull:
                return new WeibullTransitionDistributionViewModel(first, second);
        }
        throw new AssertionError("Unknown two values transition distribution type " + timedDistributionType);
    }

    private TransitionDistributionViewModel createConstantThreeValuesDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed) {
        var first = oldTimed.valueTransition;
        var second = oldTimed.value1Transition;
        var third = oldTimed.value2Transition;

        switch (timedDistributionType) {
            case Binomial:
                return new BinomialTransitionDistributionViewModel(first, second, third);
            case HyperExponential:
                return new HyperExponentialTransitionDistributionViewModel(first, second, third);
            case NegativeBinomial:
                return new NegativeBinomialTransitionDistributionViewModel(first, second, third);
        }
        throw new AssertionError("Unknown three values transition distribution type " + timedDistributionType);
    }

    private TransitionDistributionViewModel createConstantFourValuesDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed) {
        var first = oldTimed.valueTransition;
        var second = oldTimed.value1Transition;
        var third = oldTimed.value2Transition;
        var fourth = oldTimed.value3Transition;

        switch (timedDistributionType) {
            case HypoExponential:
                return new HypoExponentialDistributionViewModel(first, second, third, fourth);
        }
        throw new AssertionError("Unknown four values transition distribution type " + timedDistributionType);
    }

    private TransitionDistributionViewModel createFunctionalDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed, List<FunctionViewModel> functions) {
        switch (timedDistributionType.getNumberOfValuesType()) {
            case ONE:
                return createFunctionalOneValueDistributionViewModel(timedDistributionType, oldTimed, functions);
            case TWO:
                return createFunctionalTwoValuesDistributionViewModel(timedDistributionType, oldTimed, functions);
            case THREE:
                return createFunctionalThreeValuesDistributionViewModel(timedDistributionType, oldTimed, functions);
            case FOUR:
                return createFunctionalFourValuesDistributionViewModel(timedDistributionType, oldTimed, functions);
        }
        throw new AssertionError("Unknown number of values " + timedDistributionType.getNumberOfValuesType());
    }

    private TransitionDistributionViewModel createFunctionalOneValueDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed, List<FunctionViewModel> functions) {
        var first = findFunctionViewModel(functions, oldTimed.valueTransition);
        switch (timedDistributionType) {
            case Constant:
                return new ConstantTransitionDistributionViewModel(first);
            case Exponential:
                return new ExponentialTransitionDistributionViewModel(first);
        }
        throw new AssertionError("Unknown single value transition distribution type " + timedDistributionType);
    }

    private TransitionDistributionViewModel createFunctionalTwoValuesDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed, List<FunctionViewModel> functions) {
        var first = findFunctionViewModel(functions, oldTimed.valueTransition);
        var second = findFunctionViewModel(functions, oldTimed.value1Transition);
        switch (timedDistributionType) {
            case Beta:
                return new BetaTransitionDistributionViewModel(first, second);
            case Cauchy:
                return new CauchyTransitionDistributionViewModel(first, second);
            case Erlang:
                return new ErlangTransitionDistributionViewModel(first, second);
            case Gamma:
                return new GammaTransitionDistributionViewModel(first, second);
            case Geometric:
                return new GeometricTransitionDistributionViewModel(first, second);
            case LogarithmicNormal:
                return new LogarithmicNormalTransitionDistributionViewModel(first, second);
            case Pareto:
                return new ParetoTransitionDistributionViewModel(first, second);
            case Poisson:
                return new PoissonTransitionDistributionViewModel(first, second);
            case TruncatedNormal:
                return new TruncatedNormalTransitionDistributionViewModel(first, second);
            case Uniform:
                return new UniformTransitionDistributionViewModel(first, second);
            case Weibull:
                return new WeibullTransitionDistributionViewModel(first, second);
        }
        throw new AssertionError("Unknown two values transition distribution type " + timedDistributionType);
    }

    private TransitionDistributionViewModel createFunctionalThreeValuesDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed, List<FunctionViewModel> functions) {
        var first = findFunctionViewModel(functions, oldTimed.valueTransition);
        var second = findFunctionViewModel(functions, oldTimed.value1Transition);
        var third = findFunctionViewModel(functions, oldTimed.value2Transition);
        switch (timedDistributionType) {
            case Binomial:
                return new BinomialTransitionDistributionViewModel(first, second, third);
            case HyperExponential:
                return new HyperExponentialTransitionDistributionViewModel(first, second, third);
            case NegativeBinomial:
                return new NegativeBinomialTransitionDistributionViewModel(first, second, third);
        }
        throw new AssertionError("Unknown three values transition distribution type " + timedDistributionType);
    }

    private TransitionDistributionViewModel createFunctionalFourValuesDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed, List<FunctionViewModel> functions) {
        var first = findFunctionViewModel(functions, oldTimed.valueTransition);
        var second = findFunctionViewModel(functions, oldTimed.value1Transition);
        var third = findFunctionViewModel(functions, oldTimed.value2Transition);
        var fourth = findFunctionViewModel(functions, oldTimed.value3Transition);
        switch (timedDistributionType) {
            case HypoExponential:
                return new HypoExponentialDistributionViewModel(first, second, third, fourth);
        }
        throw new AssertionError("Unknown four values transition distribution type " + timedDistributionType);
    }

    private TransitionDistributionViewModel createPlaceDependentDistributionViewModel(TimedDistributionType timedDistributionType, TimedTransitionOldFormat oldTimed, List<PlaceViewModel> places) {
        var distributionViewModel = createConstantDistributionViewModel(timedDistributionType, oldTimed);
        distributionViewModel.distributionTypeProperty().set(TransitionDistributionType.PlaceDependent);
        distributionViewModel.setDependentPlace(findPlaceViewModel(places, oldTimed.placeDependent));
        return distributionViewModel;
    }

    private TransitionDistributionType convertTimedProbability(String choiceInput) {
        var stringToTransitionDistributionType = new HashMap<String, TransitionDistributionType>();
        stringToTransitionDistributionType.put("Constant", TransitionDistributionType.Constant);
        stringToTransitionDistributionType.put("Function", TransitionDistributionType.Functional);
        stringToTransitionDistributionType.put("Place dependent", TransitionDistributionType.PlaceDependent);
        return stringToTransitionDistributionType.get(choiceInput);
    }

    private TimedDistributionType convertDistributionType(String distributionType) {
        var stringToDistributionType = new HashMap<String, TimedDistributionType>();
        stringToDistributionType.put("Beta", TimedDistributionType.Beta);
        stringToDistributionType.put("Binomial", TimedDistributionType.Binomial);
        stringToDistributionType.put("Cauchy", TimedDistributionType.Cauchy);
        stringToDistributionType.put("Constant", TimedDistributionType.Constant);
        stringToDistributionType.put("Erlang", TimedDistributionType.Erlang);
        stringToDistributionType.put("Exponential", TimedDistributionType.Exponential);
        stringToDistributionType.put("Gamma", TimedDistributionType.Gamma);
        stringToDistributionType.put("Geometric", TimedDistributionType.Geometric);
        stringToDistributionType.put("HyperExponential", TimedDistributionType.HyperExponential);
        stringToDistributionType.put("HypoExponential", TimedDistributionType.HypoExponential);
        stringToDistributionType.put("LogNormal", TimedDistributionType.LogarithmicNormal);
        stringToDistributionType.put("Negative binomial", TimedDistributionType.NegativeBinomial);
        stringToDistributionType.put("Pareto", TimedDistributionType.Pareto);
        stringToDistributionType.put("Poisson", TimedDistributionType.Poisson);
        stringToDistributionType.put("Truncated normal", TimedDistributionType.TruncatedNormal);
        stringToDistributionType.put("Uniform", TimedDistributionType.Uniform);
        stringToDistributionType.put("Weibull", TimedDistributionType.Weibull);

        if (!stringToDistributionType.containsKey(distributionType)) {
            throw new AssertionError("Unknown distribution " + distributionType);
        }

        return stringToDistributionType.get(distributionType);
    }

    private TransitionProbabilityViewModel convertImmediateProbability(ImmediateTransitionOldFormat oldImmediate, List<PlaceViewModel> places, List<FunctionViewModel> functions) {
        switch (oldImmediate.choiceInput) {
            case "Constant value":
                var constantProbability = new ConstantTransitionProbabilityViewModel();
                constantProbability.valueProperty().set(parseDoubleOrDefault(oldImmediate.probability, 1));
                return constantProbability;

            case "Place dependent":
                var placeDependentProbability = new PlaceDependentTransitionProbabilityViewModel();
                placeDependentProbability.valueProperty().set(parseDoubleOrDefault(oldImmediate.probability, 1));
                placeDependentProbability.setDependentPlace(findPlaceViewModel(places, oldImmediate.placeDependent));
                return placeDependentProbability;

            case "Function":
                var functionalProbability = new FunctionalTransitionProbabilityViewModel();
                functionalProbability.setFunction(findFunctionViewModel(functions, oldImmediate.probability));
                return functionalProbability;

            default:
                throw new AssertionError(oldImmediate.choiceInput);
        }
    }

    private double parseDoubleOrDefault(String value, double defaultValue) {
        if (value.equals("null")) {
            return defaultValue;
        } else {
            return Double.parseDouble(value);
        }
    }

    private FunctionViewModel findFunctionViewModel(List<FunctionViewModel> functions, String name) {
        return functions.stream()
                .filter(f -> f.nameProperty().get().equals(name))
                .collect(Collectors.toList())
                .get(0);
    }

    private PlaceViewModel findPlaceViewModel(List<PlaceViewModel> places, String name) {
        return places.stream()
                .filter(p -> p.nameProperty().get().equals(name))
                .collect(Collectors.toList())
                .get(0);
    }

    private int convertPriority(String priority) {
        if (priority.equals("null")) {
            return 0;
        } else {
            return Integer.parseInt(priority);
        }
    }

    private ElementViewModel convertArc(ArcOldFormat oldArc, List<ConnectableOldFormat> oldConnectables, List<ConnectableViewModel> elements, List<FunctionViewModel> functions) {
        var oldSource = oldConnectables.stream()
                .filter(oldConnectable -> oldConnectable.name.equals(oldArc.src) && containsOutputArc(oldConnectable, oldArc.name))
                .collect(Collectors.toList())
                .get(0);
        var oldDestination = oldConnectables.stream()
                .filter(oldConnectable -> oldConnectable.name.equals(oldArc.dest) && oldConnectable.vInputArc.contains(oldArc.name))
                .collect(Collectors.toList())
                .get(0);

        var source = findElementForOld(elements, oldSource);
        var destination = findElementForOld(elements, oldDestination);
        var dragMarks = convertPointsToDragMarks(oldArc.points.subList(1, oldArc.points.size() - 1));

        ArcViewModel arcViewModel;
        if (oldArc.type.equals("Regular")) {
            arcViewModel = new StandardArcViewModel(oldArc.name, source, destination, dragMarks);
        } else if (oldArc.type.equals("Inhibitor")) {
            arcViewModel = new InhibitorArcViewModel(oldArc.name, source, destination, dragMarks);
        } else {
            throw new AssertionError("Unknown arc type " + oldArc.type);
        }


        if (oldArc.choiceInput.equals("Constant")) {
            arcViewModel.multiplicityTypeProperty().set(ArcMultiplicityType.CONSTANT);
            arcViewModel.multiplicityProperty().set(oldArc.multiplicity);
        } else if (oldArc.choiceInput.equals("Function")) {
            arcViewModel.multiplicityTypeProperty().set(ArcMultiplicityType.FUNCTION);
            arcViewModel.multiplicityFunctionProperty().set(findFunctionViewModel(functions, oldArc.multiplicity));
        } else {
            throw new AssertionError("Unknown arc choice input " + oldArc.choiceInput);
        }

        return arcViewModel;
    }

    private boolean containsOutputArc(ConnectableOldFormat oldConnectable, String arcName) {
        return oldConnectable.arcReferences.stream().anyMatch(ar -> ar.arc.equals(arcName)) ||
                oldConnectable.vOutputArc.contains(arcName);
    }

    private List<ArcDragMarkViewModel> convertPointsToDragMarks(List<XY> points) {
        return points.stream().map(xy -> new ArcDragMarkViewModel(xy.x, xy.y)).collect(Collectors.toList());
    }

    private ElementViewModel findElementForOld(List<ConnectableViewModel> elements, ConnectableOldFormat oldConnectable) {
        for (var element : elements) {
            var placeTypeEquality = oldConnectable instanceof PlaceOldFormat && element instanceof PlaceViewModel;
            var transitionTypeEquality = oldConnectable instanceof TransitionOldFormat && element instanceof TransitionViewModel;
            if (element.nameProperty().get().equals(oldConnectable.name) && (placeTypeEquality || transitionTypeEquality)) {
                return element;
            }
        }
        return null;
    }

    private List<IncludeViewModel> convertIncludes(String includes) {
        var lines = includes.split(System.lineSeparator());
        return Stream.of(lines)
                .filter(line -> !line.isBlank())
                .map(this::lineToInclude)
                .collect(Collectors.toList());
    }

    private IncludeViewModel lineToInclude(String line) {
        var stripped = line.strip();
        var tokens = stripped.split("\\s+", 2);
        return new IncludeViewModel(tokens[1]);
    }

    private List<DefineViewModel> convertDefines(String oldDefines) {
        var lines = oldDefines.split(System.lineSeparator());
        return Stream.of(lines)
                .filter(line -> !line.isBlank())
                .map(this::lineToDefine)
                .collect(Collectors.toList());
    }

    private DefineViewModel lineToDefine(String line) {
        var stripped = line.strip();
        var tokens = stripped.split("\\s+", 3);
        return new DefineViewModel(tokens[1], tokens[2]);
    }

    private List<FunctionViewModel> convertFunctions(List<FunctionOldFormat> oldFunctions) {
        return oldFunctions.stream()
                .map(old -> new FunctionViewModel(old.name, convertFunctionKind(old.kind), old.body, convertFunctionReturnType(old.returnType)))
                .collect(Collectors.toList());
    }

    private FunctionReturnType convertFunctionReturnType(String returnType) {
        var stringToReturnType = new HashMap<String, FunctionReturnType>();
        stringToReturnType.put("void", FunctionReturnType.VOID);
        stringToReturnType.put("double", FunctionReturnType.DOUBLE);
        stringToReturnType.put("int", FunctionReturnType.INT);
        return stringToReturnType.get(returnType);
    }

    private FunctionType convertFunctionKind(String kind) {
        var stringToFunctionType = new HashMap<String, FunctionType>();
        stringToFunctionType.put("spnp", FunctionType.Other); // TODO change back to SPNP ???
        stringToFunctionType.put("guard", FunctionType.Guard);
        stringToFunctionType.put("global", FunctionType.Generic); // ???
        stringToFunctionType.put("probability", FunctionType.Probability);
        stringToFunctionType.put("generic", FunctionType.Generic);
        stringToFunctionType.put("reward", FunctionType.Reward);
        stringToFunctionType.put("cardinality", FunctionType.ArcCardinality);
        stringToFunctionType.put("distribution", FunctionType.Distribution);
        stringToFunctionType.put("halting", FunctionType.Halting);
        return stringToFunctionType.get(kind);
    }
}
