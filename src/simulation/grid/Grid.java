package simulation.grid;

import javafx.util.Pair;
import simulation.grid.cell.Cell;
import simulation.grid.cell.factories.CellFactory;

import java.util.HashMap;
import java.util.Map;

public class Grid {
    private Map<Pair<Integer, Integer>, Cell> gridMap;

    public Grid(int numberOfRows, int numberOfColumns, CellFactory cellFactory) {
        gridMap = new HashMap<>(numberOfRows * numberOfColumns);

        for (Integer x = 0; x < numberOfRows; x++) {
            for (Integer y = 0; y < numberOfColumns; y++) {
                gridMap.put(new Pair<>(x, y), cellFactory.createCell());
            }
        }
    }
}
