package pl.edu.agh.kis.pz1.util;


import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * Class used for Player's hand evaluation.
 * Provides methods necessary to sum each Player's
 * score based on the owned Cards.
 */
public class HandEvaluator {
    /** LinkedHashMap used for storing number of occurrences of the Ranks in the Player's hand */
    private static LinkedHashMap<Rank, Integer> rankCounter = new LinkedHashMap<>();
    /** LinkedHashMap used for storing number of occurrences of the Suits in the Player's hand  */
    private static LinkedHashMap<Suit, Integer> suitCounter = new LinkedHashMap<>();

    /**
     * Private Constructor to avoid Class instantiation.
     */
    private HandEvaluator(){}

    /**
     * Method used for initialization rankCounter and SuitCounter
     * HashMaps. For each rank and suit number of occurrences
     * is set to 0 initially.
     */
    public static void initializeCounters(){
        // initialize rankCounter with 0s
        for(Rank r : Rank.values()){
            rankCounter.put(r, 0);
        }
        // initialize suitCounter with 0s
        for(Suit s : Suit.values()){
            suitCounter.put(s, 0);
        }
    }

    /**
     * Method which groups cards and increment their
     * number of occurrences in the rankCounter and suitCounter
     * HashMaps.
     *
     * @param cards Player's cards
     */
    public static void groupCards(ArrayList<Card> cards){
        for(Card c : cards){
            // increment card rank count
            rankCounter.put(c.getCardRank(), rankCounter.get(c.getCardRank()) + 1);
            // increment card suit count
            suitCounter.put(c.getCardSuit(), suitCounter.get(c.getCardSuit()) + 1);
        }
    }

    /**
     * Method used for calculating score
     * of each Player's based on the owned Cards.
     */
    public static int evaluateHand(ArrayList<Card> cards){
        // initialize HashMaps with default values
        initializeCounters();
        // group ranks and suits with count
        groupCards(cards);

        int pointsHand = 0;

        // check poker hand
        if(CardHierarchy.isRoyalFlush(rankCounter, suitCounter)){
            pointsHand = 200;
        } else if (CardHierarchy.isStraightFlush(rankCounter, suitCounter)){
            pointsHand = 180;
        } else if (CardHierarchy.isFourOfKind(rankCounter)){
            pointsHand = 160;
        } else if (CardHierarchy.isFullHouse(rankCounter)){
            pointsHand = 140;
        }else if (CardHierarchy.isFlush(suitCounter)){
            pointsHand = 120;
        }else if (CardHierarchy.isStraight(rankCounter)){
            pointsHand = 100;
        }else if (CardHierarchy.isThreeOfKind(rankCounter)){
            pointsHand = 80;
        }else if (CardHierarchy.isTwoPairs(rankCounter)){
            pointsHand = 60;
        }else if (CardHierarchy.isOnePair(rankCounter)){
            pointsHand = 40;
        }

        return pointsHand + getHighestCardPoints();
    }

    /**
     * Method used for assigning points
     * for the highest owned Card.
     *
     * @return points value corresponding
     *         to the highest owned card
     */
    public static int getHighestCardPoints(){
        // add points to the score of the highest Card
        int initPoints = 13;
        Rank highest = CardHierarchy.getHighestRank(rankCounter);
        // for going down in Rank hierarchy subtract 1 from init Points
        // until you find the highest owned card rank
        for(Rank r : Rank.values()){
            if(highest == r){
                break;
            }
            // when you come up to your highest card break subtracting loop
            initPoints -= 1;
        }
        // return left points
        return initPoints;
    }

    /**
     * Clear rankCounter and suitCounter HashMaps.
     */
    public static void clear(){
        rankCounter.clear();
        suitCounter.clear();
    }

    public static LinkedHashMap<Rank, Integer> getRankCounter() {
        return rankCounter;
    }

    public static LinkedHashMap<Suit, Integer> getSuitCounter() {
        return suitCounter;
    }
}
