package simulation;

import simulation.density.DensityMetric;
import simulation.density.DensityMetricFactory;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.CellFactory;
import simulation.grid.cell.factories.CellGridFactory;
import simulation.grid.cell.factories.KalahariCellFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// TODO: this class needs tests
public class Kalahari {

    // change this if neutral model
    private static final double PROPORTION_VEGETATION_TO_START_WITH = 0.5;

    private Grid grid;
    private DensityMetric densityMetric;

    private double proportionVegetation;
    private double fractionOfCellsToUpdateEveryTick;
    private int years;

    public Kalahari(KalahariParameters parameters) {

        this.proportionVegetation = parameters.getProportionVegetation();
        this.fractionOfCellsToUpdateEveryTick = parameters.getFractionOfCellsToUpdateEveryTick();
        this.years = parameters.getYears();

        CellFactory kalahariCellFactory = new KalahariCellFactory(PROPORTION_VEGETATION_TO_START_WITH);
        CellGridFactory cellGridFactory = new CellGridFactory(kalahariCellFactory);
        this.grid = new Grid(parameters.getNumberOfRows(), parameters.getNumberOfColumns(),
                cellGridFactory);

        this.densityMetric = DensityMetricFactory.createDensityMetric(parameters.getDensityParameters(), grid);
    }

    public void run(boolean print) {

        if (print) {
            System.out.println("Starting simulation");
        }

        if (print) {
            for (int year = 0; year < years; year ++) {
                System.out.print("X");
            }
            System.out.println();
        }

        for (int year = 0; year < years; year++) {
            tick();
            if (print) {
                System.out.print("X");
            }

        }
        if (print) {
            System.out.println();

            System.out.println("done");
        }

    }

    private void tick() {
        List<Position> positions = grid.getRandomPositions(fractionOfCellsToUpdateEveryTick);

        Map<Position, Cell> transitions = new HashMap<>(positions.size());

        for (Position position : positions) {
            transitions.put(position, positionShouldTransitionTo(position));
        }

        for (Position position : transitions.keySet()) {
            grid.setCell(position, transitions.get(position));
        }
    }

    // Visible for testing
    protected Cell positionShouldTransitionTo(Position position) {
        Random random = new Random();

        double vegetationDensityAtPosition = densityMetric.calculateFor(position);
        double fractionalVegetationCover = grid.getFractionalVegetationCover();

        if (grid.getCell(position) instanceof Vegetation) {
            if (random.nextDouble() < calculateVegetationToNonVegetationTransitionProbability(
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

        if (probability > 1.0) {
            probability = 1.0;
        } else if (probability < 0.0) {
            probability = 0.0;
        }


        return probability;
    }

    protected double calculateVegetationToNonVegetationTransitionProbability(double vegetationDensity,
                                                                             double fractionalVegetationCover,
                                                                             double fractionalVegetationCoverWithRainfall) {

        double probability = (1.0 - vegetationDensity) + ((fractionalVegetationCover - fractionalVegetationCoverWithRainfall)
                / fractionalVegetationCover);

        if (probability > 1.0) {
            probability = 1.0;
        } else if (probability < 0.0) {
            probability = 0.0;
        }

        return probability;
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
