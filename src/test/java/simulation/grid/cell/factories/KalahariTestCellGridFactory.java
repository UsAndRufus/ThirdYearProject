package simulation.grid.cell.factories;

import simulation.clustering.Cluster;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.NonVegetation;
import simulation.grid.cell.Vegetation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class
KalahariTestCellGridFactory extends CellGridFactory {

    private int numberOfRows = 6;
    private int numberOfColumns = 5;

    public KalahariTestCellGridFactory(CellFactory cellFactory) {
        super(cellFactory);
    }

    // Grid looks like this:
    // [~][ ][ ][ ][~]
    // [~][ ][~][ ][ ]
    // [ ][~][~][~][~]
    // [~][ ][ ][~][ ]
    // [ ][~][~][~][ ]
    // [~][~][~][~][~]

    @Override
    public Cell[][] createNewCellGrid(int numberOfRows, int numberOfColumns) {

        // should always create grid of fixed size
        Cell[][] cellGrid = new Cell[this.numberOfRows][this.numberOfColumns];

        cellGrid[0][0] = new Vegetation();
        cellGrid[0][1] = new NonVegetation();
        cellGrid[0][2] = new NonVegetation();
        cellGrid[0][3] = new NonVegetation();
        cellGrid[0][4] = new Vegetation();

        cellGrid[1][0] = new Vegetation();
        cellGrid[1][1] = new NonVegetation();
        cellGrid[1][2] = new Vegetation();
        cellGrid[1][3] = new NonVegetation();
        cellGrid[1][4] = new NonVegetation();

        cellGrid[2][0] = new NonVegetation();
        cellGrid[2][1] = new Vegetation();
        cellGrid[2][2] = new Vegetation();
        cellGrid[2][3] = new Vegetation();
        cellGrid[2][4] = new Vegetation();

        cellGrid[3][0] = new Vegetation();
        cellGrid[3][1] = new NonVegetation();
        cellGrid[3][2] = new NonVegetation();
        cellGrid[3][3] = new Vegetation();
        cellGrid[3][4] = new NonVegetation();

        cellGrid[4][0] = new NonVegetation();
        cellGrid[4][1] = new Vegetation();
        cellGrid[4][2] = new Vegetation();
        cellGrid[4][3] = new Vegetation();
        cellGrid[4][4] = new NonVegetation();

        cellGrid[5][0] = new Vegetation();
        cellGrid[5][1] = new Vegetation();
        cellGrid[5][2] = new Vegetation();
        cellGrid[5][3] = new Vegetation();
        cellGrid[5][4] = new Vegetation();

        return cellGrid;
    }

    public List<Cluster> getClusters() {
        List<Cluster> clusters = new ArrayList<>();

        Cluster size1Cluster1 = new Cluster();
        size1Cluster1.add(new Position(4,0));
        clusters.add(size1Cluster1);

        Cluster size1Cluster2 = new Cluster();
        size1Cluster2.add(new Position(0,3));
        clusters.add(size1Cluster2);

        Cluster size2Cluster = new Cluster();
        size2Cluster.add(new Position(0,0));
        size2Cluster.add(new Position(0,1));
        clusters.add(size2Cluster);

        Cluster size14Cluster = new Cluster();
        size14Cluster.add(new Position(2,1));
        size14Cluster.add(new Position(1,2));
        size14Cluster.add(new Position(2,2));
        size14Cluster.add(new Position(3,2));
        size14Cluster.add(new Position(4,2));
        size14Cluster.add(new Position(3,3));
        size14Cluster.add(new Position(1,4));
        size14Cluster.add(new Position(2,4));
        size14Cluster.add(new Position(3,4));
        size14Cluster.add(new Position(0,5));
        size14Cluster.add(new Position(1,5));
        size14Cluster.add(new Position(2,5));
        size14Cluster.add(new Position(3,5));
        size14Cluster.add(new Position(4,5));
        clusters.add(size14Cluster);

        return clusters;
    }

    public Map<Integer, Double> getProbabilities() {
        Map<Integer, Double> probabilityMap = new HashMap<>();

        probabilityMap.put(1, 2.0 / getNumberOfTrees());
        probabilityMap.put(2, 2.0 / getNumberOfTrees());
        probabilityMap.put(14, 14.0 / getNumberOfTrees());

        return probabilityMap;
    }

    public Map<Integer,Double> getCumulativeProbabilityDistribution() {
        Map<Integer, Double> cumulativeProbabilityDistribution = new HashMap<>();

        cumulativeProbabilityDistribution.put(1, 1.0);
        cumulativeProbabilityDistribution.put(2, (2.0 + 14.0) / getNumberOfTrees());
        cumulativeProbabilityDistribution.put(14, 14.0 / getNumberOfTrees());

        return cumulativeProbabilityDistribution;
    }

    public Map<Integer, Double> getNumberOfClustersDistribution() {
        Map<Integer, Double> numberOfClustersDistribution = new HashMap<>();

        numberOfClustersDistribution.put(1, 2.0);
        numberOfClustersDistribution.put(2, 1.0);
        numberOfClustersDistribution.put(14, 1.0);

        return  numberOfClustersDistribution;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfCells() {
        return numberOfColumns * numberOfRows;
    }

    public int getNumberOfTrees() {
        return getClusters().stream().mapToInt(Cluster::getNumberOfPositionsInCluster).sum();
    }

    public double getProportionVegetation() {
        return (getNumberOfTrees() * 1.0) / (getNumberOfCells() * 1.0);
    }

}
