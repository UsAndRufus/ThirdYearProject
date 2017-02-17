package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simulation.Kalahari;

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
        Kalahari kalahari = new Kalahari(500, 500, 0.25);
        kalahari.run();
    }
}
