package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    // number of players connected to the server
    protected static int numPlayers = 0;

    // start the server
    public static void startServer() throws IOException {
        // set PORT on which the server will work
        final int PORT = 4040;
        // open the server socket on the PORT
        ServerSocket serverSocket = new ServerSocket(PORT);

        // send initialisation information about server
        System.out.println("Server started...");
        System.out.println("Wating for clients...");

        while (true) {
            PrintWriter tmp;
            // accept clientSocket to the server
            Socket clientSocket = serverSocket.accept();
            // increment count of players
            numPlayers++;
            // create new EchoService attached to a player
            EchoService t = new EchoService(clientSocket);
            // start EchoService
            t.start();
        }
    }
}
