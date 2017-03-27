package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.Server;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("FTPMaster5000");
        primaryStage.setScene(new Scene(root, 600, 600));

        // upload(upload);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
