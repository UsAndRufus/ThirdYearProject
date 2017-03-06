package simulation.density;

public class DensityParameters {

    private double immediacyFactor; // immediacy factor is also known as k
    private int maximumDistance;
    private String metricType;

    public DensityParameters(double immediacyFactor, int maximumDistance, String metricType) {
        this.immediacyFactor = immediacyFactor;
        this.maximumDistance = maximumDistance;
        this.metricType = metricType;
    }

    public double getImmediacyFactor() {
        return immediacyFactor;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }

    public String getMetricType() {
        return metricType;
    }
}
