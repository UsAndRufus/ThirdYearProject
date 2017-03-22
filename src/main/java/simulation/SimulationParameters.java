package simulation;

public class SimulationParameters {

    private int numberOfRows, numberOfColumns;
    private double fractionOfCellsToUpdateEveryTick;
    private int years;

    public SimulationParameters(int numberOfRows, int numberOfColumns, double fractionOfCellsToUpdateEveryTick, int years) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.fractionOfCellsToUpdateEveryTick = fractionOfCellsToUpdateEveryTick;
        this.years = years;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public double getFractionOfCellsToUpdateEveryTick() {
        return fractionOfCellsToUpdateEveryTick;
    }

    public int getYears() {
        return years;
    }
}
