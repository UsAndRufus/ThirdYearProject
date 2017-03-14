package simulation.grid.cell.factories;

import simulation.grid.cell.*;

import java.util.Random;

public class CompetitorsCellFactory extends CellFactory {

    private double proportionSpecies1, proportionSpecies2;
    private Random random = new Random();

    public CompetitorsCellFactory(double proportionSpecies1, double proportionSpecies2) {
        this.proportionSpecies1 = proportionSpecies1;
        this.proportionSpecies2 = proportionSpecies2;

        if ((proportionSpecies1 + proportionSpecies2) > 1.0) {
            throw new IllegalArgumentException("Species proportions add up to greater than 1.0");
        }
    }

    @Override
    public Cell createCell() {
        double randomDouble = random.nextDouble();
        if (randomDouble <= proportionSpecies1) {
            return new CompetitorSpecies1();
        } else if  (randomDouble <= (proportionSpecies1 + proportionSpecies2)) {
            return new CompetitorSpecies2();
        } else {
            return new NonVegetation();
        }
    }
}
