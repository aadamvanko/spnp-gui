package cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels;

import cz.muni.fi.spnp.gui.components.diagram.graph.elements.transition.viewmodels.timed.distributions.NumberOfValuesType;

/**
 * Represents the types of distribution for timed transition.
 */
public enum TimedDistributionType {
    Beta(NumberOfValuesType.TWO),
    Binomial(NumberOfValuesType.THREE),
    Cauchy(NumberOfValuesType.TWO),
    Constant(NumberOfValuesType.ONE),
    Erlang(NumberOfValuesType.TWO),
    Exponential(NumberOfValuesType.ONE),
    Gamma(NumberOfValuesType.TWO),
    Geometric(NumberOfValuesType.TWO),
    HyperExponential(NumberOfValuesType.THREE),
    HypoExponential(NumberOfValuesType.FOUR),
    LogarithmicNormal(NumberOfValuesType.TWO),
    NegativeBinomial(NumberOfValuesType.THREE),
    Pareto(NumberOfValuesType.TWO),
    Poisson(NumberOfValuesType.TWO),
    TruncatedNormal(NumberOfValuesType.TWO),
    Uniform(NumberOfValuesType.TWO),
    Weibull(NumberOfValuesType.TWO);

    private final NumberOfValuesType numberOfValuesType;

    TimedDistributionType(NumberOfValuesType numberOfValuesType) {
        this.numberOfValuesType = numberOfValuesType;
    }

    public NumberOfValuesType getNumberOfValuesType() {
        return numberOfValuesType;
    }
}
