package simulation.grid;

import simulation.grid.cell.*;
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

        getFractionalVegetationCover();
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

    public boolean isPositionOutOfBounds(Position position) {
        return getCell(position) == null;
    }

    public boolean isRangeOutOfBounds(Position centre, int range) {
        return  isPositionOutOfBounds(new Position(centre.getX() - range, centre.getY())) ||
                isPositionOutOfBounds(new Position(centre.getX() + range, centre.getY())) ||
                isPositionOutOfBounds(new Position(centre.getX(), centre.getY() - range)) ||
                isPositionOutOfBounds(new Position(centre.getX(), centre.getY() + range));
    }

    public void setCell(Position position, Cell cell) {
        if (getCell(position) == null) {
            throw new IllegalArgumentException("Cannot set cell if position out of bounds");
        }

        if ((getCell(position) instanceof NonVegetation)
                && ((cell instanceof Vegetation) || (cell instanceof CompetitorSpecies1) ||
                (cell instanceof CompetitorSpecies2))) {
            fractionalVegetationCover += (1 / (double) (numberOfRows * numberOfColumns));
        } else if (((getCell(position) instanceof Vegetation) || (getCell(position) instanceof CompetitorSpecies1) ||
                (getCell(position) instanceof CompetitorSpecies2))
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


        for (int currentRow = 0; currentRow < numberOfRows; currentRow++) {
            for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
                Cell cell = cellGrid[currentRow][currentColumn];
                if (cell instanceof Vegetation) {
                    System.out.print("X");
                } else if (cell instanceof CompetitorSpecies1) {
                    System.out.print("1");
                } else if (cell instanceof CompetitorSpecies2) {
                    System.out.print("2");
                } else {
                    System.out.print("o");
                }
            }
            System.out.println();
        }
    }

    public void printStats()  {

        int numberOfVegetationCells = 0;
        int numberOfSpecies1Cells = 0;
        int numberOfSpecies2Cells = 0;
        int numberOfNonVegCells = 0;

        for (int currentRow = 0; currentRow < numberOfRows; currentRow++) {
            for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
                if (cellGrid[currentRow][currentColumn] instanceof Vegetation) {
                    numberOfVegetationCells++;
                } else if (cellGrid[currentRow][currentColumn] instanceof CompetitorSpecies1) {
                    numberOfSpecies1Cells++;
                } else if (cellGrid[currentRow][currentColumn] instanceof CompetitorSpecies2) {
                    numberOfSpecies2Cells++;
                } else {
                    numberOfNonVegCells++;
                }
            }
        }

        double allCells = (double) numberOfRows * numberOfColumns;

        System.out.println("Total number of cells: " + (int) allCells);

        System.out.println("Vegetation cells: " + (numberOfVegetationCells / allCells)
                + "; species 1 cells: " + (numberOfSpecies1Cells / allCells)
                + "; species 2 cells: " + (numberOfSpecies2Cells / allCells)
                + "; non-veg: " + (numberOfNonVegCells / allCells));

        double totalNumberVegCells = (double) numberOfSpecies1Cells + numberOfSpecies2Cells + numberOfVegetationCells;
        System.out.println("Proportion of all veg cells: " + (totalNumberVegCells / allCells));
    }
}
