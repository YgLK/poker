package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;


 /**
 * Client class used for Poker Game. Client connects
 * with the Server and lets Player join the
 * multi-player socket-based Poker game.
 *
 * @author  Jakub Szpunar
 * @version 1.0
 * @since   2021-12-06
 */
public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    String nickname;

     /**
      * Main method creates new Client
      * which joins the running server
      *
      * @param args
      */
    public static void main( String[] args ) {
        Client client1 = new Client();
        try {
            client1.joinServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /**
      * Client constructor. User enter his nickname
      * which will be used for the identification
      * during the game.
      *
      */
    Client(){
        // set nickname
        LOGGER.info(getInstructions());
        LOGGER.info("\nEnter your nickname: ");
        Scanner sc = new Scanner(System.in);
        nickname = sc.nextLine();
    }


     /**
      * The method is used for connecting to the Server
      * and further communication with data exchange.
      * In this method Scanners, PrintWriter and Socket are initialized.
      *
      * Server hostname and PORT are defined in the method.
      * HOST = "127.0.0.1"
      * PORT = 4040
      *
      * @throws IOException If connection or initialization
      *                     exception occurred.
      */
    public void joinServer() throws IOException {
        // set hostname and port to which client will be joining
        final String HOST = "127.0.0.1";
        final int PORT = 4040;
        // declare outClient for readability
        // PrintStream outClient = System.out;

        try (
                // initialize needed data
                Socket socket = new Socket(HOST, PORT);
                // initialize PrintWriter used for sending message to the server
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                // Server scanner from which data sent to the Client is read
                Scanner in = new Scanner(socket.getInputStream());
                // Client scanner with which Client can enter used commands
                Scanner s = new Scanner(System.in)
        ) {
            out.println(nickname); // pass nickname to the server
            //  outClient.println("You've paid Ante. Type 'get cards' to take cards from the table.");
            LOGGER.info("You've paid Ante. Type 'get cards' to take cards from the table.");
            String input = "";
            while (true) {
                // get input from Client
                //  outClient.print(nickname + " >>  ");
                LOGGER.info(nickname + " >>  ");
                // Client enters command
                input = s.nextLine();
                // pass input to the server
                out.println(input);
                // exit server if client sent "exit" message
                if (input.equalsIgnoreCase("exit")){
                    break;
                }

                // read message from the server
                if(in.hasNextLine()) {
                    // outClient.println(in.nextLine());
                    LOGGER.info(in.nextLine());
                }
            }
        }
    }

     /**
      * The method returns String  which contains
      * GAMEPLAN including all game phases description
      * USEFUL COMMANDS including commands with their usage and explanation.
      *
      * @return String
      */
   public static String getInstructions(){
       return "\nFive-card draw (Poker) INSTRUCTIONS\n" +
               "    GAMEPLAN:\n" +
               "1. Every player pays Ante and take cards from the table.\n" +
               "2. First round of betting.\n" +
               "3. Players exchange cards.\n" +
               "4. Second round of betting.\n" +
               "5. Determine a winner. \n" +
               "    USEFUL COMMANDS:\n" +
               "'get cards' - take card from the table\n" +
               "'exchange cards _ _ _' - exchange cards from your hand (cards are numbered 0 to 4) e.g. 'exchange cards 1 3'\n" +
               "'stay' - inform that you don't want exchange any cards in the third phase\n" +
               "'queue' - show players queue\n" +
               "'balance' - show balance\n" +
               "'bet _value_' - bid the amount of _value_\n" +
               "'bid status' - info about betting phase\n" +
               "'winner' - show winner\n" +
               "'won cards' - show winner's cards\n" +
               "'phase' - get current phase of the game \n" +
               "    Good luck!   \n";
    }
}
