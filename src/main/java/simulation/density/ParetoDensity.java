package simulation.density;

import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.Vegetation;

import java.util.Map;

public class ParetoDensity extends DensityMetric {
    public ParetoDensity(DensityParameters densityParameters, Grid grid) {
        super(densityParameters, grid);
    }

    public ParetoDensity(double immediacyFactor, int maximumDistance, Grid grid) {
        super(immediacyFactor, maximumDistance, grid);
    }

    @Override
    public double calculateFor(Position position, Class<? extends Cell> cellClass) {
        Map<Integer, Integer> numberOfVegetationAtDistance
                = getNumberOfCellsInRange(maximumDistance, position, cellClass);

        Map<Integer, Integer> numberOfCellsAtDistance;
        if ((!grid.isRangeOutOfBounds(position, maximumDistance))) {
            numberOfCellsAtDistance = this.generalNumberOfCellsAtDistance;
        } else {
            numberOfCellsAtDistance = getNumberOfCellsInRange(maximumDistance, position, Cell.class);
        }

        double paretoSumForAll = 0.0;
        double paretoSumForVegetation = 0.0;

        for (int distance = 1; distance <= maximumDistance; distance++) {
            double paretoForDistance = pareto(distance, immediacyFactor);
            paretoSumForAll += paretoForDistance * numberOfCellsAtDistance.get(distance);
            paretoSumForVegetation += paretoForDistance * numberOfVegetationAtDistance.get(distance);
        }

        return paretoSumForVegetation / paretoSumForAll;
    }

    // Visible for testing
    protected double pareto(int distance, double immediacyFactor) {
        return Math.pow(1.0 / (double) distance, immediacyFactor);
    }
}
