package simulation;

import simulation.grid.Grid;
import simulation.grid.cell.factories.CellFactory;
import simulation.grid.cell.factories.CellGridFactory;
import simulation.grid.cell.factories.KalahariCellFactory;

public class Kalahari {

    private Grid grid;

    public Kalahari(int numberOfRows, int numberOfColumns, double proportionVegetation) {
        CellFactory kalahariCellFactory = new KalahariCellFactory(proportionVegetation);
        CellGridFactory cellGridFactory = new CellGridFactory(kalahariCellFactory);
        grid = new Grid(numberOfRows, numberOfColumns, cellGridFactory);
    }

    public void run() {
        grid.printToConsole();
    }

    public void tick() {

    }

    public double calculateNonVegetationToVegetationTransitionProbability(double vegetationDensity,
                                                                          double fractionalVegetationCover,
                                                                          double fractionalVegetationCoverWithRainfall) {
        return 0.0;
    }
}
