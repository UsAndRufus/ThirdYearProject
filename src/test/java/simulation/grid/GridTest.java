package simulation.grid;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.KalahariCellFactory;
import simulation.grid.cell.factories.KalahariTestCellGridFactory;
import simulation.grid.cell.factories.TestCellFactory;

import static org.junit.Assert.*;

public class GridTest {

    private Grid grid;

    @Before
    public void setUp() throws Exception {
        grid = new Grid(6,5, new KalahariTestCellGridFactory(new TestCellFactory()));
    }

    @Test
    // TODO: make stronger tests by referencing actual objects rather than type. Probably have to override grid factory for this
    public void testGetCellInBounds() throws Exception {
        Position position = new Position(0, 0);
        assertTrue("[getCell] Cell at 0,0 should be a Vegetation cell",
                grid.getCell(position) instanceof Vegetation);

        position = new Position(2, 3);
        assertTrue("[getCell] Cell at 2,3 should be a NonVegetation cell",
                grid.getCell(position) instanceof NonVegetation);

        position = new Position(4, 5);
        assertTrue("[getCell] Cell at 4,5 should be a NonVegetation cell",
                grid.getCell(position) instanceof Vegetation);
    }

    @Test
    public void testGetCellOutOfBounds() throws Exception {
        Position position = new Position(6,5);
        assertNull("[getCell] Cell at 6,5 should be out of bounds", grid.getCell(position));

        position = new Position(3,7);
        assertNull("[getCell] Cell at 3,7 should be out of bounds", grid.getCell(position));

        position = new Position(-1,-1);
        assertNull("[getCell] Cell at -1,-1 should be out of bounds", grid.getCell(position));
    }

}