package simulation.grid;

import simulation.grid.cell.Cell;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.CellFactory;

public class Grid {
    private Cell[][] cellGrid;

    private int numberOfRows;
    private int numberOfColumns;

    public Grid(int numberOfRows, int numberOfColumns, CellFactory cellFactory) {
        this.cellGrid = createCellGrid(numberOfRows, numberOfColumns, cellFactory);
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    //TODO refactor gridmapfactory and move this there later;
    private Cell[][] createCellGrid(int numberOfRows, int numberOfColumns, CellFactory cellFactory) {
        Cell[][] cellGrid = new Cell[numberOfRows][numberOfColumns];

        for (int currentRow = 0; currentRow < numberOfRows; currentRow++) {
            for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
                cellGrid[currentRow][currentColumn] = cellFactory.createCell();
            }
        }

        return cellGrid;
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
