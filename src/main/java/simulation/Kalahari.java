package simulation;

import simulation.density.KalahariDensity;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.CellFactory;
import simulation.grid.cell.factories.CellGridFactory;
import simulation.grid.cell.factories.KalahariCellFactory;

import java.util.List;
import java.util.Random;

// TODO: this class needs tests
public class Kalahari {

    private Grid grid;
    private KalahariDensity kalahariDensity;

    private double proportionVegetation;
    private double fractionOfCellsToUpdateEveryTick;
    private int years;

    public Kalahari(KalahariParameters parameters) {

        this.proportionVegetation = parameters.getProportionVegetation();
        this.fractionOfCellsToUpdateEveryTick = parameters.getFractionOfCellsToUpdateEveryTick();
        this.years = parameters.getYears();

        CellFactory kalahariCellFactory = new KalahariCellFactory(proportionVegetation);
        CellGridFactory cellGridFactory = new CellGridFactory(kalahariCellFactory);
        this.grid = new Grid(parameters.getNumberOfRows(), parameters.getNumberOfColumns(),
                cellGridFactory);

        this.kalahariDensity = new KalahariDensity(parameters.getDensityParameters(), grid);
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

        for (Position position : positions) {
            grid.setCell(position, positionShouldTransitionTo(position));
        }
    }

    // Visible for testing
    protected Cell positionShouldTransitionTo(Position position) {
        Random random = new Random();

        double vegetationDensityAtPosition = kalahariDensity.calculateFor(position);
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

        // TODO: debug to test how often transition probability falls outside of 0-1 range

        return vegetationDensity + ((fractionalVegetationCoverWithRainfall - fractionalVegetationCover)
                                    / (1.0 - fractionalVegetationCover));
    }

    protected double calculateVegetationToNonVegetationTransitionProbability(double vegetationDensity,
                                                                             double fractionalVegetationCover,
                                                                             double fractionalVegetationCoverWithRainfall) {
        return (1.0 - vegetationDensity) + ((fractionalVegetationCover - fractionalVegetationCoverWithRainfall)
                                            / fractionalVegetationCover);
    }

    public Grid getGrid() {
        return grid;
    }
}
