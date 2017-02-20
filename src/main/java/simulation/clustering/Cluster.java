package simulation.clustering;

import simulation.grid.Position;

import java.util.HashSet;
import java.util.Set;

public class Cluster {

    private Set<Position> positions = new HashSet<>();

    public Cluster() {

    }

    public Cluster(Position position) {
        add(position);
    }

    public Cluster(Set<Position> positions) {
        this.positions = positions;
    }

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
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Cluster ) {
            if (((Cluster) other).getNumberOfPositionsInCluster() == this.getNumberOfPositionsInCluster()) {
                Set<Position> thisPositions = new HashSet<>(this.positions);
                Set<Position> otherPositions = new HashSet<>(((Cluster) other).getPositions());

                thisPositions.removeAll(otherPositions);

                return (thisPositions.isEmpty());
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                " Contains: " + positions.iterator().next() +
                ", numberOfPositions: " + getNumberOfPositionsInCluster() +
                "}";
    }
}
