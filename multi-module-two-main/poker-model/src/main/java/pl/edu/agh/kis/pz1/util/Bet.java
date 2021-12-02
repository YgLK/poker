package pl.edu.agh.kis.pz1.util;

import java.util.ArrayList;

public class Bet {

    private Bet(){}

    public static int getMaxFirstBet(ArrayList<Player> players){
        int max = 0;
        for(Player p : players){
            if(max < p.getfirstBid()){
            max = p.getfirstBid();
            }
        }
        return max;
    }

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
