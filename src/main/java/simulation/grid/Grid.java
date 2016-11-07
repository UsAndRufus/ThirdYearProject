package simulation.grid;

import simulation.grid.cell.Cell;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.CellGridFactory;

public class Grid {
    private Cell[][] cellGrid;

    private int numberOfRows;
    private int numberOfColumns;

    public Grid(int numberOfRows, int numberOfColumns, CellGridFactory cellGridFactory) {
        this.cellGrid = cellGridFactory.createNewCellGrid(numberOfRows, numberOfColumns);
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    public Cell getCell(Position position) {
        return null;
    }

    // Temporary printing method, remove or move to other class later
    public void printToConsole() {
        int numberOfVegetationCells = 0;
        int totalNumberOfCells = 0;

        for (int currentRow = 0; currentRow < numberOfRows; currentRow++) {
            for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
                if (cellGrid[currentRow][currentColumn] instanceof Vegetation) {
                    System.out.print("[~]");
                    numberOfVegetationCells++;
                    totalNumberOfCells++;
                } else {
                    System.out.print("[ ]");
                    totalNumberOfCells++;
                }
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("" + numberOfVegetationCells + "/" + totalNumberOfCells + " vegetation/total");
    }
}
