package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import output.ProbabilityDistributionFileWriter;
import simulation.Kalahari;
import simulation.clustering.Cluster;
import simulation.clustering.ClusterStatistics;
import simulation.clustering.KalahariClusteringMetric;

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
        Kalahari kalahari = new Kalahari(100, 100, 0.25);
        //kalahari.run();

        KalahariClusteringMetric kalahariClusteringMetric = new KalahariClusteringMetric(kalahari.getGrid());

        List<Cluster> clusters = kalahariClusteringMetric.getClusters();

        ClusterStatistics clusterStatistics = new ClusterStatistics(clusters);

        Map<Integer, Double> probabilityMap = clusterStatistics.getCumulativeProbabilityDistribution();

        ProbabilityDistributionFileWriter probabilityDistributionFileWriter = new ProbabilityDistributionFileWriter();
        try {
            probabilityDistributionFileWriter.writeProbabilityDistributionMapToFile(probabilityMap, "test",
                    "Cluster size", "Probability");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
