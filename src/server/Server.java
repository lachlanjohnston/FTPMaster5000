package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by lachlan on 24/03/17.
 */
public class Server {


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1337);
        ArrayList<Connection> connections = new ArrayList<>();
        while (true) {
            connections.add(new Connection(serverSocket.accept()));
            connections.get(connections.size() - 1).start();
        }
    }
}
