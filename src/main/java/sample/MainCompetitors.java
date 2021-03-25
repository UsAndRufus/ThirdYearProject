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
        SimulationParameters simulationParameters = new SimulationParameters(500, 500, 0.2, 500);
        DensityParameters densityParameters = new DensityParameters(3.0, 10, "pareto");

        double weightingFactor = 1.0;
        double proportionSpecies1 = 0.16;
        double proportionSpecies2 = 0.16;

        /*
        Wrote image of run to data\images\competitors_1.0_pareto~2017-05-01-12-34.png
        Total number of cells: 250000
        Vegetation cells: 0.0; species 1 cells: 0.128252; species 2 cells: 0.191704; non-veg: 0.680044
        Proportion of all veg cells: 0.319956
         */

        Competitors competitors = new Competitors(simulationParameters, proportionSpecies1, proportionSpecies2,
                weightingFactor, densityParameters);

        competitors.run(true);

        output(competitors, densityParameters, simulationParameters, proportionSpecies1, proportionSpecies2, weightingFactor);

        competitors.getGrid().printStats();
    }

    private static void output(Competitors competitors, DensityParameters densityParameters,
                               SimulationParameters simulationParameters, double proportionSpecies1,
                               double proportionSpecies2, double weightingFactor) {
        ProbabilityDistribution species1ProbabilityDistribution
                = ProbabilityDistribution.createDefaultProbabilityDistribution(competitors.getGrid(),
                CompetitorSpecies1.class, densityParameters.getMetricType());

        ProbabilityDistribution species2ProbabilityDistribution
                = ProbabilityDistribution.createDefaultProbabilityDistribution(competitors.getGrid(),
                CompetitorSpecies2.class, densityParameters.getMetricType());

        SimulationRunFileWriter simulationRunFileWriter = new SimulationRunFileWriter();
        try {
            simulationRunFileWriter.writeSimulationRunToFile("competitors-s1", species1ProbabilityDistribution,
                    simulationParameters, proportionSpecies1, densityParameters);
            simulationRunFileWriter.writeSimulationRunToFile("competitors-s2", species2ProbabilityDistribution,
                    simulationParameters, proportionSpecies2, densityParameters);
//            simulationRunFileWriter.writeCompetitorsRunToFile("test-competitors", species1ProbabilityDistribution,
//                    species2ProbabilityDistribution, simulationParameters, proportionSpecies1 + proportionSpecies2,
//                    densityParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GridImageCreator gridImageCreator = new GridImageCreator();
        gridImageCreator.createImage(competitors.getGrid(), "competitors_" + weightingFactor + "_" +
                densityParameters.getMetricType());
    }
}
