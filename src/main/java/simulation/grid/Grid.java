package simulation.grid;

import javafx.geometry.Pos;
import javafx.util.Pair;
import simulation.grid.cell.Cell;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.GridMapFactory;

import java.util.Map;

public class Grid {
    private Map<Pair<Integer, Integer>, Cell> gridMap;
    private int numberOfRows;
    private int numberOfColumns;

    public Grid(int numberOfRows, int numberOfColumns, GridMapFactory gridMapFactory) {
        this.gridMap = gridMapFactory.createNewGridMap(numberOfRows, numberOfColumns);
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    public Cell getCell(Position position) {
        return null;
    }

    // Temporary printing method, remove or move to other class later
    public void printToConsole() {
        int currentRow = 0;
        int numberOfVegetationCells = 0;
        int totalNumberOfCells = 0;

        for (Map.Entry<Pair<Integer, Integer>, Cell> entry : gridMap.entrySet()) {
            if(entry.getKey().getKey() > currentRow) {
                currentRow++;
                System.out.println();
            }
            if (entry.getValue() instanceof Vegetation) {
                System.out.print("[~]");
                numberOfVegetationCells++;
                totalNumberOfCells++;
            } else {
                System.out.print("[ ]");
                totalNumberOfCells++;
            }
        }

        System.out.println();
        System.out.println("" + numberOfVegetationCells + "/" + totalNumberOfCells + " vegetation/total");
    }
}
