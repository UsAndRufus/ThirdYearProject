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

    private Map<Integer, Integer> generalNumberOfCellsAtDistance;

    public KalahariDensity(double immediacyFactor, int maximumDistance, Grid grid) {
        this.immediacyFactor = immediacyFactor;
        this.maximumDistance = maximumDistance;
        this.grid = grid;

        this.generalNumberOfCellsAtDistance = getGeneralNumberOfCellsAtDistance(maximumDistance);
    }

    public double calculateFor(Position position) {
        Map<Integer, Integer> numberOfVegetationAtDistance
                = getNumberOfCellsInRange(maximumDistance, position, Vegetation.class);

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
    protected Map<Integer, Integer> getGeneralNumberOfCellsAtDistance(int maximumDistance) {
        Map<Integer, Integer> generalNumberOfCellsAtDistance = new HashMap<>(maximumDistance);

        for (int currentDistance = 1; currentDistance <= maximumDistance; currentDistance++) {
            // original equation from http://mathworld.wolfram.com/vonNeumannNeighborhood.html
            // but we just want the current "layer"
            // happens to be 4*d
            int numberOfCells = 4 * currentDistance;

            generalNumberOfCellsAtDistance.put(currentDistance, numberOfCells);
        }

        return generalNumberOfCellsAtDistance;
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
