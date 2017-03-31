package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Lachlan Johnston
 * 100590934
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1337);
        ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<>();

        while (true) {
            try {
                connectionHandlers.add(new ConnectionHandler(serverSocket.accept()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            connectionHandlers.get(connectionHandlers.size() - 1).start();
        }
    }
}
