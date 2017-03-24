package simulation.clustering;

import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.Vegetation;

import java.util.*;
import java.util.stream.Collectors;

public class KalahariClusteringMetric {

    private Grid grid;

    private Set<Position> visitedPositions = new HashSet<>();

    public KalahariClusteringMetric(Grid grid) {
        this.grid = grid;
    }


    public List<Cluster> getClusters(Class<? extends Cell> cellClass) {
        List<Cluster> clusters = new ArrayList<>();

        for (int x = 0; x < grid.getNumberOfColumns(); x++) {
            for (int y = 0; y < grid.getNumberOfRows(); y++) {
                Position currentPosition = new Position(x,y);
                if ((cellClass.isInstance(grid.getCell(currentPosition))) &&
                        (!visitedPositions.contains(currentPosition))) {
                    Cluster currentCluster = createClusterFrom(currentPosition, cellClass);
                    visitedPositions.addAll(currentCluster.getPositions());
                    if (currentCluster.getNumberOfPositionsInCluster() != 0) {
                        clusters.add(currentCluster);
                    }
                }
            }
        }

        return clusters;
    }

    private Cluster createClusterFrom(Position initialPosition, Class<? extends Cell> cellClass) {
        Cluster cluster = new Cluster();

        Stack<Position> positionsToCheck = new Stack<>();
        positionsToCheck.add(initialPosition);

        while (!positionsToCheck.isEmpty()) {

            Position currentPosition = positionsToCheck.pop();

            cluster.add(currentPosition);

            positionsToCheck.addAll(
                    currentPosition.getNeighbours()
                    .stream()
                    .filter(neighbour -> (cellClass.isInstance(grid.getCell(neighbour)))
                            && (!cluster.contains(neighbour)))
                    .collect(Collectors.toList()));

        }

        return cluster;
    }

}
