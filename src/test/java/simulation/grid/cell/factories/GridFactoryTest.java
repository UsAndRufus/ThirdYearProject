package simulation.grid.cell.factories;

import javafx.util.Pair;
import org.junit.Test;
import simulation.grid.Grid;
import simulation.grid.cell.Cell;
import simulation.grid.cell.factories.TestCellFactory;

import java.util.Map;

import static org.junit.Assert.*;

public class GridFactoryTest {

    private GridFactory gridFactory = new GridFactory(new TestCellFactory());

    @Test
    public void createNewGridMap() throws Exception {
        int numberOfRows = 10;
        int numberOfColumns = 10;
        Map<Pair<Integer, Integer>, Cell> gridMap
                = gridFactory.createNewGridMap(numberOfRows, numberOfColumns);
        assertGridMapCorrectSize(gridMap, numberOfRows, numberOfColumns);

        numberOfRows = 5;
        numberOfColumns = 20;
        gridMap = gridFactory.createNewGridMap(numberOfRows, numberOfColumns);
        assertGridMapCorrectSize(gridMap, numberOfRows, numberOfColumns);

        numberOfRows = 100;
        numberOfColumns = 1;
        gridMap = gridFactory.createNewGridMap(numberOfRows, numberOfColumns);
        assertGridMapCorrectSize(gridMap, numberOfRows, numberOfColumns);
    }

    private void assertGridMapCorrectSize(Map<Pair<Integer, Integer>, Cell> gridMap, int expectedRows, int expectedColumns) {
        assertEquals("[assertGridMapCorrectSize] gridMap should have rows*columns entries",
                gridMap.size(), expectedRows*expectedColumns);

        int maxRow = 0;
        int maxColumn = 0;

        for (Map.Entry<Pair<Integer, Integer>, Cell> entry : gridMap.entrySet()) {
            int row = entry.getKey().getKey();
            int column = entry.getKey().getValue();

            maxRow = (row > maxRow) ? row : maxRow;
            maxColumn = (column > maxColumn) ? column : maxColumn;
        }

        assertEquals("[assertGridMapCorrectSize] maxRow should be equal to expectedRows - 1",
                expectedRows - 1, maxRow);

        assertEquals("[assertGridMapCorrectSize] maxColumn should be equal to expectedColumns - 1",
                expectedColumns - 1, maxColumn);
    }

}