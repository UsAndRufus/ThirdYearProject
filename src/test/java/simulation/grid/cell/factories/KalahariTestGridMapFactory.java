package simulation.grid.cell.factories;

import javafx.util.Pair;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;

import java.util.HashMap;
import java.util.Map;

public class KalahariTestGridMapFactory extends GridMapFactory {
    public KalahariTestGridMapFactory(CellFactory cellFactory) {
        super(cellFactory);
    }

    @Override
    public Map<Pair<Integer, Integer>, Cell> createNewGridMap(int numberOfRows, int numberOfColumns) {
        Map<Pair<Integer, Integer>, Cell> gridMap = new HashMap<>();

        gridMap.put(new Pair<>(0,0), new Vegetation());
        gridMap.put(new Pair<>(0,1), new NonVegetation());
        gridMap.put(new Pair<>(0,2), new NonVegetation());
        gridMap.put(new Pair<>(0,3), new NonVegetation());
        gridMap.put(new Pair<>(0,4), new Vegetation());

        gridMap.put(new Pair<>(1,0), new Vegetation());
        gridMap.put(new Pair<>(1,1), new NonVegetation());
        gridMap.put(new Pair<>(1,2), new Vegetation());
        gridMap.put(new Pair<>(1,3), new NonVegetation());
        gridMap.put(new Pair<>(1,4), new NonVegetation());

        gridMap.put(new Pair<>(2,0), new NonVegetation());
        gridMap.put(new Pair<>(2,1), new Vegetation());
        gridMap.put(new Pair<>(2,2), new Vegetation());
        gridMap.put(new Pair<>(2,3), new Vegetation());
        gridMap.put(new Pair<>(2,4), new Vegetation());

        gridMap.put(new Pair<>(3,0), new Vegetation());
        gridMap.put(new Pair<>(3,1), new NonVegetation());
        gridMap.put(new Pair<>(3,2), new NonVegetation());
        gridMap.put(new Pair<>(3,3), new Vegetation());
        gridMap.put(new Pair<>(3,4), new NonVegetation());

        gridMap.put(new Pair<>(4,0), new NonVegetation());
        gridMap.put(new Pair<>(4,1), new Vegetation());
        gridMap.put(new Pair<>(4,2), new Vegetation());
        gridMap.put(new Pair<>(4,3), new Vegetation());
        gridMap.put(new Pair<>(4,4), new NonVegetation());

        gridMap.put(new Pair<>(5,0), new Vegetation());
        gridMap.put(new Pair<>(5,1), new Vegetation());
        gridMap.put(new Pair<>(5,2), new Vegetation());
        gridMap.put(new Pair<>(5,3), new Vegetation());
        gridMap.put(new Pair<>(5,4), new Vegetation());

        return gridMap;
    }
}
