package pl.edu.agh.kis.pz1.util;

import java.util.ArrayList;

// it might be moved to the poker-common

/**
 * Class used for handling Bets.
 * Especially when maximum placed Bet value
 * of the current Bet phase is needed.
 */
public class Bet {

    /**
     * Private Constructor to avoid Class instantiation.
     */
    private Bet(){}

    /**
     * Method used for finding the maximum value of
     * placed bets during first Bet phase.
     *
     * @param players List of players
     *                participating in the game
     *
     * @return  Maximum value of placed Bets during First Bet phase
     */
    public static int getMaxFirstBet(ArrayList<Player> players){
        int max = 0;
        for(Player p : players){
            if(max < p.getFirstBid()){
            max = p.getFirstBid();
            }
        }
        return max;
    }

    /**
     * Method used for finding the maximum value of
     * placed bets during second Bet phase.
     *
     * @param players List of players
     *                participating in the game
     *
     * @return  Maximum value of placed Bets during First Bet phase
     */
    public static int getMaxSecondBet(ArrayList<Player> players){
        int max = 0;
        for(Player p : players){
            if(max < p.getSecondBid()){
                max = p.getSecondBid();
            }
        }
        return max;
    }
}
