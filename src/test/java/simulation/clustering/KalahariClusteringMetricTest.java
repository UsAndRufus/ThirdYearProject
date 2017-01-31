package simulation.clustering;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.Grid;
import simulation.grid.TestGridFactory;

import java.util.List;

import static org.junit.Assert.*;

public class KalahariClusteringMetricTest {

    private Grid grid;
    private KalahariClusteringMetric kalahariClusteringMetric;

    @Before
    public void setUp() throws Exception {
        grid = TestGridFactory.createTestGrid();
        kalahariClusteringMetric = new KalahariClusteringMetric(grid);
    }

    @Test
    public void testGetClusters() throws Exception {
        List<Cluster> clusters = kalahariClusteringMetric.getClusters();

        clusters.sort((a,b) -> Integer.compare(b.getNumberOfPositionsInCluster(), a.getNumberOfPositionsInCluster()));

        assertEquals("[testGetClusters] Should have 4 clusters in the grid", 4, clusters.size());

        assertEquals("[testGetClusters] The biggest cluster should have 14 positions",
                14, clusters.get(0).getNumberOfPositionsInCluster());

        assertEquals("[testGetClusters] The second biggest cluster should have 2 positions",
                2, clusters.get(1).getNumberOfPositionsInCluster());

        assertEquals("[testGetClusters] The 3rd biggest cluster should have 1 position",
                1, clusters.get(2).getNumberOfPositionsInCluster());

        assertEquals("[testGetClusters] The 4th biggest cluster should have 1 position",
                1, clusters.get(3).getNumberOfPositionsInCluster());
    }

}