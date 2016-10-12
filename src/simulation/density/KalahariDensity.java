package simulation.density;

import simulation.cell.Vegetation;

public class KalahariDensity {
    private double immediacyFactor;
    private int maximumDistance;

    public KalahariDensity(double immediacyFactor, int maximumDistance) {
        this.immediacyFactor = immediacyFactor;
        this.maximumDistance = maximumDistance;
    }

    public KalahariDensity(double immediacyFactor) {
        this.immediacyFactor = immediacyFactor;
    }

    // TODO: calculate actual density
    public double calculateFor(Vegetation vegetationCellToCheck) {
        return 0.0;
    }

    public double getImmediacyFactor() {
        return immediacyFactor;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }
}
