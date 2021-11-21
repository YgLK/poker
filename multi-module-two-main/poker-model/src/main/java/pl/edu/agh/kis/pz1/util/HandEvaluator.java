package pl.edu.agh.kis.pz1.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;

// class to evaluate score of the cards on the player's hand
public class HandEvaluator {
    // LinkedHashMap to keep the order
    private static LinkedHashMap<Rank, Integer> rankCounter = new LinkedHashMap<>();
    private static LinkedHashMap<Suit, Integer> suitCounter = new LinkedHashMap<>();



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

    public static void groupCards(ArrayList<Card> cards){
        for(Card c : cards){
            // increment card rank count
            rankCounter.put(c.getCardRank(), rankCounter.get(c.getCardRank()) + 1);
            // increment card suit count
            suitCounter.put(c.getCardSuit(), suitCounter.get(c.getCardSuit()) + 1);
        }
    }

    // TODO: think about card combination hierarchy and points for each combination which will be
    //  easier to evaluate than checking every player's hand with each other
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
        } else if (CardHierarchy.isFourOfKind(rankCounter, suitCounter)){
            pointsHand = 160;
        } else if (CardHierarchy.isFullHouse(rankCounter, suitCounter)){
            pointsHand = 140;
        }else if (CardHierarchy.isFlush(rankCounter, suitCounter)){
            pointsHand = 120;
        }else if (CardHierarchy.isStraight(rankCounter, suitCounter)){
            pointsHand = 100;
        }else if (CardHierarchy.isThreeOfKind(rankCounter, suitCounter)){
            pointsHand = 80;
        }else if (CardHierarchy.isTwoPairs(rankCounter, suitCounter)){
            pointsHand = 60;
        }else if (CardHierarchy.isOnePair(rankCounter, suitCounter)){
            pointsHand = 40;
        }

        return pointsHand + getHighestCardPoints();
    }

    public static int getHighestCardPoints(){
        // add points to the score of the highest Card
        int initPoints = 13;
        Rank highest = CardHierarchy.getHighestRank(rankCounter, suitCounter);
        // for going down in Rank hierarchy subtract 1 from init Points
        // until you find the highest owned card rank
        for(Rank r : Rank.values()){
            if(highest == r){
                break;
            }
            // when you come up to your highest card break substracting loop
            initPoints -= 1;
        }
        // return left points
        return initPoints;
    }



}
