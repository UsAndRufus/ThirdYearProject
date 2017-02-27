package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import output.ProbabilityDistribution;
import output.SimulationRunFileWriter;
import simulation.Kalahari;
import simulation.KalahariParameters;
import simulation.clustering.Cluster;
import simulation.clustering.ClusterStatistics;
import simulation.clustering.KalahariClusteringMetric;
import simulation.density.DensityParameters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
        //primaryStage.show();


    }


    public static void main(String[] args) {
        //launch(args);

        DensityParameters densityParameters = new DensityParameters(3.0,5);

        KalahariParameters kalahariParameters = new KalahariParameters(500, 500, 0.3, 0.2, 200, densityParameters);

        Kalahari kalahari = new Kalahari(kalahariParameters);

        long start = System.currentTimeMillis();
        kalahari.run();
        System.out.println("Run took " + (System.currentTimeMillis() - start) + "ms");

        KalahariClusteringMetric kalahariClusteringMetric = new KalahariClusteringMetric(kalahari.getGrid());

        List<Cluster> clusters = kalahariClusteringMetric.getClusters();

        ClusterStatistics clusterStatistics = new ClusterStatistics(clusters);

        Map<Integer, Double> probabilityMap = clusterStatistics.getCumulativeProbabilityDistribution();

        SimulationRunFileWriter simulationRunFileWriter = new SimulationRunFileWriter();
        try {
            simulationRunFileWriter.writeSimulationRunToFile(
                    new ProbabilityDistribution(probabilityMap, "test", "Cluster size", "Probability"),
                    kalahariParameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
