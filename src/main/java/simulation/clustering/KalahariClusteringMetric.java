package simulation.clustering;

import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Vegetation;

import java.util.*;
import java.util.stream.Collectors;

public class KalahariClusteringMetric {

    private Grid grid;

    private Set<Position> visitedPositions = new HashSet<>();

    public KalahariClusteringMetric(Grid grid) {
        this.grid = grid;
    }


    public List<Cluster> getClusters() {
        List<Cluster> clusters = new ArrayList<>();

        for (int x = 0; x < grid.getNumberOfColumns(); x++) {
            for (int y = 0; y < grid.getNumberOfRows(); y++) {
                Position currentPosition = new Position(x,y);
                if ((grid.getCell(currentPosition) instanceof Vegetation) &&
                        (!visitedPositions.contains(currentPosition))) {
                    Cluster currentCluster = createClusterFrom(currentPosition);
                    visitedPositions.addAll(currentCluster.getPositions());
                    if (currentCluster.getNumberOfPositionsInCluster() != 0) {
                        clusters.add(currentCluster);
                    }
                }
            }
        }

        return clusters;
    }

    private Cluster createClusterFrom(Position initialPosition) {
        Cluster cluster = new Cluster();

        Stack<Position> positionsToCheck = new Stack<>();
        positionsToCheck.add(initialPosition);

        while (!positionsToCheck.isEmpty()) {

            Position currentPosition = positionsToCheck.pop();

            cluster.add(currentPosition);

            positionsToCheck.addAll(
                    currentPosition.getNeighbours()
                    .stream()
                    .filter(neighbour -> (grid.getCell(neighbour) instanceof Vegetation)
                            && (!cluster.contains(neighbour)))
                    .collect(Collectors.toList()));

        }

        return cluster;
    }

}
