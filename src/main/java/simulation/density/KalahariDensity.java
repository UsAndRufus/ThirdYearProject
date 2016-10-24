package simulation.density;

import simulation.grid.Grid;

public class KalahariDensity {
    private double immediacyFactor;
    private int maximumDistance;

    public KalahariDensity(double immediacyFactor, int maximumDistance) {
        this.immediacyFactor = immediacyFactor;
        this.maximumDistance = maximumDistance;
    }

    public double calculateFor(Grid grid, int row, int column) {
        return 0.0;
    }

    public double getImmediacyFactor() {
        return immediacyFactor;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }
}
