package simulation.clustering;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.Grid;
import simulation.grid.TestGridFactory;
import simulation.grid.cell.factories.KalahariTestCellGridFactory;
import simulation.grid.cell.factories.TestCellFactory;

import java.util.List;

import static org.junit.Assert.*;

public class KalahariClusteringMetricTest {

    private Grid grid;
    private KalahariClusteringMetric kalahariClusteringMetric;
    private KalahariTestCellGridFactory kalahariTestCellGridFactory;

    @Before
    public void setUp() throws Exception {
        grid = TestGridFactory.createTestGrid();
        kalahariClusteringMetric = new KalahariClusteringMetric(grid);

        kalahariTestCellGridFactory = new KalahariTestCellGridFactory(new TestCellFactory());
    }

    @Test
    public void testGetClusters() throws Exception {
        List<Cluster> createdClusters = kalahariClusteringMetric.getClusters();

        List<Cluster> expectedClusters = kalahariTestCellGridFactory.getClusters();

        assertEquals("[testGetClusters] Amount of clusters should be equal", expectedClusters.size(), createdClusters.size());

        createdClusters.removeAll(expectedClusters);
        assertEquals("[testGetClusters] If lists contain the same clusters, the negated union should be empty",
                0, createdClusters.size());
    }

}