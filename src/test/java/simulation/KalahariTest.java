package simulation;

import org.junit.Test;
import simulation.density.DensityParameters;
import simulation.grid.TestGridFactory;
import simulation.grid.cell.factories.KalahariTestCellGridFactory;
import simulation.grid.cell.factories.TestCellFactory;

public class KalahariTest {

    public KalahariTest() {
        KalahariTestCellGridFactory kalahariTestCellGridFactory = new KalahariTestCellGridFactory(new TestCellFactory());
        DensityParameters densityParameters = new DensityParameters(3.0,10,"pareto");
    }

    @Test
    public void testCalculateNonVegetationToVegetationTransitionProbability() {


    }
}
