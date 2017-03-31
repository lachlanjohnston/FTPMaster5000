package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.Socket;

/**
 * Lachlan Johnston
 * 100590934
 */

public class Controller {

    @FXML ListView<File> clientList;
    @FXML ListView<File> serverList;
    ObservableList<File> clientFiles = FXCollections.observableArrayList();
    ObservableList<File> serverFiles = FXCollections.observableArrayList();
    String workingDirectory = "Client/";
    File current = new File(workingDirectory);

    public void initialize() throws Exception {
        clientFiles.addAll(current.listFiles());
        serverFiles.addAll(getDirectoryListing());
        clientList.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
            @Override
            public ListCell<File> call(ListView<File> param) {
                return new ListCell<File>() {
                    @Override
                    protected void updateItem(File file, boolean b) {
                        super.updateItem(file, b);
                        if (file != null) setText(file.getName());
                        else setText("");
                    }
                };
            }
        });
        serverList.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
            @Override
            public ListCell<File> call(ListView<File> param) {
                return new ListCell<File>() {
                    @Override
                    protected void updateItem(File file, boolean b) {
                        super.updateItem(file, b);
                        if (file != null) setText(file.getName());
                        else setText("");
                    }
                };
            }
        });
        clientList.setItems(clientFiles);
        serverList.setItems(serverFiles);
    }

    public File[] getDirectoryListing() throws Exception {
        Socket clientSocket = new Socket("localhost", 1337);
        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

        dataOutputStream.writeUTF("DIR");
        dataOutputStream.flush();

        ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
        File[] directoryListing = (File[]) reader.readObject();

        return directoryListing;
    }

    public void upload(ActionEvent e) throws Exception {
        Socket clientSocket = new Socket("localhost", 1337);

        File selected = clientList.getFocusModel().getFocusedItem();
        if (selected == null) return;

        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        FileInputStream fileInputStream = new FileInputStream(selected);

        dataOutputStream.writeUTF("UPLOAD " + selected.getName());
        dataOutputStream.writeInt((int) selected.length());
        byte[] data = new byte[(int) selected.length()];

        fileInputStream.read(data);
        dataOutputStream.write(data);
        dataOutputStream.flush();
        fileInputStream.close();

        clientSocket.close();

        refresh();
    }

    public void refresh() throws Exception {
        clientFiles.clear();
        clientFiles.addAll(current.listFiles());
        serverFiles.clear();
        serverFiles.addAll(getDirectoryListing());
    }

    public void download(ActionEvent e) throws Exception {
        Socket clientSocket = new Socket("localhost", 1337);

        File selected = serverList.getFocusModel().getFocusedItem();
        if (selected == null) return;

        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
        FileOutputStream fileOutputStream = new FileOutputStream(
                new File(current.getCanonicalPath(), selected.getName()).toString());

        dataOutputStream.writeUTF("DOWNLOAD " + selected.getName());

        int fileSize = dataInputStream.readInt();
        System.out.println("here");

        byte[] data = new byte[fileSize];
        int read = 0;
        while (read != fileSize) {
            read += dataInputStream.read(data);
            fileOutputStream.write(data);
        }

        fileOutputStream.close();
        clientSocket.close();

        refresh();
    }

    public void changeDirectory(ActionEvent e) throws Exception {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        current = directoryChooser.showDialog(stage);
        refresh();
    }
}
