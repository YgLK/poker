package pl.edu.agh.kis.pz1.util;

import java.util.Collections;
import java.util.LinkedHashMap;


// class which helps to define which poker hands does player have
public class CardHierarchy {

    private CardHierarchy(){}

    public static boolean isRoyalFlush(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return suitCounter.containsValue(5)
                && rankCounter.get(Rank.ACE) == 1
                && rankCounter.get(Rank.KING) == 1
                && rankCounter.get(Rank.QUEEN) == 1
                && rankCounter.get(Rank.JACK) == 1
                && rankCounter.get(Rank.TEN) == 1;
    }

    public static boolean isStraightFlush(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return suitCounter.size() == 1 && isFiveRanksInRow(rankCounter);
    }

    public static boolean isFourOfKind(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return rankCounter.containsValue(4);
    }

    public static boolean isFullHouse(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return rankCounter.containsValue(3) && rankCounter.containsValue(2);
    }

    public static boolean isFlush(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return suitCounter.containsValue(5);
    }

    public static boolean isStraight(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return isFiveRanksInRow(rankCounter);
    }

    public static boolean isThreeOfKind(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return rankCounter.containsValue(3);
    }

    public static boolean isTwoPairs(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return Collections.frequency(rankCounter.values(), 2) == 2;
    }

    public static boolean isOnePair(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return rankCounter.containsValue(2);
    }

    public static Rank getHighestRank(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        // go through ranks and return first one which count isn't 0
        // (ranks goes from ACE -> TWO)
        for (Rank r : rankCounter.keySet()) {
            if(rankCounter.get(r) != 0){
                return r;
            }
        }
        // return the lowest rank by default
        return Rank.TWO;
    }

    public static boolean isFiveRanksInRow(LinkedHashMap<Rank, Integer> rankCounter){
        // ranksInRow - how many descending cards ranks does player have on hand
        int ranksInRow = 0;
        for (Rank r : rankCounter.keySet()) {
            if(rankCounter.get(r) == 1){
                ranksInRow += 1;
                if(ranksInRow == 5){
                    return true;
                }
            }else {
                ranksInRow = 0;
            }
        }
        return false;
    }
}
