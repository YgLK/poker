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
            // add player to queue
            PlayerQueue.queue.add(this);
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
                    // check if it's your turn
                    if(isMyTurn()){
                        // deal Cards for a player which is associated with this EchoService
                        ClientIdentifiers.getPlayers().get(this).dealCards();
                        out.println("Cards have been dealt.");
                        PlayerQueue.nextPlayer();
                    } else {
                        out.println("It's not your turn! [Type 'queue' to show players order]");
                    }
                } else if (input.equalsIgnoreCase("show cards")) {
                    ClientIdentifiers.getPlayers().get(this).printCards(out);
                } else if (input.toLowerCase().contains("exchange cards")) {
                    // check if it's your turn
                    if(isMyTurn()){
                        // get substring after 'exchange cards '
                        // i.e. 'exchange cards 1 3 4'
                        // which will give idxs = '1 3 4'
                        String idxs = input.substring(15);
                        //  -- just lets leave it as it is --
                        // it will be exchange cards _ _ _ , where _ is card_idx
                        ClientIdentifiers.getPlayers().get(this).exchangeCards(idxs, out);
                        PlayerQueue.nextPlayer();
                    } else {
                        out.println("It's not your turn! [Type 'queue' to show players order]");
                    }
                } else if (input.equalsIgnoreCase("players")) {
                    // print clients nicknames
                    ClientIdentifiers.printPlayers(out);
                }else if (input.equalsIgnoreCase("instructions")) {
                    // print commands and instructions for poker game
                    out.println("INSTRUKCJA\n" +
                            " \'show cards\' - prints all cards on your hand");
                    out.println("-1");
                }  else if (input.equalsIgnoreCase("evaluate hand")) {
                        // evaluate points (they will be used to check who won the game)
                        ClientIdentifiers.getPlayers().get(this).evaluatePlayerHand();
                        out.println(ClientIdentifiers.getPlayers().get(this).getNickname() + " points: "
                                + ClientIdentifiers.getPlayers().get(this).getGamePoints());
                        PlayerQueue.nextPlayer();
                } else if (input.equalsIgnoreCase("queue")) {
                    // communicate that it's your turn
                    if(isMyTurn()){
                        out.println("Your turn.");
                    } else {
                        // evaluate points (they will be used to check who won the game)
                        out.println(PlayerQueue.strQueue());
                    }
                }
//                else if (input.equalsIgnoreCase("pass")) {
//                    // NOT FINISHED YET (client after pass doesnt respond)
//
//                    if(isMyTurn()){
//                        PlayerQueue.passPlayer();
//                    } else {
//                        out.println("It's not your turn! [Type 'queue' to show players order]");
//                    }
//                }
                // if not just print return what client typed as unknown command
                else {
                    out.println("Unknown command: " + input);
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

    public boolean isMyTurn(){
        return PlayerQueue.queue.peek() == this;
    }
}