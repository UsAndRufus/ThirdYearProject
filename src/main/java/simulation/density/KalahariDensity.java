package simulation.density;

import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;

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
        // get number of vegetation at distance 1 -> x

        // get number of non-veg at distance 1 -> x

        // pareto-ish thing for all

        // pareto-ish thing for veg

        // divide

        return 0.0;
    }

    private Map<Integer, Integer> getNumberOfCellsInRange(int maximumDistance, Position position,
                                                          Class<? extends Cell> cellClass) {
        Map<Integer, Integer> numberOfCellsAtDistance = new HashMap<>(maximumDistance);

        for(int currentDistance = 1; currentDistance <= maximumDistance; currentDistance++) {
            int cellAtDistance = 0;
            for(int x = position.getX() - currentDistance; x <= position.getX() + currentDistance; x++) {
                for(int y = position.getY() - currentDistance; y <= position.getY() + currentDistance; x++) {
                }
            }
        }

        return numberOfCellsAtDistance;
    }

    public double getImmediacyFactor() {
        return immediacyFactor;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }
}
