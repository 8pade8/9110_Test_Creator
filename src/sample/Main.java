package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    static ResourseSaver resourseSaver;
    static Test9110 test9110;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("9110 Test Creator");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        PrintStream printStream = new PrintStream("Resources\\logs.txt");
        System.setOut(printStream);
        resourseSaver = new ResourseSaver();
        resourseSaver.loadAll();
        launch(args);

    }
}
