package sample;

import output.ProbabilityDistribution;
import output.SimulationRunFileWriter;
import output.image.GridImageCreator;
import simulation.Competitors;
import simulation.SimulationParameters;
import simulation.clustering.Cluster;
import simulation.clustering.ClusterStatistics;
import simulation.clustering.KalahariClusteringMetric;
import simulation.density.DensityParameters;
import simulation.grid.cell.CompetitorSpecies1;
import simulation.grid.cell.CompetitorSpecies2;

import java.io.IOException;
import java.util.List;

public class MainCompetitors {

    public static void main(String[] args) {
        SimulationParameters simulationParameters = new SimulationParameters(500, 500, 0.2, 200);
        DensityParameters densityParameters = new DensityParameters(3.0, 10, "pareto");

        double weightingFactor = 0.1;
        double proportionSpecies1 = 0.1;
        double proportionSpecies2 = 0.3;

        Competitors competitors = new Competitors(simulationParameters, proportionSpecies1, proportionSpecies2,
                weightingFactor, densityParameters);

        competitors.run(true);

        ProbabilityDistribution species1ProbabilityDistribution
                = ProbabilityDistribution.createDefaultProbabilityDistribution(competitors.getGrid(),
                CompetitorSpecies1.class, densityParameters.getMetricType());

        ProbabilityDistribution species2ProbabilityDistribution
                = ProbabilityDistribution.createDefaultProbabilityDistribution(competitors.getGrid(),
                CompetitorSpecies2.class, densityParameters.getMetricType());

        competitors.getGrid().printStats();

        SimulationRunFileWriter simulationRunFileWriter = new SimulationRunFileWriter();
        try {
            simulationRunFileWriter.writeSimulationRunToFile("competitors-s1", species1ProbabilityDistribution,
                    simulationParameters, proportionSpecies1, densityParameters);
            simulationRunFileWriter.writeSimulationRunToFile("competitors-s2", species2ProbabilityDistribution,
                    simulationParameters, proportionSpecies1, densityParameters);
//            simulationRunFileWriter.writeCompetitorsRunToFile("test-competitors", species1ProbabilityDistribution,
//                    species2ProbabilityDistribution, simulationParameters, proportionSpecies1 + proportionSpecies2,
//                    densityParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }

        competitors.getGrid().printStats();

        GridImageCreator gridImageCreator = new GridImageCreator();
        gridImageCreator.createImage(competitors.getGrid(), "competitors_" + weightingFactor + "_" +
                densityParameters.getMetricType());
    }
}
