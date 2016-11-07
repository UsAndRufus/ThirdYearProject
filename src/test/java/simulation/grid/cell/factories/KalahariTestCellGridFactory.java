package simulation.grid.cell.factories;

import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;

public class KalahariTestCellGridFactory extends CellGridFactory {
    public KalahariTestCellGridFactory(CellFactory cellFactory) {
        super(cellFactory);
    }

    // Grid looks like this:
    // [~][ ][ ][ ][~]
    // [~][ ][~][ ][ ]
    // [ ][~][~][~][~]
    // [~][ ][ ][~][ ]
    // [ ][~][~][~][ ]
    // [~][~][~][~][~]

    @Override
    public Cell[][] createNewCellGrid(int numberOfRows, int numberOfColumns) {
        int trueNumberOfRows = 6;
        int trueNumberOfColumns = 5;

        Cell[][] cellGrid = new Cell[trueNumberOfRows][trueNumberOfColumns];

        cellGrid[0][0] = new Vegetation();
        cellGrid[0][1] = new NonVegetation();
        cellGrid[0][2] = new NonVegetation();
        cellGrid[0][3] = new NonVegetation();
        cellGrid[0][4] = new Vegetation();

        cellGrid[1][0] = new Vegetation();
        cellGrid[1][1] = new NonVegetation();
        cellGrid[1][2] = new Vegetation();
        cellGrid[1][3] = new NonVegetation();
        cellGrid[1][4] = new NonVegetation();

        cellGrid[2][0] = new NonVegetation();
        cellGrid[2][1] = new Vegetation();
        cellGrid[2][2] = new Vegetation();
        cellGrid[2][3] = new Vegetation();
        cellGrid[2][4] = new Vegetation();

        cellGrid[3][0] = new Vegetation();
        cellGrid[3][1] = new NonVegetation();
        cellGrid[3][2] = new NonVegetation();
        cellGrid[3][3] = new Vegetation();
        cellGrid[3][4] = new NonVegetation();

        cellGrid[4][0] = new NonVegetation();
        cellGrid[4][1] = new Vegetation();
        cellGrid[4][2] = new Vegetation();
        cellGrid[4][3] = new Vegetation();
        cellGrid[4][4] = new NonVegetation();

        cellGrid[5][0] = new Vegetation();
        cellGrid[5][1] = new Vegetation();
        cellGrid[5][2] = new Vegetation();
        cellGrid[5][3] = new Vegetation();
        cellGrid[5][4] = new Vegetation();

        return cellGrid;
    }
}
