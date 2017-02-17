package simulation;

import simulation.clustering.Cluster;
import simulation.clustering.ClusterStatistics;
import simulation.clustering.KalahariClusteringMetric;
import simulation.density.KalahariDensity;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;
import simulation.grid.cell.factories.CellFactory;
import simulation.grid.cell.factories.CellGridFactory;
import simulation.grid.cell.factories.KalahariCellFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

// TODO: this class needs tests
public class Kalahari {

    private Grid grid;
    private KalahariDensity kalahariDensity;
    private double proportionVegetation;

    private double fractionOfCellsToUpdateEveryTick = 0.2;
    private double immediacyFactor = 3.0;

    public Kalahari(int numberOfRows, int numberOfColumns, double proportionVegetation) {
        CellFactory kalahariCellFactory = new KalahariCellFactory(proportionVegetation);
        CellGridFactory cellGridFactory = new CellGridFactory(kalahariCellFactory);
        this.grid = new Grid(numberOfRows, numberOfColumns, cellGridFactory);

        this.kalahariDensity = new KalahariDensity(immediacyFactor, 3, grid);
        this.proportionVegetation = proportionVegetation;
    }

    public void run() {
        grid.printToConsole();
        for (int year = 0; year < 200; year++) {
            System.out.println("Tick: " + year);
            tick();
        }
        grid.printToConsole();

        KalahariClusteringMetric kalahariClusteringMetric = new KalahariClusteringMetric(grid);

        List<Cluster> clusters = kalahariClusteringMetric.getClusters();

        ClusterStatistics clusterStatistics = new ClusterStatistics(clusters);

        clusterStatistics.print();

        System.out.println("done");
    }

    private void tick() {
        long timeOfLastPrint = System.currentTimeMillis();
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
}
