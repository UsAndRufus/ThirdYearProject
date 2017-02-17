package simulation.grid;

import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.CellGridFactory;

import java.util.*;

// TODO: make iterable
public class Grid {
    private Cell[][] cellGrid;

    private int numberOfRows;
    private int numberOfColumns;

    private double fractionalVegetationCover = -1;

    public Grid(int numberOfRows, int numberOfColumns, CellGridFactory cellGridFactory) {
        this.cellGrid = cellGridFactory.createNewCellGrid(numberOfRows, numberOfColumns);
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    public Cell getCell(Position position) {
        if ((position.getX() >= numberOfColumns) ||
                (position.getX() < 0) ||
                (position.getY() >= numberOfRows) ||
                (position.getY() < 0)) {
            return null;
        }

        return cellGrid[position.getY()][position.getX()];
    }

    public void setCell(Position position, Cell cell) {
        if (getCell(position) == null) {
            throw new IllegalArgumentException("Cannot set cell if position out of bounds");
        }

        if ((getCell(position) instanceof NonVegetation)
                && (cell instanceof Vegetation)) {
            fractionalVegetationCover += (1 / (double) (numberOfRows * numberOfColumns));
        } else if ((getCell(position) instanceof Vegetation)
                && (cell instanceof NonVegetation)) {
            fractionalVegetationCover -= (1 / (double) (numberOfRows * numberOfColumns));
        }

        cellGrid[position.getY()][position.getX()] = cell;
    }

    public List<Position> getRandomPositions(double fractionOfPositionsToChoose) {
        int numberOfPositionsToChoose = (int) (numberOfRows * numberOfColumns * fractionOfPositionsToChoose);

        Set<Position> positions = new HashSet<>(numberOfPositionsToChoose);

        for (int currentCell = 0; currentCell < numberOfPositionsToChoose; currentCell++) {
            Position currentPosition = getRandomPosition();
            while (positions.contains(currentPosition)) {
                currentPosition = getRandomPosition();
            }

            positions.add(currentPosition);
        }

        return new ArrayList<>(positions);
    }

    private Position getRandomPosition() {
        Random random = new Random();

        int randomX = random.nextInt(numberOfColumns);
        int randomY = random.nextInt(numberOfRows);

        return new Position(randomX, randomY);
    }

    public double getFractionalVegetationCover() {

        if (fractionalVegetationCover < 0) {
            int numberOfVegetationCells = 0;

            for(int x = 0; x < numberOfColumns; x++) {
                for (int y =0; y < numberOfRows; y++) {
                    Position currentPosition = new Position(x,y);
                    Cell currentCell = getCell(currentPosition);
                    if (currentCell instanceof Vegetation) {
                        numberOfVegetationCells++;
                    }
                }
            }

            fractionalVegetationCover = numberOfVegetationCells / (double) (numberOfRows * numberOfColumns);
        }



        return fractionalVegetationCover;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
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

        System.out.println("" + numberOfVegetationCells + "/" + totalNumberOfCells + " vegetation/total");
        System.out.println();
    }
}
