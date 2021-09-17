package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.output;

import cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options.OutputOptionsResult;
import cz.muni.fi.spnp.gui.viewmodel.DiagramViewModel;
import cz.muni.fi.spnp.gui.viewmodel.PlaceViewModel;
import cz.muni.fi.spnp.gui.viewmodel.transition.TransitionViewModel;
import org.apache.commons.lang3.StringUtils;

public abstract class OutputOptionTime extends OutputOptionViewModel {

    private static final String LOOP_TIME_VARIABLE = "loop_time";

    public OutputOptionTime(String title) {
        super(title);

        initTime();
    }

    @Override
    public void addToResult(OutputOptionsResult result, DiagramViewModel diagramViewModel) {
        if (isTimeLoop()) {
            var loopValues = getTime().split(",");
            result.lines.add(String.format("for (%s = %s; %s < %s; %s += %s) {",
                    LOOP_TIME_VARIABLE, loopValues[0], LOOP_TIME_VARIABLE, loopValues[1], LOOP_TIME_VARIABLE, loopValues[2]));
            result.lines.add(String.format("%ssolve(%s);", createIndentation(), LOOP_TIME_VARIABLE));
        } else {
            addSolve(result, getTime());
        }
    }

    protected void tryAddingClosingBracket(OutputOptionsResult result) {
        if (isTimeLoop()) {
            result.lines.add("}");
        }
    }

    private boolean isTimeLoop() {
        return StringUtils.countMatches(getTime(), ",") == 2;
    }

    private String createIndentation() {
        if (isTimeLoop()) {
            return StringUtils.repeat(' ', 4);
        } else {
            return "";
        }
    }

    protected void addExpectedNumberOfTokensPlace(OutputOptionsResult result, DiagramViewModel diagramViewModel, PlaceViewModel placeViewModel) {
        var outFunc = addPlaceTokensFunction(result, diagramViewModel, placeViewModel);
        result.lines.add(String.format("%spr_expected(\"Expected # of tokens of the place %s\", %s);",
                createIndentation(), placeViewModel.getName(), outFunc.getName()));
    }

    protected void addThroughputTransition(OutputOptionsResult result, DiagramViewModel diagramViewModel, TransitionViewModel transition) {
        var outFunc = addTransitionRateFunction(result, diagramViewModel, transition);
        result.lines.add(String.format("%spr_expected(\"Throughput of the transition %s\", %s);",
                createIndentation(), transition.getName(), outFunc.getName()));
    }

    protected void addUtilizationTransition(OutputOptionsResult result, DiagramViewModel diagramViewModel, TransitionViewModel transition) {
        var outFunc = addTransitionEnabledFunction(result, diagramViewModel, transition);
        result.lines.add(String.format("%spr_expected(\"Utilization for the transition %s\", %s);",
                createIndentation(), transition.getName(), outFunc.getName()));
    }

    protected void addProbabilityPlaceIsEmpty(OutputOptionsResult result, DiagramViewModel diagramViewModel, PlaceViewModel placeViewModel) {
        var outFunc = addPlaceIsEmptyFunction(result, diagramViewModel, placeViewModel);
        result.lines.add(String.format("%spr_expected(\"Probability that the place %s is empty\", %s);",
                createIndentation(), placeViewModel.getName(), outFunc.getName()));
    }

}
