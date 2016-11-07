package simulation.grid.cell.factories;

import org.junit.Test;
import simulation.grid.cell.Cell;

import static org.junit.Assert.*;

public class CellGridFactoryTest {

    private CellGridFactory cellGridFactory = new CellGridFactory(new TestCellFactory());

    @Test
    public void testCreateNewCellGrid() throws Exception {
        int numberOfRows = 10;
        int numberOfColumns = 10;
        Cell[][] cellGrid = cellGridFactory.createNewCellGrid(numberOfRows, numberOfColumns);
        assertCellGridCorrectSize(cellGrid, numberOfRows, numberOfColumns);

        numberOfRows = 5;
        numberOfColumns = 20;
        cellGrid = cellGridFactory.createNewCellGrid(numberOfRows, numberOfColumns);
        assertCellGridCorrectSize(cellGrid, numberOfRows, numberOfColumns);

        numberOfRows = 100;
        numberOfColumns = 1;
        cellGrid = cellGridFactory.createNewCellGrid(numberOfRows, numberOfColumns);
        assertCellGridCorrectSize(cellGrid, numberOfRows, numberOfColumns);
    }

    private void assertCellGridCorrectSize(Cell[][] cellGrid, int expectedRows, int expectedColumns) {
        assertEquals("[assertCellGridCorrectSize] cellGrid should have expectedRows rows",
                cellGrid.length, expectedRows);

        for (int currentRow = 0; currentRow < expectedRows; expectedRows++) {
            assertEquals("[assertCellGridCorrectSize] cellGrid should have expectedColumns columns in every row",
                    cellGrid[currentRow].length, expectedColumns);
        }
    }

}