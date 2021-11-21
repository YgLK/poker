package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.util.Player;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoService extends Thread {
    private Socket acceptedSocket;
//    private DataInputStream is;
    private BufferedReader is;
    private DataOutputStream os;
    private PrintWriter outPrint;
    private Scanner inScanner;

    public EchoService(Socket acceptedSocket) {
        try {
            // initialize needed data
            this.acceptedSocket = acceptedSocket;
            is = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()));
            os = new DataOutputStream(acceptedSocket.getOutputStream());
            outPrint = new PrintWriter(acceptedSocket.getOutputStream(), true);
            inScanner = new Scanner(acceptedSocket.getInputStream());
        } catch (IOException e) {
            try {
                if (this.acceptedSocket != null)
                    acceptedSocket.close();
                if(is != null)
                    is.close();
                if(os != null)
                    os.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try (
                PrintWriter out = this.getOutPrintWriter();
                Scanner in = this.getInScanner();
        ){
            // get nickname from the client
            String nickname = in.nextLine();
            System.out.println(nickname + " has joined to the server.");
            // add Client to the hashmap
            ClientIdentifiers.addClient(this, new Player(nickname));
            while(true) {
                // get input from client
                String input = in.nextLine();

                // check if input equals to "exit", if it is simply client exits
                if (input.equalsIgnoreCase("exit")) {
                    // remove client from the players group
                    Server.numPlayers--;
                    ClientIdentifiers.removeClient(this);
                    break;
                }
                // print number of players to server
                System.out.println("Number of players: " + Server.numPlayers);
                // print message to server received from client
                System.out.println("Received message from client: " + input);

                // check if user sent commands to deal cards or show cards
                if (input.equalsIgnoreCase("deal cards")) {
                    // deal Cards for a player which is associated with this EchoService
                    ClientIdentifiers.getPlayers().get(this).dealCards();
                    out.println("Cards have been dealt.");
                } else if (input.equalsIgnoreCase("show cards")) {
                    ClientIdentifiers.getPlayers().get(this).printCards(out);
                } else if (input.toLowerCase().contains("exchange cards")) {
//                    out.println("Choose Cards to exchange (indexes separated by space): ");

                    // get substring after 'exchange cards '
                    // i.e. 'exchange cards 1 3 4'
                    // which will give idxs = '1 3 4'
                    String idxs = input.substring(15);

                    // TODO: fix reading string from input error (it just loops infinitely)
                    // String idxs = in.nextLine();

                    // UPDATE: I changed method of passing idxes to exchange, but it would be good if it works

//                    String idxs = "0 3 1";  // test - works fine with string
                    ClientIdentifiers.getPlayers().get(this).exchangeCards(idxs, out);
                } else if (input.equalsIgnoreCase("players")) {
                    // print clients nicknames
                    ClientIdentifiers.printPlayers(out);
                }else if (input.equalsIgnoreCase("instructions")) {
                    // print commands and instructions for poker game
                    // TODO: make it print as whole not separated by lines (i need to press ENTER after the
                    //  first printed line and then second one appears which is inconvenient
                    //  and it breaks whole printing system - after command i need to click ENTER
                    //  couple times until i get wanted result)

                    // z \n nie działa ;/ - writeLine() sie blokuje
                    out.println("INSTRUKCJA" + "\n" + " \'show cards\' - prints all cards on your hand ");
                }  else if (input.equalsIgnoreCase("evaluate hand")) {
                    // evaluate points (they will be used to check who won the game)
                    ClientIdentifiers.getPlayers().get(this).evaluatePlayerHand();
                    out.println(ClientIdentifiers.getPlayers().get(this).getNickname() + " points: "
                            + ClientIdentifiers.getPlayers().get(this).getGamePoints());
                }
                // if not just print what client said
                else {
                    out.println("You said:" + input);
                }
            }
        }
    }

    public BufferedReader getIs() {
        return is;
    }

    public DataOutputStream getOs() {
        return os;
    }

    public Socket getAcceptedSocket() {
        return acceptedSocket;
    }

    public PrintWriter getOutPrintWriter() {
        return outPrint;
    }

    public Scanner getInScanner() {
        return inScanner;
    }
}