package simulation;

import simulation.grid.Grid;
import simulation.grid.cell.factories.CellFactory;
import simulation.grid.cell.factories.GridFactory;
import simulation.grid.cell.factories.KalahariCellFactory;

public class Kalahari {

    private Grid grid;

    public Kalahari(int numberOfRows, int numberOfColumns, double proportionVegetation) {
        CellFactory kalahariCellFactory = new KalahariCellFactory(proportionVegetation);
        GridFactory gridFactory = new GridFactory(kalahariCellFactory);
        grid = new Grid(numberOfRows, numberOfColumns, gridFactory);
    }

    public void run() {
        grid.printToConsole();
    }
}
