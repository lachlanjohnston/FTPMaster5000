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
        ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<>();

        while (true) {
            connectionHandlers.add(new ConnectionHandler(serverSocket.accept()));
            connectionHandlers.get(connectionHandlers.size() - 1).start();
        }
    }
}
