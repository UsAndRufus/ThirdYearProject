package simulation.grid;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.TestCell;
import simulation.grid.cell.Vegetation;

import java.util.List;

import static org.junit.Assert.*;

public class GridTest {

    private static final double DOUBLE_PRECISION = 0.00001d;

    private int xBound = 6;
    private int yBound = 5;

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Test
    // TODO: make stronger tests by referencing actual objects rather than type. Probably have to override grid factory for this
    public void testGetCellInBounds() throws Exception {
        Grid grid = TestGridFactory.createTestGrid();

        Position position = new Position(0, 0);
        assertTrue("[testGetCellInBounds] Cell at 0,0 should be a Vegetation cell",
                grid.getCell(position) instanceof Vegetation);

        position = new Position(2, 3);
        assertTrue("[testGetCellInBounds] Cell at 2,3 should be a NonVegetation cell",
                grid.getCell(position) instanceof NonVegetation);

        position = new Position(4, 5);
        assertTrue("[testGetCellInBounds] Cell at 4,5 should be a NonVegetation cell",
                grid.getCell(position) instanceof Vegetation);
    }

    @Test
    public void testGetCellOutOfBounds() throws Exception {
        Grid grid = TestGridFactory.createTestGrid();

        Position position = new Position(xBound,yBound);
        assertNull("[testGetCellOutOfBounds] Cell at " + xBound + "," + yBound + " should be out of bounds",
                grid.getCell(position));

        assert(3 < xBound);
        position = new Position(3,yBound+1);
        assertNull("[testGetCellOutOfBounds] Cell at 3," + yBound+1 + " should be out of bounds",
                grid.getCell(position));

        position = new Position(-1,-1);
        assertNull("[testGetCellOutOfBounds] Cell at -1,-1 should be out of bounds", grid.getCell(position));
    }

    @Test
    public void testIsPositionOutOfBounds() {
        Grid grid = TestGridFactory.createTestGrid();

        Position position = new Position(xBound,yBound);
        assertTrue("[testIsPositionOutOfBounds] Cell at " + xBound + "," + yBound + " should be out of bounds",
                grid.isPositionOutOfBounds(position));

        position = new Position(1,1);
        assert(1 < xBound);
        assert(1 < yBound);
        assertFalse("[testIsPositionOutOfBounds] Cell at 1,1 should be in bounds",
                grid.isPositionOutOfBounds(position));
    }

    @Test
    public void testIsRangeOutOfBounds() {
        Grid grid = TestGridFactory.createTestGrid();

        Position position = new Position(-10,-10);
        int range = 4;
        assertTrue("[testIsRangeOutOfBounds] Starting point is outside range", grid.isRangeOutOfBounds(position, range));

        position = new Position(0,0);
        range = 1;
        assertTrue("[testIsRangeOutOfBounds] Range crosses boundary", grid.isRangeOutOfBounds(position, range));
        position = new Position(1,1);
        range = 1;
        assertFalse("[testIsRangeOutOfBounds] Range inside bounds", grid.isRangeOutOfBounds(position, range));

        assert(2 < yBound);
        position = new Position(0,2);
        range = 2;
        assertTrue("[testIsRangeOutOfBounds] Range crosses boundary", grid.isRangeOutOfBounds(position, range));

        assert(1 < yBound);
        position = new Position(xBound - 1,1);
        range = 1;
        assertTrue("[testIsRangeOutOfBounds] Range crosses boundary", grid.isRangeOutOfBounds(position, range));

        assert(3 < xBound);
        position = new Position(3,0);
        range = 4;
        assertTrue("[testIsRangeOutOfBounds] Range crosses boundary", grid.isRangeOutOfBounds(position, range));

        assert(4 < xBound);
        position = new Position(4,yBound - 1);
        range = 1;
        assertTrue("[testIsRangeOutOfBounds] Range crosses boundary", grid.isRangeOutOfBounds(position, range));

    }

    @Test
    public void testSetCellInBounds() {
        Grid grid = TestGridFactory.createTestGrid();

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
        Grid grid = TestGridFactory.createTestGrid();

        Position position = new Position(xBound,yBound);
        Cell testCell = new TestCell();

        exception.expect(IllegalArgumentException.class);
        grid.setCell(position, testCell);
    }

    @Test
    public void testSetCellChangesFractionalVegetationCover() {

        // veg -> veg
        Grid grid = TestGridFactory.createTestGrid();
        double expectedVegetationCover = grid.getFractionalVegetationCover();
        Position positionToChange = new Position(0,0);
        assertTrue("[testSetCellChangesFractionalVegetationCover] Test makes no sense if cell isn't vegetation",
                grid.getCell(positionToChange) instanceof Vegetation);
        grid.setCell(positionToChange, new Vegetation());
        assertEquals("[testSetCellChangesFractionalVegetationCover] Fractional cover should be the same as previously",
                expectedVegetationCover, grid.getFractionalVegetationCover(), DOUBLE_PRECISION);

        // non-veg -> non-veg
        grid = TestGridFactory.createTestGrid();
        expectedVegetationCover = grid.getFractionalVegetationCover();
        positionToChange = new Position(1,0);
        assertTrue("[testSetCellChangesFractionalVegetationCover] Test makes no sense if cell isn't non-vegetation",
                grid.getCell(positionToChange) instanceof NonVegetation);
        grid.setCell(positionToChange, new NonVegetation());
        assertEquals("[testSetCellChangesFractionalVegetationCover] Fractional cover should be the same",
                expectedVegetationCover, grid.getFractionalVegetationCover(), DOUBLE_PRECISION);


        // veg -> non-veg
        grid = TestGridFactory.createTestGrid();
        expectedVegetationCover = grid.getFractionalVegetationCover() - (1.0 / (double) (xBound * yBound));
        positionToChange = new Position(0,0);
        assertTrue("[testSetCellChangesFractionalVegetationCover] Test makes no sense if cell isn't vegetation",
                grid.getCell(positionToChange) instanceof Vegetation);
        grid.setCell(positionToChange, new NonVegetation());
        assertEquals("[testSetCellChangesFractionalVegetationCover] Fractional cover should be less than previously",
                expectedVegetationCover, grid.getFractionalVegetationCover(), DOUBLE_PRECISION);

        // non-veg -> veg
        grid = TestGridFactory.createTestGrid();
        expectedVegetationCover = grid.getFractionalVegetationCover() + (1.0 / (double) (xBound * yBound));
        positionToChange = new Position(1,0);
        assertTrue("[testSetCellChangesFractionalVegetationCover] Test makes no sense if cell isn't non-vegetation",
                grid.getCell(positionToChange) instanceof NonVegetation);
        grid.setCell(positionToChange, new Vegetation());
        assertEquals("[testSetCellChangesFractionalVegetationCover] Fractional cover should be increased",
                expectedVegetationCover, grid.getFractionalVegetationCover(), DOUBLE_PRECISION);
    }

    @Test
    public void testGetRandomPositions() {
        Grid grid = TestGridFactory.createTestGrid();

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
        Grid grid = TestGridFactory.createTestGrid();

        double expected = 18.0 / 30.0;

        assertEquals("[testGetFractionalVegetationCover] Actual value should be same as hand-calculated value",
                expected, grid.getFractionalVegetationCover(), DOUBLE_PRECISION);
    }
}