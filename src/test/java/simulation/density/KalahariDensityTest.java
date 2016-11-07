package simulation.density;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.factories.CellGridFactory;
import simulation.grid.cell.factories.KalahariCellFactory;
import simulation.grid.cell.factories.KalahariTestCellGridFactory;
import simulation.grid.cell.factories.TestCellFactory;

import static org.junit.Assert.*;

public class KalahariDensityTest {

    private static final double DELTA = 1e-15;

    private KalahariDensity kalahariDensity;
    private double immediacyFactor = 3.0;

    private Grid grid;

    @Before
    public void setUp() throws Exception {
        CellGridFactory cellGridFactory = new KalahariTestCellGridFactory(new TestCellFactory());
        grid = new Grid(6,5, cellGridFactory);
    }

    @Test
    public void testCalculateForWithMaximumDistance1() throws Exception {
        kalahariDensity = new KalahariDensity(immediacyFactor, 1, grid);

        int row = 2;
        int column = 2;
        double expected = 0.5; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, kalahariDensity.calculateFor(new Position(row, column)), DELTA);
    }

    @Test
    public void testCalculateForWithMaximumDistance2() throws Exception {
        kalahariDensity = new KalahariDensity(immediacyFactor, 2, grid);

        int row = 2;
        int column = 2;
        double expected = 0.5; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, kalahariDensity.calculateFor(new Position(row, column)), DELTA);
    }

}