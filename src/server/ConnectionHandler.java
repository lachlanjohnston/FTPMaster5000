package server;

import com.sun.corba.se.impl.orbutil.ObjectWriter;

import javax.sound.sampled.AudioInputStream;
import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by lachlan on 24/03/17.
 */
public class ConnectionHandler extends Thread {
    private Socket connectionSocket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    ConnectionHandler(Socket connectionSocket) throws Exception {
        this.connectionSocket = connectionSocket;
        dataInputStream = new DataInputStream(connectionSocket.getInputStream());
        dataOutputStream = new DataOutputStream(connectionSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            byte[] data;
            String currentDir = "Server/";

            String[] commandIn = dataInputStream.readUTF().split(" ");
            String command = commandIn[0];

            switch (command) {
                case "DIR":
                    System.out.println("Sending directory listing!");

                    File file = new File("Server/");

                    File[] dirListing = file.listFiles();
                    ObjectOutputStream os = new ObjectOutputStream(connectionSocket.getOutputStream());

                    os.writeObject(dirListing);
                    os.flush();

                    connectionSocket.close();

                    break;
                case "UPLOAD":
                    System.out.println("Receiving File!");

                    int fileSize = dataInputStream.readInt();
                    int read = 0;
                    data = new byte[fileSize];
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            new File("Server/" + commandIn[1]));

                    while(read != fileSize) {
                        read += dataInputStream.read(data, 0, fileSize);
                        fileOutputStream.write(data);
                    }

                    fileOutputStream.close();

                    System.out.println("Done!");

                    connectionSocket.close();

                    break;
                case "DOWNLOAD":
                    System.out.println("Sending file!");

                    File download = new File(currentDir + commandIn[1]);
                    FileInputStream fileInputStream = new FileInputStream(download);

                    dataOutputStream.writeInt((int) download.length());
                    dataOutputStream.flush();
                    data = new byte[(int) download.length()];

                    fileInputStream.read(data);
                    dataOutputStream.write(data);
                    dataOutputStream.flush();

                    fileInputStream.close();

                    System.out.println("Done!");

                    connectionSocket.close();

                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload(File file) {

    }

    public void download() {

    }
}
