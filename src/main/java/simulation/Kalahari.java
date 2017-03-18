package simulation;

import simulation.density.DensityMetric;
import simulation.density.DensityMetricFactory;
import simulation.density.DensityParameters;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.CellFactory;
import simulation.grid.cell.factories.CellGridFactory;
import simulation.grid.cell.factories.KalahariCellFactory;

import java.util.Random;

// TODO: this class needs tests
public class Kalahari extends Simulation {

    // change this if neutral model
    private static final double PROPORTION_VEGETATION_TO_START_WITH = 0.5;

    private DensityMetric densityMetric;

    private double proportionVegetation;

    public Kalahari(SimulationParameters simulationParameters, double proportionVegetation,
                    DensityParameters densityParameters) {

        super(simulationParameters);

        this.proportionVegetation = proportionVegetation;

        CellFactory kalahariCellFactory = new KalahariCellFactory(proportionVegetation);
        CellGridFactory cellGridFactory = new CellGridFactory(kalahariCellFactory);
        this.grid = new Grid(simulationParameters.getNumberOfRows(), simulationParameters.getNumberOfColumns(),
                cellGridFactory);

        this.densityMetric = DensityMetricFactory.createDensityMetric(densityParameters, grid);
    }

    // Visible for testing
    protected Cell positionShouldTransitionTo(Position position) {
        Random random = new Random();

        double vegetationDensityAtPosition = densityMetric.calculateFor(position, Vegetation.class);
        double fractionalVegetationCover = grid.getFractionalVegetationCover();

        if (grid.getCell(position) instanceof Vegetation) {
            if (random.nextDouble() < calculateNonVegetationTransitionProbability(
                    vegetationDensityAtPosition, fractionalVegetationCover, proportionVegetation)) {
                return new NonVegetation();
            } else {
                return new Vegetation();
            }
        } else {
            if (random.nextDouble() < calculateNonVegetationToVegetationTransitionProbability(
                    vegetationDensityAtPosition,fractionalVegetationCover, proportionVegetation)) {
                return new Vegetation();
            } else {
                return new NonVegetation();
            }
        }
    }

    // Visible for testing
    // TODO: Work out what fractionalVegetationCoverWithRainfall really is!!
    protected double calculateNonVegetationToVegetationTransitionProbability(double vegetationDensity,
                                                                          double fractionalVegetationCover,
                                                                          double fractionalVegetationCoverWithRainfall) {

        double probability = vegetationDensity + ((fractionalVegetationCoverWithRainfall - fractionalVegetationCover)
                / (1.0 - fractionalVegetationCover));

        return normaliseProbability(probability);
    }

    public Grid getGrid() {
        return grid;
    }

    // Visible for testing
    // Can't be bothered making a Kalahari factory so this is a dirty way to test the class
    protected void setGrid(Grid grid) {
        this.grid = grid;
    }
}
