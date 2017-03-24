package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by lachlan on 24/03/17.
 */
public class Connection extends Thread {
    private Socket connectionSocket;

    Connection(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        try {
            System.out.println("New connection!");
            BufferedReader br = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            System.out.println(br.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
