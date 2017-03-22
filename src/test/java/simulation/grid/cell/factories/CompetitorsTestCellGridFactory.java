package simulation.grid.cell.factories;

import simulation.grid.cell.Cell;
import simulation.grid.cell.CompetitorSpecies1;
import simulation.grid.cell.CompetitorSpecies2;
import simulation.grid.cell.NonVegetation;

public class CompetitorsTestCellGridFactory extends CellGridFactory {

    private int numberOfRows = 6;
    private int numberOfColumns = 5;

    public CompetitorsTestCellGridFactory(CellFactory cellFactory) {
        super(cellFactory);
    }

    // Grid looks like this:
    // [1][ ][ ][ ][1]
    // [1][ ][2][ ][2]
    // [ ][2][2][2][ ]
    // [ ][ ][ ][2][ ]
    // [ ][ ][2][2][ ]
    // [ ][ ][1][1][1]

    @Override
    public Cell[][] createNewCellGrid(int numberOfRows, int numberOfColumns) {

        // should always create grid of fixed size
        Cell[][] cellGrid = new Cell[this.numberOfRows][this.numberOfColumns];

        cellGrid[0][0] = new CompetitorSpecies1();
        cellGrid[0][1] = new NonVegetation();
        cellGrid[0][2] = new NonVegetation();
        cellGrid[0][3] = new NonVegetation();
        cellGrid[0][4] = new CompetitorSpecies1();

        cellGrid[1][0] = new CompetitorSpecies1();
        cellGrid[1][1] = new NonVegetation();
        cellGrid[1][2] = new CompetitorSpecies2();
        cellGrid[1][3] = new NonVegetation();
        cellGrid[1][4] = new CompetitorSpecies2();

        cellGrid[2][0] = new NonVegetation();
        cellGrid[2][1] = new CompetitorSpecies2();
        cellGrid[2][2] = new CompetitorSpecies2();
        cellGrid[2][3] = new CompetitorSpecies2();
        cellGrid[2][4] = new NonVegetation();

        cellGrid[3][0] = new NonVegetation();
        cellGrid[3][1] = new NonVegetation();
        cellGrid[3][2] = new NonVegetation();
        cellGrid[3][3] = new CompetitorSpecies2();
        cellGrid[3][4] = new NonVegetation();

        cellGrid[3][0] = new NonVegetation();
        cellGrid[3][1] = new NonVegetation();
        cellGrid[3][2] = new CompetitorSpecies2();
        cellGrid[3][3] = new CompetitorSpecies2();
        cellGrid[3][4] = new NonVegetation();

        cellGrid[3][0] = new NonVegetation();
        cellGrid[3][1] = new NonVegetation();
        cellGrid[3][2] = new CompetitorSpecies1();
        cellGrid[3][3] = new CompetitorSpecies1();
        cellGrid[3][4] = new CompetitorSpecies1();

        return cellGrid;
    }

}
