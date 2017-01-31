package simulation.clustering;

import simulation.grid.Position;

import java.util.HashSet;
import java.util.Set;

public class Cluster {

    private Set<Position> positions = new HashSet<>();

    public boolean contains(Position position) {
       return positions.contains(position);
    }

    public boolean add(Position position) {
       return positions.add(position);
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public int getNumberOfPositionsInCluster() {
        return positions.size();
    }

    @Override
    public String toString() {
        return "Cluster{" +
                " Contains: " + positions.iterator().next() +
                ", numberOfPositions: " + getNumberOfPositionsInCluster() +
                "}";
    }
}
