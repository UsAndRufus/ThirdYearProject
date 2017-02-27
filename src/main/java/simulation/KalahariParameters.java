package simulation;

import simulation.density.DensityParameters;

public class KalahariParameters {

    private int numberOfRows, numberOfColumns;
    private double proportionVegetation;
    private double fractionOfCellsToUpdateEveryTick;
    private int years;
    private DensityParameters densityParameters;

    public KalahariParameters(int numberOfRows, int numberOfColumns, double proportionVegetation,
                              double fractionOfCellsToUpdateEveryTick, int years, DensityParameters densityParameters) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.proportionVegetation = proportionVegetation;
        this.fractionOfCellsToUpdateEveryTick = fractionOfCellsToUpdateEveryTick;
        this.years = years;

        this.densityParameters = densityParameters;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public double getProportionVegetation() {
        return proportionVegetation;
    }

    public double getFractionOfCellsToUpdateEveryTick() {
        return fractionOfCellsToUpdateEveryTick;
    }

    public int getYears() {
        return years;
    }

    public DensityParameters getDensityParameters() { return densityParameters; }


}
