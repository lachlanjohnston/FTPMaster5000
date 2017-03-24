package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("FTPMaster 45.1");
        primaryStage.setScene(new Scene(root, 300, 275));


        Socket clientSocket = new Socket("localhost", 1337);
        PrintWriter os = new PrintWriter(clientSocket.getOutputStream());
        String s = "omg man\n";
        os.write(s);
        os.close();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}