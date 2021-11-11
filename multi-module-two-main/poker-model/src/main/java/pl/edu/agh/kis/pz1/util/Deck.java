package pl.edu.agh.kis.pz1.util;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class representation of a Deck
 * consisted of Card class instances
 * @version 1.0
 * @author J. Szpunar
 */
public class Deck {
    private ArrayList<Card> cards;

    /**
     * Construct a Deck.
     *
     * Cards in the Deck are initially sorted.
     */
    Deck(){
        cards = new ArrayList<>();
        // initially deck is sorted
        for(Rank r : Rank.values()){
            for(Suit s : Suit.values()){
                cards.add(new Card(s, r));
            }
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void factory(){
        // sort the deck
        cards.clear();
        for(Rank r : Rank.values()){
            for(Suit s : Suit.values()){
                cards.add(new Card(s, r));
            }
        }
    }

    public void shuffle(){
        // shuffle the deck
        Collections.shuffle(cards);
    }

    public void printDeck(){
        for (Card c : cards) {
            System.out.println(c.getCardRank() + " " + c.getCardSuit());
        }
    }

}
