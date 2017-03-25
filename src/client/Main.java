package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("FTPMaster 45.1");
        primaryStage.setScene(new Scene(root, 300, 275));


        Socket clientSocket = new Socket("localhost", 1337);
        OutputStream os = clientSocket.getOutputStream();


        String s = "UPLOAD proguard5.3.2.tar.gz";

        //os.write(s.length() + " " + s);
        os.flush();

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        File upload = new File("Client/" + s.split(" ")[1]);
        FileReader fileReader = new FileReader(upload);
        long fileSize = upload.length();



        char[] data = new char[1024];
        while (fileReader.read(data) >= 0) {

            //os.write(data);
            os.flush();
            data = new char[1024];
        }

        os.close();
//        ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
//        File[] directoryListing = (File[]) reader.readObject();
//
//        for (File file : directoryListing)
//            System.out.println(file.getName());

        clientSocket.close();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
