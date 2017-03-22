package simulation.density;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.TestGridFactory;
import simulation.grid.cell.Vegetation;

import static org.junit.Assert.assertEquals;

public class ExponentialDensityTest {
        private static final double DELTA = 0.00001d;

        private ExponentialDensity exponentialDensity;
        private double immediacyFactor = 0.2;

        private Grid grid;

        @Before
        public void setUp() throws Exception {
            grid = TestGridFactory.createTestGrid();
        }

        @Test
        public void testCalculateForWithMaximumDistance1() throws Exception {
            exponentialDensity = new ExponentialDensity(immediacyFactor, 1, grid);

            int x = 2;
            int y = 2;
            double expected = 0.75; // did the maths on paper yo

            assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                    expected, exponentialDensity.calculateFor(new Position(x, y), Vegetation.class), DELTA);
        }

        @Test
        public void testCalculateForWithMaximumDistance2() throws Exception {
            exponentialDensity = new ExponentialDensity(immediacyFactor, 2, grid);

            int x = 2;
            int y = 2;
            double expected = 0.5171821699; // did the maths on paper yo

            assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                    expected, exponentialDensity.calculateFor(new Position(x, y), Vegetation.class), DELTA);

            x = 4;
            y = 5;
            expected = 0.5918643884; // did the maths on paper yo

            assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                    expected, exponentialDensity.calculateFor(new Position(x, y), Vegetation.class), DELTA);
        }

        @Test
        public void testCalculateForWithMaximumDistance3() throws Exception {
            exponentialDensity = new ExponentialDensity(immediacyFactor, 3, grid);

            int x = 2;
            int y = 2;
            double expected = 0.5311426032; // did the maths on paper yo

            assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                    expected, exponentialDensity.calculateFor(new Position(x, y), Vegetation.class), DELTA);

            x = 4;
            y = 5;
            expected = 0.7451856007; // did the maths on paper yo

            assertEquals("[testCalculateFor] The density given should be the one from hand calculation (within delta)",
                    expected, exponentialDensity.calculateFor(new Position(x, y), Vegetation.class), DELTA);
        }
}
