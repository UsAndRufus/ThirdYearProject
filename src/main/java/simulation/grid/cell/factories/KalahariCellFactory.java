package simulation.grid.cell.factories;

import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;

import java.util.Random;

public class KalahariCellFactory extends CellFactory {

    private double proportionVegetation;
    private Random random = new Random();

    public KalahariCellFactory(double proportionVegetation) {
        this.proportionVegetation = proportionVegetation;
    }

    @Override
    public Cell createCell() {
        if (random.nextDouble() <= proportionVegetation) {
            return new Vegetation();
        } else {
            return new NonVegetation();
        }
    }
}
