package cz.muni.fi.spnp.gui.components.propertieseditor.transition;

import cz.muni.fi.spnp.core.models.transitions.distributions.TransitionDistributionType;
import cz.muni.fi.spnp.gui.viewmodel.ElementViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TimedDistributionType;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.TimedTransitionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.TransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.fourvalues.HypoExponentialDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue.ConstantTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.singlevalue.ExponentialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues.BinomialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues.HyperExponentialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.threevalues.NegativeBinomialTransitionDistributionViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.twovalues.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Map;

public class TimedTransitionPropertiesEditor extends TransitionPropertiesEditor {

    private final Map<TransitionDistributionType, TransitionDistributionSubEditor> subEditors;
    private Label distributionTypeLabel;
    private ChoiceBox<TransitionDistributionType> distributionTypeChoiceBox;
    private Label transitionDistributionTypeLabel;
    private ChoiceBox<TimedDistributionType> transitionDistributionTypeChoiceBox;

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
        distributionTypeLabel = new Label("Distribution type:");
        distributionTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(TransitionDistributionType.values()));
        addRow(distributionTypeLabel, distributionTypeChoiceBox);

        transitionDistributionTypeLabel = new Label("Transition distribution type:");
        transitionDistributionTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(TimedDistributionType.values()));
        addRow(transitionDistributionTypeLabel, transitionDistributionTypeChoiceBox);
    }


    private void onDistributionTypeChangedListener(ObservableValue<? extends TransitionDistributionType> observable, TransitionDistributionType oldType, TransitionDistributionType newType) {
        var oldSubEditor = subEditors.get(oldType);
        if (oldSubEditor != null && oldSubEditor.getDiagramViewModel() != null) {
            System.out.println("unbinding old sub editor distribution type");
            oldSubEditor.getRows().forEach(row -> gridPane.getChildren().removeAll(row.getLeft(), row.getRight()));
            oldSubEditor.unbindViewModel();
            oldSubEditor.unbindDiagramViewModel();
        }

        if (newType != null) {
            var timedTransitionViewModel = (TimedTransitionViewModel) viewModel;
            timedTransitionViewModel.setTransitionDistribution(createTransitionDistribution(newType, timedTransitionViewModel.getTransitionDistribution().getEnumType()));

            var newSubEditor = subEditors.get(newType);

            newSubEditor.bindDiagramViewModel(diagramViewModel);
            newSubEditor.bindViewModel(timedTransitionViewModel.getTransitionDistribution());
            newSubEditor.getRows().forEach(row -> addRow(row.getLeft(), row.getRight()));
        }
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

    private void onTransitionDistributionTypeChangedListener(ObservableValue<? extends TimedDistributionType> observable, TimedDistributionType oldType, TimedDistributionType newType) {
        var timedTransitionViewModel = (TimedTransitionViewModel) viewModel;

        var oldSubEditor = subEditors.get(timedTransitionViewModel.getTransitionDistribution().distributionTypeProperty().get());
        if (oldSubEditor.getDiagramViewModel() != null) {
            System.out.println("unbinding old sub editor transition distribution type");
            oldSubEditor.getRows().forEach(row -> gridPane.getChildren().removeAll(row.getLeft(), row.getRight()));
            oldSubEditor.unbindViewModel();
            oldSubEditor.unbindDiagramViewModel();
        }

        if (newType != null) {
            timedTransitionViewModel.setTransitionDistribution(createTransitionDistribution(timedTransitionViewModel.getTransitionDistribution().distributionTypeProperty().get(), newType));

            var newSubEditor = subEditors.get(timedTransitionViewModel.getTransitionDistribution().distributionTypeProperty().get());

            newSubEditor.bindDiagramViewModel(diagramViewModel);
            newSubEditor.bindViewModel(timedTransitionViewModel.getTransitionDistribution());
            newSubEditor.getRows().forEach(row -> addRow(row.getLeft(), row.getRight()));
        }
    }

    @Override
    public void bindViewModel(ElementViewModel viewModel) {
        super.bindViewModel(viewModel);

        var timedTransitionViewModel = (TimedTransitionViewModel) viewModel;
        distributionTypeChoiceBox.getSelectionModel().select(timedTransitionViewModel.getTransitionDistribution().distributionTypeProperty().get());
        transitionDistributionTypeChoiceBox.getSelectionModel().select(timedTransitionViewModel.getTransitionDistribution().getEnumType());
    }

}
