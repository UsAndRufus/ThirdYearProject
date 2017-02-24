package simulation;

public class KalahariParameters {

    private int numberOfRows, numberOfColumns;
    private double proportionVegetation;
    private double fractionOfCellsToUpdateEveryTick;
    private int years;
    private double immediacyFactor; // immediacy factor is also known as k
    private int densityDistance;

    public KalahariParameters(int numberOfRows, int numberOfColumns, double proportionVegetation,
                              double fractionOfCellsToUpdateEveryTick, int years, double immediacyFactor,
                              int densityDistance) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.proportionVegetation = proportionVegetation;
        this.fractionOfCellsToUpdateEveryTick = fractionOfCellsToUpdateEveryTick;
        this.years = years;

        // TODO: maybe change these two into DensityParameters tinytype?
        this.immediacyFactor = immediacyFactor;
        this.densityDistance = densityDistance;
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

    public double getImmediacyFactor() {
        return immediacyFactor;
    }

    public int getDensityDistance() { return densityDistance; }


}
