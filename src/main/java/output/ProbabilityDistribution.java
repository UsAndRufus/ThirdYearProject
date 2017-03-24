package output;

import simulation.clustering.Cluster;
import simulation.clustering.ClusterStatistics;
import simulation.clustering.KalahariClusteringMetric;
import simulation.grid.Grid;
import simulation.grid.cell.Cell;
import simulation.grid.cell.Vegetation;

import java.util.List;
import java.util.Map;

public class ProbabilityDistribution {

    private Map<Integer, Double> distributionMap;
    private String name;
    private String integerColumnName;
    private String doubleColumnName;

    public ProbabilityDistribution(Map<Integer, Double> distributionMap, String name,
                                   String integerColumnName, String doubleColumnName) {
        this.distributionMap = distributionMap;
        this.name = name;
        this.integerColumnName = integerColumnName;
        this.doubleColumnName = doubleColumnName;
    }

    public Map<Integer, Double> getDistributionMap() {
        return distributionMap;
    }

    public String getName() {
        return name;
    }

    public String getIntegerColumnName() {
        return integerColumnName;
    }

    public String getDoubleColumnName() {
        return doubleColumnName;
    }

    public static ProbabilityDistribution createDefaultProbabilityDistribution(Grid grid,
                                                                               Class<? extends Cell> cellClass,
                                                                               String metricType) {
        KalahariClusteringMetric kalahariClusteringMetric = new KalahariClusteringMetric(grid);

        List<Cluster> clusters = kalahariClusteringMetric.getClusters(cellClass);

        ClusterStatistics clusterStatistics = new ClusterStatistics(clusters);

        Map<Integer, Double> distribution = clusterStatistics.getCumulativeProbabilitiesNew();

        return new ProbabilityDistribution(distribution, metricType, "Cluster size",
                 "P(A>=a)");
    }
}
