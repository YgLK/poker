package pl.edu.agh.kis.pz1;

import org.apache.commons.lang3.Validate;
import pl.edu.agh.kis.pz1.util.Bet;
import pl.edu.agh.kis.pz1.util.Card;
import pl.edu.agh.kis.pz1.util.Gameplay;
import pl.edu.agh.kis.pz1.util.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Main class used for communication between client and server.
 * Through this class data is exchanged.
 */
public class EchoService extends Thread {
    private static final Logger LOGGER = Logger.getLogger(EchoService.class.getName() );
    private Socket acceptedSocket;
    private BufferedReader is;
    private DataOutputStream os;
    private PrintWriter outPrint;
    private Scanner inScanner;
    private ArrayList<String> commandList;
    private final String isNotMyTurn = "It's not your turn! [Type 'queue' to show players order]";


    /**
     * EchoService constructor which pair Client with the EchoService allowing
     * to easier communication with the use of created commands.
     *
     * @param acceptedSocket Client socket
     */
    public EchoService(Socket acceptedSocket) {
        Validate.notNull(acceptedSocket, "Accepted Socket is NULL.");
        initInstructions();

        try {
            // initialize needed data
            this.acceptedSocket = acceptedSocket;

            is = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()));
            os = new DataOutputStream(acceptedSocket.getOutputStream());
            // get Client printer
            outPrint = new PrintWriter(acceptedSocket.getOutputStream(), true);
            // get Client scanner
            inScanner = new Scanner(acceptedSocket.getInputStream());
        } catch (IOException e) {
            try {
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

    /**
     * Overridden Thread run method which allows Server and Client
     * to communicate with the use of created commands.
     */
    @Override
    public void run() {
        try (
                // get
                PrintWriter out = this.getOutPrintWriter();
                Scanner in = this.getInScanner()
        ){
            // get nickname from the client
            String nickname = in.nextLine();
            LOGGER.log(Level.INFO,"{0} - join server",nickname);

            // add Client to the hashmap
            ClientIdentifiers.addClient(this, new Player(nickname));
            // add player to queue
            PlayerQueue.queue.add(this);

            // print number of players to server
            LOGGER.log(Level.INFO,"Number of players: {0}", Server.numPlayers);
            // pay Ante
            ClientIdentifiers.getPlayer(this).payAnte();
            String input;
            while(true) {
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

    /**
     * Method used for handling first phase of the Game
     * in which every player gets the cards by
     * 'deal cards' or 'get cards' method.
     *
     * If other command is sent otherCommands() method is called.
     *
     * @param input String containing Client's sent command
     * @param out PrintWriter which will print the result
     */
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

    /**
     * Method used for handling second and fourth phases of the Game
     * in which every player place bets by calling 'bet XX' command,
     * where XX is bet value.
     *
     * If other command is sent otherCommands() method is called.
     *
     * @param input String containing Client's sent command
     * @param out PrintWriter which will print the result
     */
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
                    actualBid = ClientIdentifiers.getPlayer(this).getFirstBid();
                } else if(Gameplay.getGamePhase() == 4){
                    actualBid = ClientIdentifiers.getPlayer(this).getSecondBid();
                }
                out.println("You stay with your previous bid - " + actualBid);
            }
        } else {
            otherCommands(input, out);
        }
    }

    /**
     * Method used placing Bets during the second and fourth game phase.
     * In here bet values are extracted from the command String and
     * set to the suitable player.
     *
     * @param input String containing Client's sent command
     * @param gamePhase current game phase
     * @param out PrintWriter which will print the result
     */
    public void placeBet(String input, int gamePhase, PrintWriter out){
        int value = Integer.parseInt(input.replaceAll("[^0-9]+", ""));
        int maxFirstBet = Bet.getMaxFirstBet(ClientIdentifiers.getPlayersKeys());
        int maxSecondBet = Bet.getMaxSecondBet(ClientIdentifiers.getPlayersKeys());
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

    /**
     * Method used for handling third phase of the game in which
     * players exchange their cards.
     *
     * @param input String containing Client's sent command
     * @param out PrintWriter which will print the result
     */
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

    /**
     * Method used for handling fifth phase of the game in which
     * the winner is determined.
     *
     * Players are also able to call 'restart' command. If all of them
     * call it the new game will start.
     *
     * @param input String containing Client's sent command
     * @param out PrintWriter which will print the result
     */
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

    /**
     * Method used for handling general commands which
     * are not strongly associated with the specific
     * game phase.
     *
     * Sample commands:
     * - 'show cards' - show owned cards
     * - 'players' - show players list
     * - 'queue' - show queue order
     * - 'bid status' - show current bid the highest value
     * - 'phase' - show phase with short description
     * - 'winner' - show the winner
     *  etc.
     *
     * @param input String containing Client's sent command
     * @param out PrintWriter which will print the result
     */
    public void otherCommands(String input, PrintWriter out){
        if (input.equalsIgnoreCase("show cards")) {
            out.println(ClientIdentifiers.getPlayers().get(this).yourCardsToString());
        } else if (input.equalsIgnoreCase("players")) {
            // print clients nicknames
            out.println(ClientIdentifiers.playersListToStr());
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
                 out.println("The winner is... " + Gameplay.getWinner().getNickname() + " with " + Gameplay.getWinner().getStringHandCombination() + "!");
             }
        } else if(input.toLowerCase().contains("won cards")){
            printWonCards(out);
        } else {
            // if not just print return what client typed as unknown command
            out.println("Unknown command '" + input + "' OR you're in the wrong game phase to use it.");
        }
    }


    /**
     * Method for printing Winner Cards
     *
     * @param out PrintWriter used to print information
     */
    public static void printWonCards(PrintWriter out){
        if(Gameplay.getWinner() == null){
            out.println("Winner hasn't been established yet.");
        } else {
            StringBuilder winCards = new StringBuilder("The winner's cards: ");
            for (Card c : Gameplay.getWinner().getCards()) {
                winCards.append(" [").append(c.getCardRank()).append(" ").append(c.getCardSuit()).append("] ");
            }
            out.println(winCards);
        }
    }

    /**
     * Method used for finding maximum value of
     * the current game phase bet.
     *
     * Used in the second or fourth game phase (bet phases).
     *
     * @return max value of bet
     */
    public int getMaxBet(){
        if(Gameplay.getGamePhase() == 2){
            return Bet.getMaxFirstBet(ClientIdentifiers.getPlayersKeys());
        } else if(Gameplay.getGamePhase() == 4){
            return Bet.getMaxSecondBet(ClientIdentifiers.getPlayersKeys());
        } else{
            return -1;
        }
    }

    /**
     * Method returning text representation of the queue.
     *
     * @return String representation of the queue
     */
    public String strGetQueue(){
        // communicate that it's your turn
        if(isMyTurn()){
            return "Your turn. " + PlayerQueue.strQueue();
        } else {
            // return message with queue
            return PlayerQueue.strQueue();
        }
    }

    /**
     * Method returning text representation of the Hand evaluation points.
     *
     * @return String with player's game points
     */
    public String strGetHandValue(){
        return ClientIdentifiers.getPlayers().get(this).getNickname() + " points: "
                + ClientIdentifiers.getPlayers().get(this).getGamePoints();
    }

    /**
     * Method used to find the actual Client bet value.
     * Used in the second or fourth game phase (bet phases).
     *
     * @return actual Client bet value
     */
    public int getActualBetValue(){
        int gamePhase = Gameplay.getGamePhase();
        if(gamePhase == 2){
            return ClientIdentifiers.getPlayer(this).getFirstBid();
        } else if (gamePhase == 4){
            return ClientIdentifiers.getPlayer(this).getSecondBid();
        } else {
            return -1;
        }
    }

    public void evaluateMyHand(){
        ClientIdentifiers.getPlayers().get(this).evaluatePlayerHand();
    }

    /**
     * Method used to exchange Player's (corresponding to Client) cards.
     * Used in the third game phase (exchange phase).
     *
     * @param input String containing Client's sent command
     * @param out PrintWriter which will print the result
     *
     * @return true if success
     *         else false
     */
    public boolean cardsExchange(String input, PrintWriter out){
        // check if it's your turn
        if(isMyTurn()){
            // get substring after 'exchange cards '
            // i.e. 'exchange cards 1 3 4'
            // which will give idxs = '1 3 4'
            String idxs = input.substring(15);
            out.println(ClientIdentifiers.getPlayers().get(this).exchangeCards(idxs));
            PlayerQueue.nextPlayer();
            return true;
        } else {
            out.println(isNotMyTurn);
            return false;
        }
    }

    /**
     * Method used in the first GamePhase for dealing cards at the beginning of the game.
     *
     * @param out PrintWriter which will print the result
     * @return true if success
     *         else false
     */
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

    /**
     * Method used for logging players' command calls.
     *
     * @param nickname Nick of the calling Player
     * @param input String with the command
     * @return Log with the information about called command, player nickname and phase in which
     *         command was called.
     */
    public String strServerLog(String nickname, String input) {
        // return player method call in String format
        if(commandList.contains(input)){
            return  "[Phase " + Gameplay.getGamePhase() + "] " + nickname + " call: '" + input + "'";
        } else {
            return "[Phase " + Gameplay.getGamePhase() + "] " + nickname + " call: unknown command '" + input + "'";
        }
    }

    /**
     * Method lets Client exit the Server.
     *
     * @param nickname Nick of the exiting Player.
     */
    public void exitServer(String nickname){
        // remove client from the players group
        Server.decrementNumPlayers();
        LOGGER.log(Level.INFO, "{0} - quit server", nickname);
        ClientIdentifiers.removeClient(this);
        LOGGER.log(Level.INFO, "Number of players: {0}", Server.numPlayers);
    }

    /**
     * Method which restarts the game.
     * Is called when all players votes for the restart in the fifth game phase.
     */
    public void restartGame(){
        Gameplay.clearGameplayData();
        ClientIdentifiers.clearPlayersData();
        Gameplay.getRestartVotes().clear();
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

    public void initInstructions(){
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
    }

}