package simulation.density;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.TestGridFactory;
import simulation.grid.cell.Vegetation;

import static org.junit.Assert.assertEquals;

public class ParetoDensityTest {

    private static final double DELTA = 0.00001d;

    private ParetoDensity paretoDensity;
    private double immediacyFactor = 3.0;

    private Grid grid;

    @Before
    public void setUp() throws Exception {
        grid = TestGridFactory.createTestGrid();
    }

    @Test
    public void testCalculateForWithMaximumDistance1() throws Exception {
        paretoDensity = new ParetoDensity(immediacyFactor, 1, grid);

        int x = 2;
        int y = 2;
        double expected = 0.75; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, paretoDensity.calculateFor(new Position(x, y), Vegetation.class), DELTA);
    }

    @Test
    public void testCalculateForWithMaximumDistance2() throws Exception {
        paretoDensity = new ParetoDensity(immediacyFactor, 2, grid);

        int x = 2;
        int y = 2;
        double expected = 0.675; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, paretoDensity.calculateFor(new Position(x, y), Vegetation.class), DELTA);

        x = 4;
        y = 5;
        expected = 10.0 / 19.0; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, paretoDensity.calculateFor(new Position(x, y), Vegetation.class), DELTA);
    }

    @Test
    public void testCalculateForWithMaximumDistance3() throws Exception {
        paretoDensity = new ParetoDensity(immediacyFactor, 3, grid);

        int x = 2;
        int y = 2;
        double expected = 769.0 / 1152.0; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, paretoDensity.calculateFor(new Position(x, y), Vegetation.class), DELTA);

        x = 4;
        y = 5;
        expected = 302.0 / 545.0; // did the maths on paper yo

        assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                expected, paretoDensity.calculateFor(new Position(x, y), Vegetation.class), DELTA);
    }

    @Test
    public void testParetoWithImmediacyFactor3Point0() {
        double immediacyFactor = 3.0;
        paretoDensity = new ParetoDensity(immediacyFactor, 3, grid);

        int distance = 1;
        double expected = 1.0;
        assertEquals("[testParetoWithImmediacyFactor3Point0] At distance 1, answer should be 1.0",
                expected, paretoDensity.pareto(distance, immediacyFactor), DELTA);

        distance = 2;
        expected = 1.0 / 8.0;
        assertEquals("[testParetoWithImmediacyFactor3Point0] At distance 2, answer should be 1/8",
                expected, paretoDensity.pareto(distance, immediacyFactor), DELTA);

        distance = 3;
        expected = 1.0 / 27.0;
        assertEquals("[testParetoWithImmediacyFactor3Point0] At distance 3, answer should be 1/27",
                expected, paretoDensity.pareto(distance, immediacyFactor), DELTA);
    }

    @Test
    public void testParetoWithImmediacyFactor2Point5() {
        double immediacyFactor = 2.5;
        paretoDensity = new ParetoDensity(immediacyFactor, 3, grid);

        int distance = 1;
        double expected = 1.0;
        assertEquals("[testParetoWithImmediacyFactor2Point5] At distance 1, answer should be 1.0",
                expected, paretoDensity.pareto(distance, immediacyFactor), DELTA);

        distance = 2;
        expected = Math.pow(1.0 / (double) distance, immediacyFactor);
        assertEquals("[testParetoWithImmediacyFactor2Point5] At distance 2, answer should be 1/8",
                expected, paretoDensity.pareto(distance, immediacyFactor), DELTA);

        distance = 3;
        expected = Math.pow(1.0 / (double) distance, immediacyFactor);
        assertEquals("[testParetoWithImmediacyFactor2Point5] At distance 3, answer should be 1/27",
                expected, paretoDensity.pareto(distance, immediacyFactor), DELTA);
    }

    @Test
    public void testParetoWithImmediacyFactor1() {
        double immediacyFactor = 1.0;
        paretoDensity = new ParetoDensity(immediacyFactor, 3, grid);

        int distance = 1;
        double expected = 1.0;
        assertEquals("[testParetoWithImmediacyFactor1] At distance 1, answer should be 1.0",
                expected, paretoDensity.pareto(distance, immediacyFactor), DELTA);

        distance = 2;
        expected = 1.0 / 2.0;
        assertEquals("[testParetoWithImmediacyFactor1] At distance 2, answer should be 1/2",
                expected, paretoDensity.pareto(distance, immediacyFactor), DELTA);

        distance = 3;
        expected = 1.0 / 3.0;
        assertEquals("[testParetoWithImmediacyFactor1] At distance 3, answer should be 1/3",
                expected, paretoDensity.pareto(distance, immediacyFactor), DELTA);
    }
}
