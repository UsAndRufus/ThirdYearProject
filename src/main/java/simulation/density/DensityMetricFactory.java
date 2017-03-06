package simulation.density;

import simulation.grid.Grid;

public class DensityMetricFactory {

    public static DensityMetric createDensityMetric(DensityParameters densityParameters, Grid grid) {
        switch (densityParameters.getMetricType()) {
            case "pareto":
                return new ParetoDensity(densityParameters, grid);
            default:
                throw new IllegalArgumentException("No density metric found called "
                        + densityParameters.getMetricType() + "!");
        }

    }
}
