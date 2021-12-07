package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.core.transformators.spnp.elements.PolicyAffectedType;
import cz.muni.fi.spnp.gui.components.diagram.graph.common.ElementViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.TimedDistributionType;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.TransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.fourvalues.HypoExponentialDistributionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.singlevalue.ConstantTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.singlevalue.ExponentialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.threevalues.BinomialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.threevalues.HyperExponentialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.threevalues.NegativeBinomialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.models.timed.distributions.twovalues.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class TimedTransitionPropertiesEditor extends TransitionPropertiesEditor {

    private final Map<TransitionDistributionType, TransitionDistributionSubEditor> subEditors;
    private Label policyLabel;
    private ChoiceBox<PolicyAffectedType> policyChoiceBox;
    private Label affectedLabel;
    private ChoiceBox<PolicyAffectedType> affectedChoiceBox;
    private Label distributionTypeLabel;
    private ChoiceBox<TransitionDistributionType> distributionTypeChoiceBox;
    private Label transitionDistributionTypeLabel;
    private ChoiceBox<TimedDistributionType> transitionDistributionTypeChoiceBox;
    private TransitionDistributionSubEditor selectedSubEditor;

    public TimedTransitionPropertiesEditor() {
        createView();

        distributionTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(this::onDistributionTypeChangedListener);
        transitionDistributionTypeChoiceBox.getSelectionModel().selectedItemProperty().addListener(this::onTransitionDistributionTypeChangedListener);

        subEditors = new HashMap<>();
        subEditors.put(TransitionDistributionType.Constant, new ConstantTransitionDistributionSubEditor());
        subEditors.put(TransitionDistributionType.Functional, new FunctionalTransitionDistributionSubEditor());
        subEditors.put(TransitionDistributionType.PlaceDependent, new PlaceDependentTransitionDistributionSubEditor());
    }

    private void createView() {
        policyLabel = new Label("Policy:");
        policyChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(PolicyAffectedType.values()));
        addRow(policyLabel, policyChoiceBox);

        affectedLabel = new Label("Affected:");
        affectedChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(PolicyAffectedType.values()));
        addRow(affectedLabel, affectedChoiceBox);

        transitionDistributionTypeLabel = new Label("Transition distribution type:");
        transitionDistributionTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(TimedDistributionType.values()));
        addRow(transitionDistributionTypeLabel, transitionDistributionTypeChoiceBox);

        distributionTypeLabel = new Label("Distribution type:");
        distributionTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(TransitionDistributionType.values()));
        addRow(distributionTypeLabel, distributionTypeChoiceBox);
    }


    private TransitionDistributionViewModel createTransitionDistribution(TransitionDistributionType transitionDistributionType, TimedDistributionType timedDistributionType) {
        switch (transitionDistributionType) {
            case Constant:
                return createConstantTransitionDistribution(timedDistributionType);
            case Functional:
                return createFunctionalTransitionDistribution(timedDistributionType);
            case PlaceDependent:
                return createPlaceDependentTransitionDistribution(timedDistributionType);
        }
        throw new AssertionError("Unknown transition distribution type " + transitionDistributionType);
    }

    private TransitionDistributionViewModel createConstantTransitionDistribution(TimedDistributionType timedDistributionType) {
        switch (timedDistributionType) {
            case Beta:
                return new BetaTransitionDistributionViewModel();
            case Binomial:
                return new BinomialTransitionDistributionViewModel();
            case Cauchy:
                return new CauchyTransitionDistributionViewModel();
            case Constant:
                return new ConstantTransitionDistributionViewModel();
            case Erlang:
                return new ErlangTransitionDistributionViewModel();
            case Exponential:
                return new ExponentialTransitionDistributionViewModel();
            case Gamma:
                return new GammaTransitionDistributionViewModel();
            case Geometric:
                return new GeometricTransitionDistributionViewModel();
            case HyperExponential:
                return new HyperExponentialTransitionDistributionViewModel();
            case HypoExponential:
                return new HypoExponentialDistributionViewModel();
            case LogarithmicNormal:
                return new LogarithmicNormalTransitionDistributionViewModel();
            case NegativeBinomial:
                return new NegativeBinomialTransitionDistributionViewModel();
            case Pareto:
                return new ParetoTransitionDistributionViewModel();
            case Poisson:
                return new PoissonTransitionDistributionViewModel();
            case TruncatedNormal:
                return new TruncatedNormalTransitionDistributionViewModel();
            case Uniform:
                return new UniformTransitionDistributionViewModel();
            case Weibull:
                return new WeibullTransitionDistributionViewModel();
        }
        throw new AssertionError("Unknown timed transition distribution type " + timedDistributionType);
    }

    private TransitionDistributionViewModel createFunctionalTransitionDistribution(TimedDistributionType timedDistributionType) {
        var transitionDistribution = createConstantTransitionDistribution(timedDistributionType);
        transitionDistribution.distributionTypeProperty().set(TransitionDistributionType.Functional);
        return transitionDistribution;
    }

    private TransitionDistributionViewModel createPlaceDependentTransitionDistribution(TimedDistributionType timedDistributionType) {
        var transitionDistribution = createConstantTransitionDistribution(timedDistributionType);
        transitionDistribution.distributionTypeProperty().set(TransitionDistributionType.PlaceDependent);
        return transitionDistribution;
    }

    private void onDistributionTypeChangedListener(ObservableValue<? extends TransitionDistributionType> observable, TransitionDistributionType oldType, TransitionDistributionType newType) {
        TransitionDistributionViewModel transitionDistributionViewModel = null;
        if (selectedSubEditor != null) {
            transitionDistributionViewModel = selectedSubEditor.getViewModel();
        }

        unbindSelectedSubEditor();
        if (oldType != null && oldType != newType && transitionDistributionViewModel != null && transitionDistributionViewModel == getViewModel().getTransitionDistribution()) {
            getViewModel().setTransitionDistribution(createTransitionDistribution(newType, getViewModel().getTransitionDistribution().getEnumType()));
        }
        bindSelectedSubEditor();
    }

    private void onTransitionDistributionTypeChangedListener(ObservableValue<? extends TimedDistributionType> observable, TimedDistributionType oldType, TimedDistributionType newType) {
        TransitionDistributionViewModel transitionDistributionViewModel = null;
        if (selectedSubEditor != null) {
            transitionDistributionViewModel = selectedSubEditor.getViewModel();
        }

        unbindSelectedSubEditor();
        if (oldType != null && oldType != newType && transitionDistributionViewModel != null && transitionDistributionViewModel == getViewModel().getTransitionDistribution()) {
            getViewModel().setTransitionDistribution(createTransitionDistribution(getViewModel().getTransitionDistribution().distributionTypeProperty().get(), newType));
        }
        bindSelectedSubEditor();
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        policyChoiceBox.valueProperty().bindBidirectional(getViewModel().policyProperty());
        affectedChoiceBox.valueProperty().bindBidirectional(getViewModel().affectedProperty());
        distributionTypeChoiceBox.getSelectionModel().select(getViewModel().getTransitionDistribution().distributionTypeProperty().get());
        transitionDistributionTypeChoiceBox.getSelectionModel().select(getViewModel().getTransitionDistribution().getEnumType());
        bindSelectedSubEditor();
    }

    @Override
    public void unbindViewModel() {
        unbindSelectedSubEditor();
        policyChoiceBox.valueProperty().unbindBidirectional(getViewModel().policyProperty());
        affectedChoiceBox.valueProperty().unbindBidirectional(getViewModel().affectedProperty());

        super.unbindViewModel();
    }

    private void bindSelectedSubEditor() {
        if (selectedSubEditor != null) {
            return;
        }

        selectedSubEditor = subEditors.get(getViewModel().getTransitionDistribution().distributionTypeProperty().get());
        selectedSubEditor.bindDiagramViewModel(diagramViewModel);
        selectedSubEditor.bindViewModel(getViewModel().getTransitionDistribution());
        selectedSubEditor.getRows().forEach(row -> addRow(row.getLeft(), row.getRight()));
    }

    private void unbindSelectedSubEditor() {
        if (selectedSubEditor != null) {
            selectedSubEditor.getRows().forEach(row -> gridPane.getChildren().removeAll(row.getLeft(), row.getRight()));
            selectedSubEditor.unbindViewModel();
            selectedSubEditor.unbindDiagramViewModel();
        }
        selectedSubEditor = null;
    }

    @Override
    protected TimedTransitionViewModel getViewModel() {
        return (TimedTransitionViewModel) viewModel;
    }

}
