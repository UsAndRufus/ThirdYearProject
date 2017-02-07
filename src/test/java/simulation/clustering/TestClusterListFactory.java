package simulation.clustering;

import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.TestGridFactory;

import java.util.ArrayList;
import java.util.List;

public class TestClusterListFactory {

    public static List<Cluster> getStandardTestClusterList() {
        Grid grid = TestGridFactory.createTestGrid();
        KalahariClusteringMetric kalahariClusteringMetric = new KalahariClusteringMetric(grid);
        return kalahariClusteringMetric.getClusters();
    }

    public static List<Cluster> getSmallClustersList() {
        List<Cluster> clusters = new ArrayList<>();

        clusters.add(new Cluster(new Position(1,0)));
        clusters.add(new Cluster(new Position(25,100)));
        clusters.add(new Cluster(new Position(-17,5)));
        Cluster cluster = new Cluster();
        cluster.add(new Position(3,12));
        cluster.add(new Position(4,12));
        clusters.add(cluster);

        return clusters;
    }
}
