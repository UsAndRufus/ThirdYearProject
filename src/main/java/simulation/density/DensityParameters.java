package simulation.density;

public class DensityParameters {

    private double immediacyFactor; // immediacy factor is also known as k
    private int maximumDistance;

    public DensityParameters(double immediacyFactor, int maximumDistance) {
        this.immediacyFactor = immediacyFactor;
        this.maximumDistance = maximumDistance;
    }

    public double getImmediacyFactor() {
        return immediacyFactor;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }
}
