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
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            // send initialisation information about server
            System.out.println("Poker game server is up and running.");
            System.out.println("Wating for players...");

            while (true) {
                PrintWriter tmp;
                // accept clientSocket to the server
                Socket clientSocket = serverSocket.accept();
                // increment count of players
                incrementNumPlayers();
                // create new EchoService attached to a player
                EchoService t = new EchoService(clientSocket);
                // start EchoService
                t.start();
            }
        } catch (IOException o){
            o.printStackTrace();
        }
    }

    public static void incrementNumPlayers(){
        numPlayers += 1;
    }

    public static void decrementNumPlayers(){
        numPlayers -= 1;
    }

}
