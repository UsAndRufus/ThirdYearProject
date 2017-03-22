package simulation.density;

import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.Vegetation;

import java.util.Map;

public class ExponentialDensity extends DensityMetric{

    public ExponentialDensity(DensityParameters densityParameters, Grid grid) {
        super(densityParameters, grid);
    }

    public ExponentialDensity(double immediacyFactor, int maximumDistance, Grid grid) {
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

        double sumForAll = 0.0;
        double sumForVegetation = 0.0;

        for (int distance = 1; distance <= maximumDistance; distance++) {
            double exponentialForDistance = Math.exp(-1.0 * immediacyFactor * distance);
            sumForAll += exponentialForDistance * numberOfCellsAtDistance.get(distance);
            sumForVegetation += exponentialForDistance * numberOfVegetationAtDistance.get(distance);
        }

        return sumForVegetation / sumForAll;
    }
}
