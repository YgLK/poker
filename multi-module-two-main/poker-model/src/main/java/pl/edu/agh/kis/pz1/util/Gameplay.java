package pl.edu.agh.kis.pz1.util;


import java.util.*;


public class Gameplay {
    private static int gamePhase = 1;
    private static Set<Player> phase1 = new LinkedHashSet<>();
    private static HashMap<Player, Integer> phase2 = new HashMap<>();
    private static HashSet<Player> phase3 = new HashSet<>();
    private static HashMap<Player, Integer> phase4 = new HashMap<>();
    private static Player winner;
    private static HashMap<Player, Boolean> restartVotes = new HashMap<>();


    Gameplay(){}

    /*
    gamePhases
    1 - pay Ante & deal cards
    2 - first Bid
    3 - exchange cards
    4 - second Bid
    5 - evaluate Hands and under 'leader' command there will be  winner / restart game
    */

    // group players who have taken their cards from the table
    public static void passPhase1(Player p){
        phase1.add(p);
        System.out.println("Phase1 size " + phase1.size()); // debug
        System.out.println("Player count " + Player.getPlayerCount()); // debug
        if(phase1.size() == Player.getPlayerCount()){
            incrementGamePhase();
        }
        System.out.println("Game Phase:" + Gameplay.getGamePhase());
    }

    // pass this phase if all bets are equal, bets != 0 and first in the
    // queue is the same person which was at the beginning
    public static void passPhase2(Player p){
        // put player and value of his bet to the HashSet
        phase2.put(p, p.getfirstBid());
        ArrayList<Player> pl = new ArrayList<>(phase1);
        // debug
        Player lastInTheQueue = pl.get(pl.size()-1);
        // check how many unique values are there in the actual Player's bets
        if(phase2.size() == Player.getPlayerCount()){
            HashSet<Integer> uniqueValues = new HashSet<>(phase2.values());
            // I need to check if now is the first player turn
            System.out.println(uniqueValues.size()); // debug
            if(uniqueValues.size() == 1
                    // check if p equals to the last player in the order in queue
                    // (if yes that means that all players have equal bet value, we can move further)
                    && p == lastInTheQueue){
                incrementGamePhase();
            }
        }
    }

    // group players who have exchanged their cards
    public static void passPhase3(Player p){
        phase3.add(p);
        if(phase3.size() == Player.getPlayerCount()){
            incrementGamePhase();
        }
    }

    public static void passPhase4(Player p){
        // put player and value of his bet to the HashSet
        phase4.put(p, p.getSecondBid());
        ArrayList<Player> pl = new ArrayList<>(phase1);
        // debug
        Player lastInTheQueue = pl.get(pl.size()-1);
        // check how many unique values are there in the actual Player's bets
        if(phase4.size() == Player.getPlayerCount()){
            HashSet<Integer> uniqueValues = new HashSet<>(phase4.values());
            // I need to check if now is the first player turn
            System.out.println(uniqueValues.size()); // debug
            if(uniqueValues.size() == 1
                    // check if p equals to the last player in the order in queue
                    // (if yes that means that all players have equal bet value, we can move further)
                    && p == lastInTheQueue){
                incrementGamePhase();
            }
        }
    }

    public static int getGamePhase(){
        return gamePhase;
    }

    public static void incrementGamePhase(){
        gamePhase++;
        System.out.println("GamePhase in Increment func:" + Gameplay.getGamePhase());
    }

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

    public static Player pickWinner(){
        // i can pick winner on the grounds of list of players from phase1
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

    public static void addRestartVote(Player p){
        restartVotes.put(p, true);
    }

    public static HashMap<Player, Boolean> getRestartVotes() {
        return restartVotes;
    }

    public static void clearGameplayData(){
        setGamePhaseToOne();
        phase1.clear();
        phase2.clear();
        phase3.clear();
        phase4.clear();
    }

}
