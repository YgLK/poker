package pl.edu.agh.kis.pz1.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Class used for Player's hand estimation.
 * Provides the methods necessary to check which
 * hand ranking hierarchy combination Player has, if any.
 */
public class CardHierarchy {

    private CardHierarchy(){}

    /**
     * Check if Player has Royal Flush in his Cards.
     * A Royal Flush is made out of 10, Jack, Queen, King, Ace, all of the same suit.
     *
     * @param rankCounter hashMap in which Rank quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     * @param suitCounter hashMap in which Suit quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     *
     * @return true if Royal Flush occurs
     *         else false
     */
    public static boolean isRoyalFlush(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return suitCounter.containsValue(5)
                && rankCounter.get(Rank.ACE) == 1
                && rankCounter.get(Rank.KING) == 1
                && rankCounter.get(Rank.QUEEN) == 1
                && rankCounter.get(Rank.JACK) == 1
                && rankCounter.get(Rank.TEN) == 1;
    }

    /**
     * Check if Player has Straight Flush in his Cards.
     * A Straight Flush is five cards in a row, all in the same suit.
     *
     * @param rankCounter hashMap in which Rank quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     * @param suitCounter hashMap in which Suit quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     *
     * @return true if Straight Flush occurs
     *         else false
     */
    public static boolean isStraightFlush(LinkedHashMap<Rank, Integer> rankCounter, LinkedHashMap<Suit, Integer> suitCounter){
        return suitCounter.containsValue(5) && isFiveRanksInRow(rankCounter);
    }

    /**
     * Check if Player has Four of a Kind in his Cards.
     * A Four of a Kind is the same card in each of the four suits.
     *
     * @param rankCounter hashMap in which Rank quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     *
     * @return true if Four of a Kind occurs
     *         else false
     */
    public static boolean isFourOfKind(LinkedHashMap<Rank, Integer> rankCounter){
        return rankCounter.containsValue(4);
    }

    /**
     * Check if Player has Full House in his Cards.
     * A Full House is a pair plus three of a kind in the same hand.
     *
     * @param rankCounter hashMap in which Rank quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     *
     * @return true if Full House occurs
     *         else false
     */
    public static boolean isFullHouse(LinkedHashMap<Rank, Integer> rankCounter){
        return rankCounter.containsValue(3) && rankCounter.containsValue(2);
    }

    /**
     * Check if Player has Flush in his Cards.
     * A Flush is five cards in the same suit, not in numerical order.
     *
     * @param suitCounter hashMap in which Suit quantity are
     *                    stored - it stores how many Cards of each
     *                    rank does Player have
     *
     * @return true if Flush occurs
     *         else false
     */
    public static boolean isFlush(LinkedHashMap<Suit, Integer> suitCounter){
        return suitCounter.containsValue(5);
    }

    /**
     * Check if Player has Straight in his Cards.
     * A Straight is five cards in numerical order, but not in the same suit.
     *
     * @param rankCounter hashMap in which Rank quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     *
     * @return true if Straight occurs
     *         else false
     */
    public static boolean isStraight(LinkedHashMap<Rank, Integer> rankCounter){
        return isFiveRanksInRow(rankCounter);
    }


    /**
     * Check if Player has Three of a Kind in his Cards.
     * Three of a Kind is three of one card and two non-paired cards.
     *
     * @param rankCounter hashMap in which Rank quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     *
     * @return true if Three of a Kind occurs
     *         else false
     */
    public static boolean isThreeOfKind(LinkedHashMap<Rank, Integer> rankCounter){
        return rankCounter.containsValue(3);
    }

    /**
     * Check if Player has Two Pairs in his Cards.
     * Two Pairs is two different pairings or sets of the same card in one hand.
     *
     * @param rankCounter hashMap in which Rank quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     *
     * @return true if Two Pairs occurs
     *         else false
     */
    public static boolean isTwoPairs(LinkedHashMap<Rank, Integer> rankCounter){
        return Collections.frequency(rankCounter.values(), 2) == 2;
    }

    /**
     * Check if Player has One Pair in his Cards.
     * One Pair is a pairing of the same card.
     *
     * @param rankCounter hashMap in which Rank quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     *
     * @return true if One Pair occurs
     *         else false
     */
    public static boolean isOnePair(LinkedHashMap<Rank, Integer> rankCounter){
        return rankCounter.containsValue(2);
    }

    /**
     * Method used for determining the highest Card in the Player's hand.
     *
     * @param rankCounter hashMap in which Rank quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     *
     * @return the highest owned cards
     */
    public static Rank getHighestRank(LinkedHashMap<Rank, Integer> rankCounter){
        // go through ranks and return first one which count isn't 0
        // (ranks goes from ACE -> TWO)
        for (Map.Entry<Rank, Integer> e : rankCounter.entrySet()) {
            if(e.getValue() != 0){
                return e.getKey();
            }
        }
        // return the lowest rank by default
        return Rank.TWO;
    }

    /**
     * Method used to determine if five cards in a row occurs.
     *
     * @param rankCounter hashMap in which Rank quantity are stored - it stores
     *                    how many Cards of each rank does Player have
     *
     * @return true if five cards in a row occurs
     *         else false
     */
    public static boolean isFiveRanksInRow(LinkedHashMap<Rank, Integer> rankCounter){
        // ranksInRow - how many descending cards ranks does player have on hand
        int ranksInRow = 0;
        for (Map.Entry<Rank, Integer> entry : rankCounter.entrySet()) {
            if(entry.getValue() == 1){
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
