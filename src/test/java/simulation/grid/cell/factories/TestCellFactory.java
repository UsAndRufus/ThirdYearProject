package simulation.grid.cell.factories;

import simulation.grid.cell.Cell;
import simulation.grid.cell.TestCell;

public class TestCellFactory extends CellFactory {
    @Override
    public Cell createCell() {
        return new TestCell();
    }
}
