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
            double probability = clusterSize / numberOfPositions;

            if (!probabilityMap.containsKey(clusterSize)) {
                probabilityMap.put(clusterSize, probability);
            } else {
                probabilityMap.put(clusterSize, probability * 2);
            }
        }

        return probabilityMap;
    }

    // TODO: does this work? tests pls x
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
