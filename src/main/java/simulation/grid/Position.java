package simulation.grid;

import java.util.HashSet;
import java.util.Set;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Set<Position> getNeighbours() {
        Set<Position> neighbours = new HashSet<>(4);

        neighbours.add(new Position(x-1,y));
        neighbours.add(new Position(x+1,y));
        neighbours.add(new Position(x,y-1));
        neighbours.add(new Position(x,y+1));

        return neighbours;
    }

    @Override
    public int hashCode() {
        // Stolen from javafx.util.pair
        // x is multiplied by an arbitrary prime number (29)
        // in order to make sure there is a difference in the hashCode between
        // these two parameters:
        //  name: a  value: aa
        //  name: aa value: a
        return x * 29 + (y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Position) {
            return ((this.x == ((Position) other).getX()) && (this.y == ((Position) other).getY()));
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
