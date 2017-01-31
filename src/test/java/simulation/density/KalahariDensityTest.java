package simulation.density;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.TestGridFactory;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.CellGridFactory;
import simulation.grid.cell.factories.KalahariTestCellGridFactory;
import simulation.grid.cell.factories.TestCellFactory;

import java.util.Map;

import static org.junit.Assert.*;

public class KalahariDensityTest {

    private static final double DELTA = 1e-15;

    private KalahariDensity kalahariDensity;
    private double immediacyFactor = 3.0;

    private Grid grid;

    @Before
    public void setUp() throws Exception {
        grid = TestGridFactory.createTestGrid();
    }

    @Test
    public void testCalculateForWithMaximumDistance1() throws Exception {
        kalahariDensity = new KalahariDensity(immediacyFactor, 1, grid);

        int x = 2;
        int y = 2;
        double expected = 0.75; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, kalahariDensity.calculateFor(new Position(x, y)), DELTA);
    }

    @Test
    public void testCalculateForWithMaximumDistance2() throws Exception {
        kalahariDensity = new KalahariDensity(immediacyFactor, 2, grid);

        int x = 2;
        int y = 2;
        double expected = 0.675; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, kalahariDensity.calculateFor(new Position(x, y)), DELTA);

        x = 4;
        y = 5;
        expected = 10.0 / 19.0; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, kalahariDensity.calculateFor(new Position(x, y)), DELTA);
    }

    @Test
    public void testCalculateForWithMaximumDistance3() throws Exception {
        kalahariDensity = new KalahariDensity(immediacyFactor, 3, grid);

        int x = 2;
        int y = 2;
        double expected = 769.0 / 1152.0; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, kalahariDensity.calculateFor(new Position(x, y)), DELTA);
    }

    @Test
    public void testGetNumberOfCellsInRange0() {
        kalahariDensity = new KalahariDensity(immediacyFactor, 0, grid);

        Map<Integer,Integer> rangeMap = kalahariDensity.getNumberOfCellsInRange(0, new Position(1,1), Vegetation.class);

        assertEquals("[testGetNumberOfCellsInRange0] Length of map should be 0", 0, rangeMap.size());
    }

    @Test
    public void testGetNumberOfCellsInRange1() {
        kalahariDensity = new KalahariDensity(immediacyFactor, 1, grid);

        Map<Integer,Integer> rangeMap = kalahariDensity.getNumberOfCellsInRange(1, new Position(1,1), Vegetation.class);

        assertEquals("[testGetNumberOfCellsInRange1] Length of map should be 1", 1, rangeMap.size());

        assertEquals("[testGetNumberOfCellsInRange1] Number of vegetation cells should be 3",
                new Integer(3), rangeMap.get(1));

        rangeMap = kalahariDensity.getNumberOfCellsInRange(1, new Position(1,1), NonVegetation.class);
        assertEquals("[testGetNumberOfCellsInRange1] Number of non-vegetation cells should be 1",
                new Integer(1), rangeMap.get(1));

        rangeMap = kalahariDensity.getNumberOfCellsInRange(1, new Position(4,5), Vegetation.class);
        assertEquals("[testGetNumberOfCellsInRange1] Number of vegetation cells should be 1",
                new Integer(1), rangeMap.get(1));

        rangeMap = kalahariDensity.getNumberOfCellsInRange(1, new Position(4,5), NonVegetation.class);
        assertEquals("[testGetNumberOfCellsInRange1] Number of non-vegetation cells should be 1",
                new Integer(1), rangeMap.get(1));


    }

    @Test
    public void testGetNumberOfCellsInRange1ForTypeCell() {
        kalahariDensity = new KalahariDensity(immediacyFactor, 1, grid);

        Map<Integer,Integer> rangeMap = kalahariDensity.getNumberOfCellsInRange(1, new Position(0,0), Cell.class);
        assertEquals("[testGetNumberOfCellsInRange1] Number of cells should be 2",
                new Integer(2), rangeMap.get(1));

        rangeMap = kalahariDensity.getNumberOfCellsInRange(1, new Position(2,2), Cell.class);
        assertEquals("[testGetNumberOfCellsInRange1] Number of cells should be 4",
                new Integer(4), rangeMap.get(1));
    }

    @Test
    public void testGetNumberOfCellsInRange2() {
        kalahariDensity = new KalahariDensity(immediacyFactor, 2, grid);

        Map<Integer,Integer> rangeMap = kalahariDensity.getNumberOfCellsInRange(2, new Position(2,3), Vegetation.class);

        assertEquals("[testGetNumberOfCellsInRange2] Length of map should be 2", 2, rangeMap.size());

        assertEquals("[testGetNumberOfCellsInRange2] Number of vegetation cells should be 7",
                new Integer(7), rangeMap.get(2));

        rangeMap = kalahariDensity.getNumberOfCellsInRange(2, new Position(2,3), NonVegetation.class);
        assertEquals("[testGetNumberOfCellsInRange2] Number of non-vegetation cells should be 1",
                new Integer(1), rangeMap.get(2));

        rangeMap = kalahariDensity.getNumberOfCellsInRange(2, new Position(4,5), Vegetation.class);
        assertEquals("[testGetNumberOfCellsInRange2] Number of vegetation cells at range 2 should be 2",
                new Integer(2), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange2] Number of vegetation cells at range 1 should be 1",
                new Integer(1), rangeMap.get(1));

        rangeMap = kalahariDensity.getNumberOfCellsInRange(2, new Position(4,5), NonVegetation.class);
        assertEquals("[testGetNumberOfCellsInRange2] Number of non-vegetation cells at range 2 should be 1",
                new Integer(1), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange2] Number of non-vegetation cells at range 1 should be 1",
                new Integer(1), rangeMap.get(1));
    }

    @Test
    public void testGetNumberOfCellsInRange2ForTypeCell() {
        kalahariDensity = new KalahariDensity(immediacyFactor, 1, grid);

        Map<Integer,Integer> rangeMap = kalahariDensity.getNumberOfCellsInRange(2, new Position(2,2), Cell.class);
        assertEquals("[testGetNumberOfCellsInRange1] Number of cells should be 8",
                new Integer(8), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange1] Number of cells should be 4",
                new Integer(4), rangeMap.get(1));
    }

    @Test
    public void testParetoWithImmediacyFactor3Point0() {
        double immediacyFactor = 3.0;
        kalahariDensity = new KalahariDensity(immediacyFactor, 3, grid);

        int distance = 1;
        double expected = 1.0;
        assertEquals("[testParetoWithImmediacyFactor3Point0] At distance 1, answer should be 1.0",
                expected, kalahariDensity.pareto(distance, immediacyFactor), DELTA);

        distance = 2;
        expected = 1.0 / 8.0;
        assertEquals("[testParetoWithImmediacyFactor3Point0] At distance 2, answer should be 1/8",
                expected, kalahariDensity.pareto(distance, immediacyFactor), DELTA);

        distance = 3;
        expected = 1.0 / 27.0;
        assertEquals("[testParetoWithImmediacyFactor3Point0] At distance 3, answer should be 1/27",
                expected, kalahariDensity.pareto(distance, immediacyFactor), DELTA);
    }

    @Test
    public void testParetoWithImmediacyFactor2Point5() {
        double immediacyFactor = 2.5;
        kalahariDensity = new KalahariDensity(immediacyFactor, 3, grid);

        int distance = 1;
        double expected = 1.0;
        assertEquals("[testParetoWithImmediacyFactor2Point5] At distance 1, answer should be 1.0",
                expected, kalahariDensity.pareto(distance, immediacyFactor), DELTA);

        distance = 2;
        expected = Math.pow(1.0 / (double) distance, immediacyFactor);
        assertEquals("[testParetoWithImmediacyFactor2Point5] At distance 2, answer should be 1/8",
                expected, kalahariDensity.pareto(distance, immediacyFactor), DELTA);

        distance = 3;
        expected = Math.pow(1.0 / (double) distance, immediacyFactor);
        assertEquals("[testParetoWithImmediacyFactor2Point5] At distance 3, answer should be 1/27",
                expected, kalahariDensity.pareto(distance, immediacyFactor), DELTA);
    }
}