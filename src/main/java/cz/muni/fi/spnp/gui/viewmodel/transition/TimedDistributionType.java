package cz.muni.fi.spnp.gui.viewmodel.transition;

import cz.muni.fi.spnp.gui.viewmodel.transition.timed.distributions.NumberOfValuesType;

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
