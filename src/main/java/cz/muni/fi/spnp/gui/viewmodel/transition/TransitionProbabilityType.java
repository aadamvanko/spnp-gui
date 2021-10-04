package cz.muni.fi.spnp.gui.viewmodel.transition;

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
