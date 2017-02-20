package simulation.density;

import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.Vegetation;

import java.util.HashMap;
import java.util.Map;

public class KalahariDensity {
    private double immediacyFactor;
    private int maximumDistance;

    private Grid grid;

    public KalahariDensity(double immediacyFactor, int maximumDistance, Grid grid) {
        this.immediacyFactor = immediacyFactor;
        this.maximumDistance = maximumDistance;
        this.grid = grid;
}

    public double calculateFor(Position position) {
        Map<Integer, Integer> numberOfVegetationAtDistance
                = getNumberOfCellsInRange(maximumDistance, position, Vegetation.class);

        // TODO: can probably just precompute this rather than expensive operation
        // TODO: i.e. precompute for cells with no edge interactions, and compute for edge interactions
        // TODO: can probably precompute for edge interactions and do checking, but easier and less faff to do it the simple way
        Map<Integer, Integer> numberOfCellsAtDistance
                = getNumberOfCellsInRange(maximumDistance, position, Cell.class);

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
    protected Map<Integer, Integer> getNumberOfCellsInRange(int maximumDistance, Position centre,
                                                          Class<? extends Cell> cellClass) {
        Map<Integer, Integer> numberOfCellsAtDistance = new HashMap<>(maximumDistance);

        for(int currentDistance = 1; currentDistance <= maximumDistance; currentDistance++) {
            int cellsAtDistance = 0;
            boolean increaseY = true;
            int yChange = 0;
            for(int x = centre.getX() - currentDistance; x <= centre.getX() + currentDistance; x++) {
                for(int y = centre.getY() - yChange; y <= centre.getY() + yChange; y += yChange*2) {
                    Position currentPosition = new Position(x,y);
                    Cell currentCell = grid.getCell(currentPosition);
                    if (cellClass.isInstance(currentCell)) {
                        cellsAtDistance++;
                    }
                    if (yChange == 0) {
                        break;
                    }
                }

                if (increaseY) {
                    yChange++;
                } else {
                    yChange--;
                }

                if (yChange == currentDistance) {
                    increaseY = false;
                }

            }
            numberOfCellsAtDistance.put(currentDistance, cellsAtDistance);
        }

        return numberOfCellsAtDistance;
    }
    // Visible for testing
    protected double pareto(int distance, double immediacyFactor) {
        return Math.pow(1.0 / (double) distance, immediacyFactor);
    }

    public double getImmediacyFactor() {
        return immediacyFactor;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }
}
