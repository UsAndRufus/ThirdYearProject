package simulation.clustering;

import org.junit.Before;
import org.junit.Test;
import simulation.grid.Position;

import static org.junit.Assert.*;

public class ClusterTest {

    private Cluster emptyCluster;
    private Cluster clusterWithPositions;
    private Cluster unequalCluster;
    private Cluster equalCluster;

    @Before
    public void setUp() throws Exception {
        emptyCluster = new Cluster();

        clusterWithPositions = new Cluster();
        clusterWithPositions.add(new Position(0,0));
        clusterWithPositions.add(new Position(10,23));

        unequalCluster = new Cluster();
        unequalCluster.add(new Position(0, 0));
        unequalCluster.add(new Position(-100, 3000));

        equalCluster = new Cluster();
        equalCluster.add(new Position(0,0));
        equalCluster.add(new Position(10,23));

    }

    @Test
    public void testEquals() throws Exception {
        assertNotEquals("[testEquals] Cluster should not be equal to a non-Cluster object",
                clusterWithPositions, "bananas");

        assertNotEquals("[testEquals] Cluster with items in it should not be equal to an empty cluster",
                clusterWithPositions, emptyCluster);

        assertNotEquals("[testEquals] Clusters with different contents should not be equal",
                clusterWithPositions, unequalCluster);

        assertEquals("[testEquals] Clusters with same contents should be equal", clusterWithPositions, equalCluster);

        assertEquals("[testEquals] Empty clusters should be equal in size", emptyCluster, new Cluster());
    }

}