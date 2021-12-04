package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.util.Bet;
import pl.edu.agh.kis.pz1.util.Gameplay;
import pl.edu.agh.kis.pz1.util.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class EchoService extends Thread {
    private Socket acceptedSocket;
    private BufferedReader is;
    private DataOutputStream os;
    private PrintWriter outPrint;
    private Scanner inScanner;
    private final ArrayList<String> commandList;
    private final String isNotMyTurn = "It's not your turn! [Type 'queue' to show players order]";

    public EchoService(Socket acceptedSocket) {
        commandList = new ArrayList<>();
        commandList.add("exit");
        commandList.add("deal cards");
        commandList.add("get cards");
        commandList.add("show cards");
        commandList.add("exchange cards");
        commandList.add("players");
        commandList.add("evaluate hand");
        commandList.add("queue");
        commandList.add("balance");
        commandList.add("fold");
        commandList.add("bet");
        commandList.add("phase");

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
            // pay Ante
            ClientIdentifiers.getPlayer(this).payAnte();
            String input = "";
            while(true) {
                System.out.println("Phase" + Gameplay.getGamePhase());
                // get input from client
                input = in.nextLine();

                // print message to server received from client
                strServerLog(nickname, input);
                // check if input equals to "exit", if it is simply client exits
                if (input.equalsIgnoreCase("exit")) {
                    exitServer(nickname);
                    break;
                }

                // get gamePhase from Gameplay class
                int gamePhase = Gameplay.getGamePhase();

                // recognize commands depending on the actual game phase
                switch(gamePhase) {
                    case 1:
                        firstPhase(input, out);
                        break;
                    case 2:
                    case 4:
                        secondFourthPhase(input, out);
                        break;
                    case 3:
                        thirdPhase(input, out);
                        break;
                    case 5:
                        fifthPhase(input, out);
                        break;
                    default:
                }
            }
        }
    }


    public void firstPhase(String input, PrintWriter out){
        if (input.equalsIgnoreCase("deal cards")
                || input.equalsIgnoreCase("get cards")) {
            // call dealCards if true it is accomplished successfully and add player to the passPhase1 set
            if(dealCards(out)){
                Gameplay.passPhase1(ClientIdentifiers.getPlayer(this));
            }
        } else {
            otherCommands(input, out);
        }
    }

    public void secondFourthPhase(String input, PrintWriter out){
        int gamePhase = Gameplay.getGamePhase();
        if (input.toLowerCase().contains("bet") && input.length() > 4){
            if(isMyTurn()){
                placeBet(input, gamePhase, out);
            } else if(!isMyTurn()){
                out.println(isNotMyTurn);
            } else {
                int actualBid = 0;
                if(Gameplay.getGamePhase() == 2){
                    actualBid = ClientIdentifiers.getPlayer(this).getfirstBid();
                } else if(Gameplay.getGamePhase() == 4){
                    actualBid = ClientIdentifiers.getPlayer(this).getSecondBid();
                }
                out.println("You stay with your previous bid - " + actualBid);
            }
        } else {
            otherCommands(input, out);
        }
    }

    public void placeBet(String input, int gamePhase, PrintWriter out){
        int value = Integer.parseInt(input.replaceAll("[^0-9]+", ""));
        int maxFirstBet = Bet.getMaxFirstBet(ClientIdentifiers.getPlayersKeys());
        int maxSecondBet = Bet.getMaxSecondBet(ClientIdentifiers.getPlayersKeys());
        System.out.println(value);
        if(gamePhase == 2 && value >= maxFirstBet || gamePhase == 4 && value >= maxSecondBet){
            ClientIdentifiers.getPlayer(this).setBid(value);
            if(gamePhase == 2){
                Gameplay.passPhase2(ClientIdentifiers.getPlayer(this));
            } else {
                Gameplay.passPhase4(ClientIdentifiers.getPlayer(this));
            }
            PlayerQueue.nextPlayer();
            out.println("Bet of value " + value + " has been placed.");
            // to this place it works
        } else if(Gameplay.getGamePhase() == 2){
            out.println("Value should be higher or equal to " + maxFirstBet);
        }else if(Gameplay.getGamePhase() == 4){
            out.println("Value should be higher or equal to " + maxSecondBet);
        }else{
            out.println("It's not bet phase!");
        }
    }

    public void thirdPhase(String input, PrintWriter out){
            // exchange cards
        if (input.toLowerCase().contains("exchange cards")) {
            if (cardsExchange(input, out)) {
                Gameplay.passPhase3(ClientIdentifiers.getPlayer(this));
            }
            // stay with the current cards
        } else if(input.toLowerCase().contains("stay")){
            out.println("You didn't exchange any cards.");
            Gameplay.passPhase3(ClientIdentifiers.getPlayer(this));
            PlayerQueue.nextPlayer();
        } else {
            otherCommands(input, out);
        }
    }

    public void fifthPhase(String input, PrintWriter out){
        Gameplay.setWinner(Gameplay.pickWinner());
        if (input.toLowerCase().contains("restart")){
            // add vote for restarting game
            Gameplay.addRestartVote(ClientIdentifiers.getPlayer(this));
            // check if all players voted for game restart, if so start next game
            if(Gameplay.getRestartVotes().size() == Gameplay.getPhase1().size()){
                out.println(Gameplay.getRestartVotes().size() + "/" + Gameplay.getPhase1().size() + " voted for rematch. New game has started!");
                restartGame();
            } else {
                out.println(Gameplay.getRestartVotes().size() + "/" + Gameplay.getPhase1().size() + " voted for rematch.");
            }
        } else {
            otherCommands(input, out);
        }
    }

    public void otherCommands(String input, PrintWriter out){
        if (input.equalsIgnoreCase("show cards")) {
            ClientIdentifiers.getPlayers().get(this).printCards(out);
        } else if (input.equalsIgnoreCase("players")) {
            // print clients nicknames
            ClientIdentifiers.printPlayers(out);
        }  else if (input.equalsIgnoreCase("evaluate hand")) {
            // evaluate points (they will be used to check who won the game)
            evaluateMyHand();
            out.println(strGetHandValue());
            PlayerQueue.nextPlayer();
        } else if (input.equalsIgnoreCase("queue")) {
            out.println(strGetQueue());
        } else if (input.equalsIgnoreCase("balance")){
            out.println(strGetBalance());
        }else if (input.equalsIgnoreCase("bid status")){
            out.println("Your actual bet: " + getActualBetValue() + ". Actual highest equals " + getMaxBet());
        } else if(input.toLowerCase().contains("phase")){
            out.println(Gameplay.strGamePhase());
        } else if(input.toLowerCase().contains("winner")){
             if(Gameplay.getWinner() == null){
                 out.println("Winner hasn't been established yet.");
             } else {
                 out.println("The winner -> " + Gameplay.getWinner().getNickname() + " with " + Gameplay.getWinner().getGamePoints() + " points!");
             }
        } else {
            // if not just print return what client typed as unknown command
            out.println("Unknown command '" + input + "' OR you're in the wrong game phase to use it.");
        }
    }

    public int getMaxBet(){
        if(Gameplay.getGamePhase() == 2){
            return Bet.getMaxFirstBet(ClientIdentifiers.getPlayersKeys());
        } else if(Gameplay.getGamePhase() == 4){
            return Bet.getMaxSecondBet(ClientIdentifiers.getPlayersKeys());
        } else{
            return -1;
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
        return "For instructions type in 'man method'. Method list:" +
                "['deal cards', 'show cards' 'exchange cards', " +
                "'players', 'evaluate hand', 'queue', 'balance', 'fold', 'bid']";
    }

    public int getActualBetValue(){
        int gamePhase = Gameplay.getGamePhase();
        if(gamePhase == 2){
            return ClientIdentifiers.getPlayer(this).getfirstBid();
        } else if (gamePhase == 4){
            return ClientIdentifiers.getPlayer(this).getSecondBid();
        } else {
            return -1;
        }
    }

    public void evaluateMyHand(){
        ClientIdentifiers.getPlayers().get(this).evaluatePlayerHand();
    }

    public boolean cardsExchange(String input, PrintWriter out){
        // check if it's your turn
        if(isMyTurn()){
            // get substring after 'exchange cards '
            // i.e. 'exchange cards 1 3 4'
            // which will give idxs = '1 3 4'
            String idxs = input.substring(15);
            ClientIdentifiers.getPlayers().get(this).exchangeCards(idxs, out);
            PlayerQueue.nextPlayer();
            return true;
        } else {
            out.println(isNotMyTurn);
            return false;
        }
    }

    public void getCards(PrintWriter out){
        ClientIdentifiers.getPlayers().get(this).dealCards();
        ClientIdentifiers.getPlayers().get(this).printCards(out);
    }

    public boolean dealCards(PrintWriter out){
        // check if it's your turn
        if(isMyTurn()){
            // deal Cards for a player which is associated with this EchoService
            ClientIdentifiers.getPlayers().get(this).dealCards();
            out.println("Cards have been dealt. ['show cards' will tell you what you got]");
            PlayerQueue.nextPlayer();
            return true;
        } else {
            out.println(isNotMyTurn);
            return false;
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
        System.out.println("Number of players: " + Server.numPlayers);
    }

    public void restartGame(){
        Gameplay.clearGameplayData();
        ClientIdentifiers.clearPlayersData();
        Gameplay.getRestartVotes().clear();
    }
}