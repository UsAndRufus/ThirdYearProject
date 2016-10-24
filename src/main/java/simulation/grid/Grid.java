package simulation.grid;

import javafx.util.Pair;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.CellFactory;

import java.util.HashMap;
import java.util.Map;

public class Grid {
    private Map<Pair<Integer, Integer>, Cell> gridMap;
    private int numberOfRows;
    private int numberOfColumns;

    public Grid(int numberOfRows, int numberOfColumns, CellFactory cellFactory) {
        this.gridMap = createNewGridMap(numberOfRows, numberOfColumns, cellFactory);
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    protected static Map<Pair<Integer, Integer>, Cell> createNewGridMap(int numberOfRows, int numberOfColumns, CellFactory cellFactory) {
        Map<Pair<Integer, Integer>, Cell> newGridMap = new HashMap<>(numberOfRows * numberOfColumns);

        for (Integer x = 0; x < numberOfRows; x++) {
            for (Integer y = 0; y < numberOfColumns; y++) {
                newGridMap.put(new Pair<>(x, y), cellFactory.createCell());
            }
        }

        return newGridMap;
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
