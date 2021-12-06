package pl.edu.agh.kis.pz1.util;

import java.util.*;

/**
 *
 * Class providing smooth Game Phases transition
 * when certain conditions are satisfied.
 *
 * List of gamePhases:
 * 1 - pay Ante & get cards
 * 2 - first Bid
 * 3 - exchange cards
 * 4 - second Bid
 * 5 - evaluate Hands and under 'winner' command there will be  winner / restart game by voting with 'restart' command
*/
public class Gameplay {
    /** current phase of the game */
    private static int gamePhase = 1;
    /** set containing Players who've taken cards from the table */
    private static Set<Player> phase1 = new LinkedHashSet<>();
    /** map containing Players and their Bet values in the second GamePhase */
    private static HashMap<Player, Integer> phase2 = new HashMap<>();
    /** set containing Players who've exchanged their cards in the third GamePhase */
    private static HashSet<Player> phase3 = new HashSet<>();
    /** map containing Players and their Bet values in the fourth GamePhase */
    private static HashMap<Player, Integer> phase4 = new HashMap<>();
    /** winner of the game */
    private static Player winner;
    /** map containing votes from players who declared to restart the game */
    private static HashMap<Player, Boolean> restartVotes = new HashMap<>();

    /**
     * Private Constructor to avoid Class instantiation.
     */
    private Gameplay(){}

    /**
     * Method used for grouping players who have taken their cards from the table.
     * When all players take their cards GamePhase is incremented.
     * Then second GamePhase comes - it is first Bet phase.
     *
     * @param p Add player to the phase1 set when Player
     *          takes cards from the table ('get cards' command)
     */
    public static void passPhase1(Player p){
        // add player to the set
        phase1.add(p);
        // if all players have taken cards from the table move to the next GamePhase
        if(phase1.size() == Player.getPlayerCount()){
            incrementGamePhase();
        }
    }


    /**
     * The method provides handling first Bet phase in the game (Second GamePhase).
     * Second phase is passed when all bets are equal, not 0 and queue has accomplished
     * full cycle (first player at the beginning of the cycle is the first player).
     * In simple words when all Players meet in the same bet value.
     *
     * To check if all players' Bet values are equal is used HashMap in which
     * Players and their Bet values are stored.
     *
     *  @param p Add player to the phase2 HashMap when Player
     *          places a Bet
     */
    public static void passPhase2(Player p){
        // put player and value of his bet to the HashSet
        phase2.put(p, p.getFirstBid());
        checkBetStatus(p, phase2);
    }


    /**
     * Method used for grouping players who have exchanged their cards.
     * When all players exchange their cards GamePhase is incremented.
     * Then fourth GamePhase comes - it is second Bet phase.
     *
     * @param p Add player to the phase3 set when Player
     *          takes cards from the table ('get cards' command)
     */
    public static void passPhase3(Player p){
        phase3.add(p);
        if(phase3.size() == Player.getPlayerCount()){
            incrementGamePhase();
        }
    }

    /**
     * The method provides handling second Bet phase in the game (Fourth GamePhase).
     * Fourth phase is passed when all bets are equal, not 0 and queue has accomplished
     * full cycle (first player at the beginning of the cycle is the first player).
     * In simple words when all Players meet in the same bet value.
     *
     * To check if all players' Bet values are equal is used HashMap in which
     * Players and their Bet values are stored.
     *
     *  @param p Add player to the phase4 HashMap when Player
     *          places a Bet
     */
    public static void passPhase4(Player p){
        // put player and value of his bet to the HashSet
        phase4.put(p, p.getSecondBid());
        checkBetStatus(p, phase4);
    }


    /**
     * Method used for Bet handling.
     * Checks if queue accomplished full cycle and
     * all players' bet values are equal (and not 0).
     *
     * @param p Player placing bet
     * @param bets HashMap containing Players with
     *             their bet values
     */
    public static void checkBetStatus(Player p, HashMap<Player, Integer> bets){
        ArrayList<Player> pl = new ArrayList<>(phase1);
        Player lastInTheQueue = pl.get(pl.size()-1);
        // check how many unique values are there in the actual Player's bets
        if(bets.size() == Player.getPlayerCount()){
            HashSet<Integer> uniqueValues = new HashSet<>(bets.values());
            // I need to check if now is the first player turn
            if(uniqueValues.size() == 1
                    // check if p equals to the last player in the order in queue
                    // (if yes that means that all players have equal bet value, we can move further)
                    && p == lastInTheQueue){
                incrementGamePhase();
            }
        }
    }

    /**
     * Method which returns text representation with short description
     * of each gamePhase.
     *
     * @return String gamePhase description
     */
    public static String strGamePhase(){
        String info;
        switch(gamePhase) {
            case 1:
                info = "[Start] Every player pays Ante and take cards from the table.";
                break;
            case 2:
                info = "[Bet 1] First round of betting. ";
                break;
            case 3:
                info = "[Exchange] Players exchange cards.";
                break;
            case 4:
                info = "[Bet 2] Second round of betting.";
                break;
            case 5:
                info = "[Finish] Determine a winner. ['winner' to check if you won!]";
                break;
            default:
                info = "";
        }
        return info;
    }

    /**
     * Method provides picking the winner functionality.
     * Winner with the highest gamePoints is picked and returned.
     *
     * @return winner - Player with the highest gamePoints value
     */
    public static Player pickWinner(){
        // I can pick winner on the grounds of the list of players from phase1
        Player winner = null;
        int winnerPts = 0;
        for(Player p : phase1){
            p.evaluatePlayerHand();
            if(p.getGamePoints() > winnerPts){
                winner = p;
                winnerPts = winner.getGamePoints();
            }
        }
        return winner;
    }

    public static int getGamePhase(){
        return gamePhase;
    }

    public static void incrementGamePhase(){
        gamePhase++;
    }

    public static void setWinner(Player p){
        winner = p;
    }

    public static Player getWinner(){
        return winner;
    }

    public static Set<Player> getPhase1() {
        return phase1;
    }

    public static HashMap<Player, Integer> getPhase2() {
        return phase2;
    }

    public static HashSet<Player> getPhase3() {
        return phase3;
    }

    public static HashMap<Player, Integer> getPhase4() {
        return phase4;
    }

    public static void setGamePhaseToOne(){
        gamePhase = 1;
    }

    public static void setGamePhase(int value){
        gamePhase = value;
    }

    public static void addRestartVote(Player p){
        restartVotes.put(p, true);
    }

    public static HashMap<Player, Boolean> getRestartVotes() {
        return restartVotes;
    }

    /**
     * Method provides clearing Containers during the game,
     * in the gamePhases and sets gamePhase to 1 (Start).
     */
    public static void clearGameplayData(){
        setGamePhaseToOne();
        phase1.clear();
        phase2.clear();
        phase3.clear();
        phase4.clear();
    }

}
