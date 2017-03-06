package simulation.density;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import simulation.grid.Grid;
import simulation.grid.TestGridFactory;

import static org.junit.Assert.*;

public class DensityMetricFactoryTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testCreateDensityMetric() {
        Grid grid = TestGridFactory.createTestGrid();
        DensityParameters densityParameters = new DensityParameters(3.0, 10, "pareto");

        assertTrue("[testCreateDensityMetric] Should be instance of ParetoDensity",
                DensityMetricFactory.createDensityMetric(densityParameters, grid) instanceof ParetoDensity);

        exception.expect(IllegalArgumentException.class);
        densityParameters = new DensityParameters(3.0, 10, "banana");
        DensityMetricFactory.createDensityMetric(densityParameters, grid);
    }
}
