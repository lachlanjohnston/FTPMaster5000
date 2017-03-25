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

    ConnectionHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        try {
            System.out.println("New connection!");
            InputStream is = connectionSocket.getInputStream();
            File file = new File("./Server");
            byte[] data = new byte[1024];

            is.read(data);
            String clientRequest = new String(data);
            int length = Integer.parseInt(clientRequest.split(" ")[0]);
            clientRequest = clientRequest.substring(0, length);

            String[] commandData = clientRequest.split(" ");
            String command = commandData[1];
            String filePath = commandData[2];

            switch (command) {
                case "DIR":
                    System.out.println("Sending directory listing!");

                    File[] dirListing = file.listFiles();
                    ObjectOutputStream os = new ObjectOutputStream(connectionSocket.getOutputStream());

                    os.writeObject(dirListing);

                    break;
                case "UPLOAD":
                    System.out.println("Receiving File!");

                    File upload = new File("Server/" + filePath);
                    //System.out.println(upload.getName());
                    OutputStream fileWriter = new FileOutputStream(upload);


                    while (is.read(data) >= 0) {
                        //System.out.print(data);
                        fileWriter.write(data);
                    }

                    fileWriter.flush();

                    System.out.println("Done!");

                    break;
                case "DOWNLOAD":
                    System.out.println("Sending file!");

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
