package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels;

/**
 * Represents probability types for the immediate transition.
 */
public enum TransitionProbabilityType {

    Constant("C"),
    Functional("F"),
    PlaceDependent("#");

    private final String symbol;

    TransitionProbabilityType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

}
