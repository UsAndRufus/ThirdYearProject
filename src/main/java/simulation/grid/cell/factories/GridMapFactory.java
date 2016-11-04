package simulation.grid.cell.factories;

import javafx.util.Pair;
import simulation.grid.cell.Cell;

import java.util.HashMap;
import java.util.Map;

public class GridMapFactory {

    private CellFactory cellFactory;

    public GridMapFactory(CellFactory cellFactory) {
        this.cellFactory = cellFactory;
    }

    public Map<Pair<Integer, Integer>, Cell> createNewGridMap(int numberOfRows, int numberOfColumns) {
        Map<Pair<Integer, Integer>, Cell> newGridMap = new HashMap<>(numberOfRows * numberOfColumns);

        for (Integer x = 0; x < numberOfRows; x++) {
            for (Integer y = 0; y < numberOfColumns; y++) {
                newGridMap.put(new Pair<>(x, y), cellFactory.createCell());
            }
        }

        return newGridMap;
    }
}
