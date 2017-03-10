package simulation.clustering;

import java.util.*;
import java.util.stream.Collectors;

public class ClusterStatistics {

    private List<Cluster> clusters;
    private double numberOfPositions;

    public ClusterStatistics(List<Cluster> clusters) {
        clusters.sort(Comparator.comparing(Cluster::getNumberOfPositionsInCluster).reversed());
        this.clusters = clusters;

        this.numberOfPositions = clusters
                                    .stream()
                                    .mapToDouble(Cluster::getNumberOfPositionsInCluster)
                                    .sum();
    }

    public Map<Integer, Double> getProbabilities() {
        Map<Integer, Double> probabilityMap = new HashMap<>();

        for (Cluster cluster : clusters) {
            int clusterSize = cluster.getNumberOfPositionsInCluster();
            double probability = (double) clusterSize / numberOfPositions;

            if (!probabilityMap.containsKey(clusterSize)) {
                probabilityMap.put(clusterSize, probability);
            } else {
                double currentProbability = probabilityMap.get(clusterSize);
                probabilityMap.put(clusterSize, probability + currentProbability);
            }
        }

        return probabilityMap;
    }

    public Map<Integer, Double> getCumulativeProbabilitiesNew() {

        // get cluster size count
        Map<Integer, Integer> clusterSizeCount = new HashMap<>();
        for (Cluster cluster : clusters) {
            int clusterSize = cluster.getNumberOfPositionsInCluster();
            if (!clusterSizeCount.containsKey(clusterSize)) {
                clusterSizeCount.put(clusterSize, 16);
            } else {
                int currentCount = clusterSizeCount.get(clusterSize);
                clusterSizeCount.put(clusterSize, currentCount + 16);
            }
        }

        // get CUMULATIVE cluster size count
        // i.e. if there is a cluster of size 745, there are 745 size 1 clusters in it, 372 size 2 clusters in it, etc

        // if we have 1 cluster of size 1, one of size 2, and one of size 3
        // P(A>=1) = 6/6, P(A>=2) = 2 / 6, P(A>=3) = 1/6

        List<Integer> clusterSizeKeys = clusterSizeCount.keySet().stream().sorted().collect(Collectors.toList());
        List<Integer> clusterSizeKeysRemaining = new ArrayList<>(clusterSizeKeys);

        Map<Integer, Integer> cumulativeClusterSizeCount = new HashMap<>();
        for (Integer clusterSize : clusterSizeKeys) {
            int currentSum = clusterSizeCount.get(clusterSize);
            clusterSizeKeysRemaining.remove(clusterSize); // already added
            for (Integer clusterSizeToAdd : clusterSizeKeysRemaining) {
                currentSum += (clusterSizeCount.get(clusterSizeToAdd) * clusterSizeToAdd) / clusterSize;
            }
            cumulativeClusterSizeCount.put(clusterSize,currentSum);
        }

        // divide all cumulative cluster sizes sums by total cumulative number of cells
        double cumulativeNumberOfCells = cumulativeClusterSizeCount.get(1);

        Map<Integer, Double> cumulativeProbabilityDistribution = new HashMap<>();

        for (Integer size : cumulativeClusterSizeCount.keySet()) {
            cumulativeProbabilityDistribution.put(size, cumulativeClusterSizeCount.get(size) / cumulativeNumberOfCells);
        }


        return cumulativeProbabilityDistribution;
    }

    public Map<Integer, Double> getCumulativeProbabilityDistribution() {
        Map<Integer, Double> probabilityMap = getProbabilities();

        Map<Integer, Double> cumulativeProbabilityDistribution = new HashMap<>();

        List<Integer> clusterSizes = probabilityMap.keySet().stream().sorted().collect(Collectors.toList());
        List<Integer> clusterSizesRemaining = new ArrayList<>(clusterSizes);

        for (Integer clusterSize : clusterSizes) {
            double probability = 0;
            for (Integer clusterSizeToAdd : clusterSizesRemaining) {
                probability += probabilityMap.get(clusterSizeToAdd);
            }
            clusterSizesRemaining.remove(clusterSize);
            cumulativeProbabilityDistribution.put(clusterSize, probability);
        }

        return cumulativeProbabilityDistribution;
    }

    // x = cluster size, y = number of clusters of that size
    public Map<Integer, Double> getNumberOfClustersDistribution() {
        Map<Integer, Double> clusterSizeAgainstNumberOfClusters = new HashMap<>();

        for (Cluster cluster : clusters) {
            int clusterSize = cluster.getNumberOfPositionsInCluster();
            if (!clusterSizeAgainstNumberOfClusters.containsKey(clusterSize)) {
                clusterSizeAgainstNumberOfClusters.put(clusterSize, 1.0);
            } else {
                double currentNumberOfClusters = clusterSizeAgainstNumberOfClusters.get(clusterSize);
                clusterSizeAgainstNumberOfClusters.put(clusterSize, currentNumberOfClusters + 1);
            }
        }

        return clusterSizeAgainstNumberOfClusters;
    }

    public void printNew() {
        Map<Integer, Double> cumulativeProbabilityDistribution = getCumulativeProbabilitiesNew();

        cumulativeProbabilityDistribution
                .keySet()
                .stream()
                .sorted(Comparator.reverseOrder())
                .map(clusterSize -> "P(A>=" + clusterSize + ") = " + cumulativeProbabilityDistribution.get(clusterSize))
                .forEach(System.out::println);
    }

    public void print() {
        System.out.println("There are " + clusters.size() + " clusters");

        Map<Integer, Double> probabilities = getProbabilities();

        probabilities
                .keySet()
                .stream()
                .sorted(Comparator.reverseOrder())
                .map(clusterSize -> "Probability of cell being in cluster size " + clusterSize + ": " + probabilities.get(clusterSize))
                .forEach(System.out::println);

        Map<Integer, Double> cumulativeProbabilityDistribution = getCumulativeProbabilityDistribution();

        cumulativeProbabilityDistribution
                .keySet()
                .stream()
                .sorted(Comparator.reverseOrder())
                .map(clusterSize -> "P(A>=" + clusterSize + ") = " + cumulativeProbabilityDistribution.get(clusterSize))
                .forEach(System.out::println);
    }
}
