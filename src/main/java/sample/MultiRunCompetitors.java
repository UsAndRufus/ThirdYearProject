package sample;

import output.SimulationRunFileWriter;
import simulation.Competitors;
import simulation.SimulationParameters;
import simulation.density.DensityParameters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MultiRunCompetitors {

    public static void main(String[] args) {
        double proportionSpecies1 = 0.1;
        double proportionSpecies2 = 0.3;
        double step = 0.1;
        double currentWeighting = 0.0;

        Map<Double, Double> weightingAgainstRatios = new HashMap<>();

        while (currentWeighting <= 1.0) {
            double species1ToSpecies2Ratio = run(currentWeighting, proportionSpecies1, proportionSpecies2);
            weightingAgainstRatios.put(currentWeighting, species1ToSpecies2Ratio);

            currentWeighting += step;
        }

        SimulationRunFileWriter simulationRunFileWriter = new SimulationRunFileWriter();

        try {
            simulationRunFileWriter.writeMultiRunToFile("multirun", weightingAgainstRatios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double run(double weightingFactor, double proportionSpecies1, double proportionSpecies2) {
        SimulationParameters simulationParameters = new SimulationParameters(500, 500, 0.2, 200);
        DensityParameters densityParameters = new DensityParameters(3.0, 10, "pareto");

        Competitors competitors = new Competitors(simulationParameters, proportionSpecies1, proportionSpecies2,
                weightingFactor, densityParameters);

        competitors.run(true);

        Map<String, Integer> cellTypeCounts = competitors.getGrid().getCellTypeCounts();

        double species1ToSpecies2Ratio = ((double) cellTypeCounts.get("CompetitorSpecies1"))
                / ((double) cellTypeCounts.get("CompetitorSpecies2"));

        return species1ToSpecies2Ratio;
    }
}
