package simulation.clustering;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.cell.factories.KalahariTestCellGridFactory;
import simulation.grid.cell.factories.TestCellFactory;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.summingDouble;
import static org.junit.Assert.*;

public class ClusterStatisticsTest {

    private static final double DOUBLE_PRECISION = 0.00001d;

    private ClusterStatistics clusterStatistics;

    private KalahariTestCellGridFactory kalahariTestCellGridFactory
            = new KalahariTestCellGridFactory(new TestCellFactory());

    @Before
    public void setUp() throws Exception {
        clusterStatistics = new ClusterStatistics(TestClusterListFactory.getStandardTestClusterList());
    }

    @Test
    public void testGetProbabilities() throws Exception {
        Map<Integer, Double> probabilityMap = clusterStatistics.getProbabilities();

        double sumOfProbabilities = probabilityMap.values().stream().collect(summingDouble(d->d));

        assertEquals("[testGetProbabilities] Sum of probabilities should be 1.0", 1.0,
                sumOfProbabilities, DOUBLE_PRECISION);

        Map<Integer, Double> expectedProbabilities = kalahariTestCellGridFactory.getProbabilities();


        assertProbabilityMapsAreEqual(expectedProbabilities, probabilityMap);
    }

    @Test
    public void testGetProbabilitiesManySmallClusters() throws Exception {
        ClusterStatistics smallClustersStatistics
                = new ClusterStatistics(TestClusterListFactory.getSmallClustersList());
        Map<Integer, Double> probabilityMap = smallClustersStatistics.getProbabilities();

        Map<Integer, Double> expectedProbabilities = new HashMap<>();
        expectedProbabilities.put(1, 0.6);
        expectedProbabilities.put(2, 0.4);

        double sumOfProbabilities = probabilityMap.values().stream().collect(summingDouble(d->d));

        assertEquals("[testGetProbabilitiesManySmallClusters] Sum of probabilities should be 1.0", 1.0,
                sumOfProbabilities, DOUBLE_PRECISION);

        assertProbabilityMapsAreEqual(expectedProbabilities, probabilityMap);
    }

    @Test
    public void testGetCumulativeProbabilityDistribution() throws Exception {

        Map<Integer, Double> cumulativeProbabilityDistributionMap =
                clusterStatistics.getCumulativeProbabilityDistribution();

        assertEquals("[testGetCumulativeProbabilityDistribution] Should be a 100% probability of being in a cluster " +
                "of size 1 or more", 1.0, cumulativeProbabilityDistributionMap.get(1), DOUBLE_PRECISION);

        Map<Integer, Double> expectedCumulativeProbabilityDistribution =
                kalahariTestCellGridFactory.getCumulativeProbabilityDistribution();

        assertProbabilityMapsAreEqual(expectedCumulativeProbabilityDistribution, cumulativeProbabilityDistributionMap);
    }

    @Test
    public void testGetNumberOfClustersDistribution() {
        Map<Integer, Double> numberOfClustersDistribution = clusterStatistics.getNumberOfClustersDistribution();

        Map<Integer, Double> expectedNumberOfClustersDistribution =
                kalahariTestCellGridFactory.getNumberOfClustersDistribution();

        assertProbabilityMapsAreEqual(expectedNumberOfClustersDistribution, numberOfClustersDistribution);

    }

    private void assertProbabilityMapsAreEqual(Map<Integer, Double> expected, Map<Integer, Double> actual) {
        for (int key : expected.keySet()) {
            double expectedProbability = expected.get(key);
            double actualProbability = actual.get(key);
            assertEquals("[testGetProbabilities] Probabilities should be the same", expectedProbability,
                    actualProbability, DOUBLE_PRECISION);
        }
    }

}