package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.util.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class EchoService extends Thread {
    private Socket acceptedSocket;
//    private DataInputStream is;
    private BufferedReader is;
    private DataOutputStream os;
    private PrintWriter outPrint;
    private Scanner inScanner;
    private final ArrayList<String> commandList;

    public EchoService(Socket acceptedSocket) {
        commandList = new ArrayList<>();
        commandList.add("exit");
        commandList.add("deal cards");
        commandList.add("show cards");
        commandList.add("exchange cards");
        commandList.add("players");
        commandList.add("evaluate hand");
        commandList.add("queue");
        commandList.add("balance");
        commandList.add("fold");

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
                Scanner in = this.getInScanner()
        ){
            // get nickname from the client
            String nickname = in.nextLine();
            System.out.println(nickname + " - join server");

            // add Client to the hashmap
            ClientIdentifiers.addClient(this, new Player(nickname));
            // add player to queue
            PlayerQueue.queue.add(this);

            // print number of players to server
            System.out.println("Number of players: " + Server.numPlayers);
            while(true) {
                // get input from client
                String input = in.nextLine();

                // print message to server received from client
                System.out.println(strServerLog(nickname, input));

                // check if input equals to "exit", if it is simply client exits
                if (input.equalsIgnoreCase("exit")) {
                    exitServer(nickname);
                    break;
                }

                // check if user sent commands to deal cards or show cards
                if (input.equalsIgnoreCase("deal cards")) {
                    dealCards(out);
                } else if (input.equalsIgnoreCase("show cards")) {
                    ClientIdentifiers.getPlayers().get(this).printCards(out);
                } else if (input.toLowerCase().contains("exchange cards")) {
                    cardsExchange(input, out);
                } else if (input.equalsIgnoreCase("players")) {
                    // print clients nicknames
                    ClientIdentifiers.printPlayers(out);
                }else if (input.equalsIgnoreCase("instructions")) {
                    // print commands and instructions for poker game
                    // jesli zadziala printowanie wielu linii to po prostu linia za linią bedzie printowane co co oznacza i jak sie gra
                    out.println(strGetInstructions());
                }  else if (input.equalsIgnoreCase("evaluate hand")) {
                        // evaluate points (they will be used to check who won the game)
                        evaluateMyHand();
                        out.println(strGetHandValue());
                        PlayerQueue.nextPlayer();
                } else if (input.equalsIgnoreCase("queue")) {
                    out.println(strGetQueue());
                } else if (input.equalsIgnoreCase("balance")){
                    out.println(strGetBalance());
                } else if (input.equalsIgnoreCase("fold")) {
                    // TODO: implement
                } else if (input.toLowerCase().contains("bid")){
                    // TODO: implement
                } else {
                    // if not just print return what client typed as unknown command
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

    public String strGetBalance(){
        return "Your balance: " + ClientIdentifiers.getPlayers().get(this).getMoney()+ "";
    }

    public String strGetQueue(){
        // communicate that it's your turn
        if(isMyTurn()){
            return "Your turn.";
        } else {
            // evaluate points (they will be used to check who won the game)
            return PlayerQueue.strQueue();
        }
    }

    public String strGetHandValue(){
        return ClientIdentifiers.getPlayers().get(this).getNickname() + " points: "
                + ClientIdentifiers.getPlayers().get(this).getGamePoints();
    }

    public String strGetInstructions(){
        return "For instructions type in 'man method'. Method list: ['deal cards', 'show cards' 'exchange cards', " +
                "'players', 'evaluate hand', 'queue', 'balance', 'fold', 'bid']";
    }

    public void evaluateMyHand(){
        ClientIdentifiers.getPlayers().get(this).evaluatePlayerHand();
    }

    public void cardsExchange(String input, PrintWriter out){
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
    }

    public void dealCards(PrintWriter out){
        // check if it's your turn
        if(isMyTurn()){
            // deal Cards for a player which is associated with this EchoService
            ClientIdentifiers.getPlayers().get(this).dealCards();
            out.println("Cards have been dealt.");
            PlayerQueue.nextPlayer();
        } else {
            out.println("It's not your turn! [Type 'queue' to show players order]");
        }
    }

    public String strServerLog(String nickname, String input) {
        // return player method call in String format
        if(commandList.contains(input)){
            return nickname + " - call: '" + input + "'";
        } else {
            return nickname + "- call: unknown command '" + input + "'";
        }
    }

    public void exitServer(String nickname){
        // remove client from the players group
        Server.decrementNumPlayers();
        System.out.println(nickname + " - quit server");
        ClientIdentifiers.removeClient(this);
    }

}