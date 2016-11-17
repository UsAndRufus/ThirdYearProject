package simulation.grid;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.TestCell;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.KalahariTestCellGridFactory;
import simulation.grid.cell.factories.TestCellFactory;

import java.util.List;

import static org.junit.Assert.*;

public class GridTest {

    private static final double DELTA = 1e-15;

    private Grid grid;
    private int xBound = 6;
    private int yBound = 5;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        grid = new Grid(xBound,yBound, new KalahariTestCellGridFactory(new TestCellFactory()));
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
        Position position = new Position(xBound,yBound);
        assertNull("[getCell] Cell at " + xBound + "," + yBound + " should be out of bounds", grid.getCell(position));

        assert(3 < xBound);
        position = new Position(3,yBound+1);
        assertNull("[getCell] Cell at 3," + yBound+1 + " should be out of bounds", grid.getCell(position));

        position = new Position(-1,-1);
        assertNull("[getCell] Cell at -1,-1 should be out of bounds", grid.getCell(position));
    }

    @Test
    public void testSetCellInBounds() {
        Position position = new Position(0,0);

        assertFalse("[testSetCell] Check cell at 0,0 is not an instance of TestCell, otherwise test is meaningless",
                grid.getCell(position) instanceof TestCell);

        Cell testCell = new TestCell();
        grid.setCell(position, testCell);

        assertTrue("[testSetCell] Cell at 0,0 should be instance of TestCell as we have just set it to be so",
                grid.getCell(position) instanceof  TestCell);

    }

    @Test
    public void testSetCellOutOfBounds() {
        Position position = new Position(xBound,yBound);
        Cell testCell = new TestCell();

        exception.expect(IllegalArgumentException.class);
        grid.setCell(position, testCell);
    }

    @Test
    public void testGetRandomPositions() {
        double fractionToChoose = 0.1;
        int expectedNumberOfPositions = (int) (xBound * yBound * fractionToChoose);

        List<Position> positions = grid.getRandomPositions(fractionToChoose);

        assertEquals("[testGetRandomPositions] Length of list should be fraction * number of cells, rounded down",
                expectedNumberOfPositions, positions.size());

        fractionToChoose = 0.137921;
        expectedNumberOfPositions = (int) (xBound * yBound * fractionToChoose);

        positions = grid.getRandomPositions(fractionToChoose);

        assertEquals("[testGetRandomPositions] Length of list should be fraction * number of cells, rounded down",
                expectedNumberOfPositions, positions.size());
    }

    @Test
    public void testGetFractionalVegetationCover() {
        double expected = 18.0 / 30.0;

        assertEquals("[testGetFractionalVegetationCover] Actual value should be same as hand-calculated value",
                expected, grid.getFractionalVegetationCover(), DELTA);
    }
}