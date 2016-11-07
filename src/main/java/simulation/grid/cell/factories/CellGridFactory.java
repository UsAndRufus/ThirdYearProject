package simulation.grid.cell.factories;

import simulation.grid.cell.Cell;

public class CellGridFactory {

    private CellFactory cellFactory;

    public CellGridFactory(CellFactory cellFactory) {
        this.cellFactory = cellFactory;
    }

    public Cell[][] createNewCellGrid(int numberOfRows, int numberOfColumns) {
        Cell[][] cellGrid = new Cell[numberOfRows][numberOfColumns];

        for (int currentRow = 0; currentRow < numberOfRows; currentRow++) {
            for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
                cellGrid[currentRow][currentColumn] = cellFactory.createCell();
            }
        }

        return cellGrid;
    }
}
