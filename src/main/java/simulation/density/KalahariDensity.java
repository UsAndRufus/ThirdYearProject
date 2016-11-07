package simulation.density;

import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
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
        // get number of vegetation at distance 1 -> x
        Map<Integer, Integer> numberOfVegetationAtDistance
                = getNumberOfCellsInRange(maximumDistance, position, Vegetation.class);

        // get number of non-veg at distance 1 -> x
        Map<Integer, Integer> numberOfNonVegetationAtDistance
                = getNumberOfCellsInRange(maximumDistance, position, NonVegetation.class);

        // pareto-ish thing for all

        // pareto-ish thing for veg

        // divide

        return 0.0;
    }

    //Visible for testing
    protected Map<Integer, Integer> getNumberOfCellsInRange(int maximumDistance, Position centre,
                                                          Class<? extends Cell> cellClass) {
        Map<Integer, Integer> numberOfCellsAtDistance = new HashMap<>(maximumDistance);

        for(int currentDistance = 1; currentDistance <= maximumDistance; currentDistance++) {
            int cellsAtDistance = 0;
            for(int y = centre.getY() - currentDistance; y <= centre.getY() + currentDistance; y++) {
                for(int x = centre.getX() - currentDistance; x <= centre.getX() + currentDistance; x++) {
                    Position currentPosition = new Position(x,y);
                    if (!currentPosition.equals(centre)) {
                        Cell currentCell = grid.getCell(currentPosition);
                        try {
                            if (cellClass.newInstance().getClass().isInstance(currentCell)) {
                                cellsAtDistance++;
                            }
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            numberOfCellsAtDistance.put(currentDistance, cellsAtDistance);
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
