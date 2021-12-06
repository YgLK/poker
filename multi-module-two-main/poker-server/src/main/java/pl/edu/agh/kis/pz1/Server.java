package pl.edu.agh.kis.pz1;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server class used for Poker Game. Server gathers
 * clients, provides communication with Players and let them play the
 * socket-based Poker game.
 *
 * @author  Jakub Szpunar
 * @version 1.0
 * @since   2021-12-06
 */
public class Server {
    /** Announces new logs */
    private static final Logger LOGGER = Logger.getLogger(EchoService.class.getName() );
    /** Number of players connected to the server */
    protected static int numPlayers = 0;

    /**
     * Main method starts the Server which waits for the clients.
     */
    public static void main( String[] args ) {
        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Server method used for starting the server.
     * Port on which server will work is declared and
     * server's socket is opened for mew Clients trying to join the server.
     */
    public static void startServer() throws IOException {
        // set PORT on which the server will work
        final int PORT = 4040;
        // open the server socket on the PORT
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            int serverRun = 1;
            // send initialisation information about server
            LOGGER.log(Level.INFO, "[Poker game server is up and running]");
            LOGGER.log(Level.INFO, "Wating for players...");

            while (serverRun == 1) {
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

    public static void setNumPlayers(int numPlayers) {
        Server.numPlayers = numPlayers;
    }

    public static int getNumPlayers() {
        return numPlayers;
    }
}
