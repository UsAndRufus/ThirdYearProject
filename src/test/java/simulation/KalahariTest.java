package simulation;

import org.junit.Test;
import simulation.density.DensityParameters;
import simulation.grid.TestGridFactory;
import simulation.grid.cell.factories.KalahariTestCellGridFactory;
import simulation.grid.cell.factories.TestCellFactory;

public class KalahariTest {

    private KalahariParameters kalahariParameters;

    public KalahariTest() {
        KalahariTestCellGridFactory kalahariTestCellGridFactory = new KalahariTestCellGridFactory(new TestCellFactory());
        DensityParameters densityParameters = new DensityParameters(3.0,10,"pareto");
        kalahariParameters = new KalahariParameters(kalahariTestCellGridFactory.getNumberOfRows(),
                kalahariTestCellGridFactory.getNumberOfColumns(), kalahariTestCellGridFactory.getProportionVegetation(),
                0.2, 200, densityParameters);
    }

    @Test
    public void testCalculateNonVegetationToVegetationTransitionProbability() {
        Kalahari kalahari = new Kalahari(kalahariParameters);
        kalahari.setGrid(TestGridFactory.createTestGrid());


    }
}
