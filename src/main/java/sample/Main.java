package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simulation.Kalahari;
import simulation.KalahariParameters;

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

        KalahariParameters kalahariParameters = new KalahariParameters(500, 500, 0.3, 0.2, 200, 0.3, 10);

        Kalahari kalahari = new Kalahari(kalahariParameters);
        kalahari.run();
    }
}
