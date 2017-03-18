package simulation.density;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.TestGridFactory;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;

import java.util.Map;

import static org.junit.Assert.*;

public class DensityMetricTest {

    private DensityMetric densityMetric;
    private double immediacyFactor = 3.0;

    private Grid grid;

    private class DensityMetricTester extends DensityMetric {

        public DensityMetricTester(double immediacyFactor, int maximumDistance, Grid grid) {
            super(immediacyFactor, maximumDistance, grid);
        }

        @Override
        public double calculateFor(Position position, Class<? extends Cell> cellClass) {
            return 0;
        }
    }

    @Before
    public void setUp() throws Exception {
        grid = TestGridFactory.createTestGrid();
    }

    @Test
    public void testGetGeneralNumberOfCellsAtDistance() {
        densityMetric = new DensityMetricTester(immediacyFactor, 5, grid);
        Map<Integer,Integer> rangeMap = densityMetric.getGeneralNumberOfCellsAtDistance(0);

        assertEquals("[testGetGeneralNumberOfCellsAtDistance] Length of map should be 0", 0, rangeMap.size());

        rangeMap = densityMetric.getGeneralNumberOfCellsAtDistance(1);

        assertEquals("[testGetGeneralNumberOfCellsAtDistance] Length of map should be 1", 1, rangeMap.size());
        assertEquals("[testGetGeneralNumberOfCellsAtDistance] First element should be 4",
                new Integer(4), rangeMap.get(1));

        rangeMap = densityMetric.getGeneralNumberOfCellsAtDistance(4);

        assertEquals("[testGetGeneralNumberOfCellsAtDistance] Length of map should be 4", 4, rangeMap.size());
        assertEquals("[testGetGeneralNumberOfCellsAtDistance] First element should be 4",
                new Integer(4), rangeMap.get(1));
        assertEquals("[testGetGeneralNumberOfCellsAtDistance] Second element should be 8",
                new Integer(8), rangeMap.get(2));
        assertEquals("[testGetGeneralNumberOfCellsAtDistance] Third element should be 12",
                new Integer(12), rangeMap.get(3));
        assertEquals("[testGetGeneralNumberOfCellsAtDistance] Fourth element should be 16",
                new Integer(16), rangeMap.get(4));
    }

    @Test
    public void testGetNumberOfCellsInRange0() {
        densityMetric = new DensityMetricTester(immediacyFactor, 0, grid);

        Map<Integer,Integer> rangeMap = densityMetric.getNumberOfCellsInRange(0, new Position(1,1), Vegetation.class);

        assertEquals("[testGetNumberOfCellsInRange0] Length of map should be 0", 0, rangeMap.size());
    }

    @Test
    public void testGetNumberOfCellsInRange1() {
        densityMetric = new DensityMetricTester(immediacyFactor, 1, grid);

        Map<Integer,Integer> rangeMap = densityMetric.getNumberOfCellsInRange(1, new Position(1,1), Vegetation.class);

        assertEquals("[testGetNumberOfCellsInRange1] Length of map should be 1", 1, rangeMap.size());

        assertEquals("[testGetNumberOfCellsInRange1] Number of vegetation cells should be 3",
                new Integer(3), rangeMap.get(1));

        rangeMap = densityMetric.getNumberOfCellsInRange(1, new Position(1,1), NonVegetation.class);
        assertEquals("[testGetNumberOfCellsInRange1] Number of non-vegetation cells should be 1",
                new Integer(1), rangeMap.get(1));

        rangeMap = densityMetric.getNumberOfCellsInRange(1, new Position(4,5), Vegetation.class);
        assertEquals("[testGetNumberOfCellsInRange1] Number of vegetation cells should be 1",
                new Integer(1), rangeMap.get(1));

        rangeMap = densityMetric.getNumberOfCellsInRange(1, new Position(4,5), NonVegetation.class);
        assertEquals("[testGetNumberOfCellsInRange1] Number of non-vegetation cells should be 1",
                new Integer(1), rangeMap.get(1));


    }

    @Test
    public void testGetNumberOfCellsInRange1ForTypeCell() {
        densityMetric = new DensityMetricTester(immediacyFactor, 1, grid);

        Map<Integer,Integer> rangeMap = densityMetric.getNumberOfCellsInRange(1, new Position(0,0), Cell.class);
        assertEquals("[testGetNumberOfCellsInRange1ForTypeCell] Number of cells should be 2",
                new Integer(2), rangeMap.get(1));

        rangeMap = densityMetric.getNumberOfCellsInRange(1, new Position(2,2), Cell.class);
        assertEquals("[testGetNumberOfCellsInRange1ForTypeCell] Number of cells should be 4",
                new Integer(4), rangeMap.get(1));
    }

    @Test
    public void testGetNumberOfCellsInRange2() {
        densityMetric = new DensityMetricTester(immediacyFactor, 2, grid);

        Map<Integer,Integer> rangeMap = densityMetric.getNumberOfCellsInRange(2, new Position(2,3), Vegetation.class);

        assertEquals("[testGetNumberOfCellsInRange2] Length of map should be 2", 2, rangeMap.size());

        assertEquals("[testGetNumberOfCellsInRange2] Number of vegetation cells should be 7",
                new Integer(7), rangeMap.get(2));

        rangeMap = densityMetric.getNumberOfCellsInRange(2, new Position(2,3), NonVegetation.class);
        assertEquals("[testGetNumberOfCellsInRange2] Number of non-vegetation cells should be 1",
                new Integer(1), rangeMap.get(2));

        rangeMap = densityMetric.getNumberOfCellsInRange(2, new Position(4,5), Vegetation.class);
        assertEquals("[testGetNumberOfCellsInRange2] Number of vegetation cells at range 2 should be 2",
                new Integer(2), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange2] Number of vegetation cells at range 1 should be 1",
                new Integer(1), rangeMap.get(1));

        rangeMap = densityMetric.getNumberOfCellsInRange(2, new Position(4,5), NonVegetation.class);
        assertEquals("[testGetNumberOfCellsInRange2] Number of non-vegetation cells at range 2 should be 1",
                new Integer(1), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange2] Number of non-vegetation cells at range 1 should be 1",
                new Integer(1), rangeMap.get(1));
    }

    @Test
    public void testGetNumberOfCellsInRange2ForTypeCell() {
        densityMetric = new DensityMetricTester(immediacyFactor, 2, grid);

        Map<Integer,Integer> rangeMap = densityMetric.getNumberOfCellsInRange(2, new Position(2,2), Cell.class);
        assertEquals("[testGetNumberOfCellsInRange2ForTypeCell] Number of cells should be 8",
                new Integer(8), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange2ForTypeCell] Number of cells should be 4",
                new Integer(4), rangeMap.get(1));

        rangeMap = densityMetric.getNumberOfCellsInRange(2, new Position(4,5), Cell.class);
        assertEquals("[testGetNumberOfCellsInRange2ForTypeCell] Number of cells should be 3",
                new Integer(3), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange2ForTypeCell] Number of cells should be 2",
                new Integer(2), rangeMap.get(1));
    }

    @Test
    public void testGetNumberOfCellsInRange3() {
        densityMetric = new DensityMetricTester(immediacyFactor, 3, grid);

        Map<Integer,Integer> rangeMap = densityMetric.getNumberOfCellsInRange(3, new Position(2,3), Vegetation.class);

        assertEquals("[testGetNumberOfCellsInRange3] Length of map should be 3", 3, rangeMap.size());

        assertEquals("[testGetNumberOfCellsInRange3] Number of vegetation cells at range 3 should be 3",
                new Integer(3), rangeMap.get(3));
        assertEquals("[testGetNumberOfCellsInRange3] Number of vegetation cells at range 2 should be 7",
                new Integer(7), rangeMap.get(2));

        rangeMap = densityMetric.getNumberOfCellsInRange(3, new Position(2,3), NonVegetation.class);
        assertEquals("[testGetNumberOfCellsInRange3] Number of non-vegetation cells at range 3 should be 6",
                new Integer(6), rangeMap.get(3));
        assertEquals("[testGetNumberOfCellsInRange3] Number of non-vegetation cells at range 2 should be 1",
                new Integer(1), rangeMap.get(2));

        rangeMap = densityMetric.getNumberOfCellsInRange(3, new Position(4,5), Vegetation.class);
        assertEquals("[testGetNumberOfCellsInRange3] Number of vegetation cells at range 3 should be 4",
                new Integer(4), rangeMap.get(3));
        assertEquals("[testGetNumberOfCellsInRange3] Number of vegetation cells at range 2 should be 2",
                new Integer(2), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange3] Number of vegetation cells at range 1 should be 1",
                new Integer(1), rangeMap.get(1));

        rangeMap = densityMetric.getNumberOfCellsInRange(3, new Position(4,5), NonVegetation.class);
        assertEquals("[testGetNumberOfCellsInRange3] Number of non-vegetation cells at range 3 should be 0",
                new Integer(0), rangeMap.get(3));
        assertEquals("[testGetNumberOfCellsInRange3] Number of non-vegetation cells at range 2 should be 1",
                new Integer(1), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange3] Number of non-vegetation cells at range 1 should be 1",
                new Integer(1), rangeMap.get(1));
    }

    @Test
    public void testGetNumberOfCellsInRange3ForTypeCell() {
        densityMetric = new DensityMetricTester(immediacyFactor, 3, grid);

        Map<Integer,Integer> rangeMap = densityMetric.getNumberOfCellsInRange(3, new Position(2,2), Cell.class);
        assertEquals("[testGetNumberOfCellsInRange3ForTypeCell] Number of cells at range 3 should be 9",
                new Integer(9), rangeMap.get(3));
        assertEquals("[testGetNumberOfCellsInRange3ForTypeCell] Number of cells at range 2 should be 8",
                new Integer(8), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange3ForTypeCell] Number of cells at range 1 should be 4",
                new Integer(4), rangeMap.get(1));

        rangeMap = densityMetric.getNumberOfCellsInRange(3, new Position(4,5), Cell.class);
        assertEquals("[testGetNumberOfCellsInRange3ForTypeCell] Number of cells at range 3 should be 4",
                new Integer(4), rangeMap.get(3));
        assertEquals("[testGetNumberOfCellsInRange3ForTypeCell] Number of cells at range 2 should be 3",
                new Integer(3), rangeMap.get(2));
        assertEquals("[testGetNumberOfCellsInRange3ForTypeCell] Number of cells at range 1 should be 2",
                new Integer(2), rangeMap.get(1));
    }
}