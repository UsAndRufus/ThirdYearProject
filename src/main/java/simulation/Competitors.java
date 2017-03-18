package simulation;

import simulation.density.DensityMetric;
import simulation.density.DensityMetricFactory;
import simulation.density.DensityParameters;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.*;
import simulation.grid.cell.factories.CellFactory;
import simulation.grid.cell.factories.CellGridFactory;
import simulation.grid.cell.factories.CompetitorsCellFactory;

import java.util.Random;

public class Competitors extends Simulation {

    private Random random = new Random();

    private DensityMetric densityMetric;
    private double competitionFactor;
    private double targetProportionVegetation;

    public Competitors(SimulationParameters simulationParameters, double initialProportionSpecies1,
                       double initialProportionSpecies2, double competitionFactor, DensityParameters densityParameters) {
        super(simulationParameters);

        CellFactory competitorsCellFactory = new CompetitorsCellFactory(initialProportionSpecies1, initialProportionSpecies2);
        CellGridFactory cellGridFactory = new CellGridFactory(competitorsCellFactory);
        this.grid = new Grid(simulationParameters.getNumberOfRows(), simulationParameters.getNumberOfColumns(),
                cellGridFactory);

        this.densityMetric = DensityMetricFactory.createDensityMetric(densityParameters, grid);

        this.competitionFactor = competitionFactor;

        this.targetProportionVegetation = initialProportionSpecies1 + initialProportionSpecies2;
    }

    @Override
    protected Cell positionShouldTransitionTo(Position position) {
        double fractionalVegetationCover = grid.getFractionalVegetationCover();

        double competitorSpecies1DensityAtPosition = densityMetric.calculateFor(position, CompetitorSpecies1.class);
        double competitorSpecies2DensityAtPosition = densityMetric.calculateFor(position, CompetitorSpecies2.class);

        double species1TransitionProbability = calculateCompetitorSpeciesTransitionProbability(
                competitorSpecies1DensityAtPosition, competitorSpecies2DensityAtPosition, competitionFactor,
                fractionalVegetationCover, targetProportionVegetation);

        double species2TransitionProbability = calculateCompetitorSpeciesTransitionProbability(
                competitorSpecies2DensityAtPosition, competitorSpecies1DensityAtPosition, competitionFactor,
                fractionalVegetationCover, targetProportionVegetation);

        double rand = random.nextDouble();

        if (rand < species1TransitionProbability) {
            return new CompetitorSpecies1();
        } else if (rand < (species1TransitionProbability + species2TransitionProbability)) {
            return new CompetitorSpecies2();
        } else {
            return new NonVegetation();
        }
    }

    private double calculateCompetitorSpeciesTransitionProbability(double calculatingForSpeciesDensityAtPosition,
                                                                   double competitorSpeciesDensityAtPosition,
                                                                   double competitorWeighting,
                                                                   double fractionalVegetationCover,
                                                                   double targetFractionalVegetationCover) {
        double targetTerm = ((targetFractionalVegetationCover - fractionalVegetationCover)
                / (1.0 - fractionalVegetationCover));

        double probability = calculatingForSpeciesDensityAtPosition
                - (competitorSpeciesDensityAtPosition * competitorWeighting)
                + targetTerm;

        return normaliseProbability(probability);
    }

    public Grid getGrid() {
        return grid;
    }
}
