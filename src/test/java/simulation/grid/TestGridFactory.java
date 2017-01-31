package simulation.grid;

import simulation.grid.cell.factories.KalahariTestCellGridFactory;
import simulation.grid.cell.factories.TestCellFactory;

public class TestGridFactory {

    public static Grid createTestGrid() {
        KalahariTestCellGridFactory kalahariTestCellGridFactory
                = new KalahariTestCellGridFactory(new TestCellFactory());

        return new Grid(kalahariTestCellGridFactory.getNumberOfRows(), kalahariTestCellGridFactory.getNumberOfColumns(),
                kalahariTestCellGridFactory);
    }

}
