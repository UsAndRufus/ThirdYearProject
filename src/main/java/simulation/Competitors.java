package simulation;

import simulation.density.DensityMetric;
import simulation.density.DensityMetricFactory;
import simulation.density.DensityParameters;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.factories.CellFactory;
import simulation.grid.cell.factories.CellGridFactory;
import simulation.grid.cell.factories.CompetitorsCellFactory;

import java.util.Random;

public class Competitors extends Simulation {

    private Random random = new Random();

    private DensityMetric densityMetric;

    public Competitors(SimulationParameters simulationParameters, double proportionSpecies1, double proportionSpecies2,
                       DensityParameters densityParameters) {
        super(simulationParameters);

        CellFactory competitorsCellFactory = new CompetitorsCellFactory(proportionSpecies1, proportionSpecies2);
        CellGridFactory cellGridFactory = new CellGridFactory(competitorsCellFactory);
        this.grid = new Grid(simulationParameters.getNumberOfRows(), simulationParameters.getNumberOfColumns(),
                cellGridFactory);

        this.densityMetric = DensityMetricFactory.createDensityMetric(densityParameters, grid);
    }

    @Override
    protected Cell positionShouldTransitionTo(Position position) {
        double vegetationDensityAtPosition = densityMetric.calculateFor(position);
        double fractionalVegetationCover = grid.getFractionalVegetationCover();


        return grid.getCell(position);
    }

    public Grid getGrid() {
        return grid;
    }
}
