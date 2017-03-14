package sample;

import output.image.GridImageCreator;
import simulation.Competitors;
import simulation.SimulationParameters;
import simulation.density.DensityParameters;

public class MainCompetitors {

    public static void main(String[] args) {
        SimulationParameters simulationParameters = new SimulationParameters(500, 500, 0.2, 200);
        DensityParameters densityParameters = new DensityParameters(3.0, 10, "pareto");

        Competitors competitors = new Competitors(simulationParameters, 0.1, 0.3, densityParameters);

        competitors.getGrid().printStats();

        GridImageCreator gridImageCreator = new GridImageCreator();
        gridImageCreator.createImage(competitors.getGrid(), densityParameters.getMetricType());
    }
}
