package pl.edu.agh.kis.pz1.util;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class with represents Card Deck
 * consisted of Card class instances.
 */
public class Deck {
    private ArrayList<Card> cards;

    /**
     * Construct a Deck. Deck contains
     * 52 Cards - combinations of each Rank with Suits.
     * Cards in the Deck are initially sorted.
     */
    public Deck(){
        cards = new ArrayList<>();
        // initially deck is sorted
        for(Rank r : Rank.values()){
            for(Suit s : Suit.values()){
                cards.add(new Card(s, r));
            }
        }
    }

    /**
     * Method which lets to get first card from the Deck
     *
     * @return Card
     */
    public Card getCard(){
        Card c = cards.get(0);
        cards.remove(0);
        return c;
    }

    /**
     * Method which makes Deck sorted as initially order.
     */
    public void factory(){
        // sort the deck
        cards.clear();
        for(Rank r : Rank.values()){
            for(Suit s : Suit.values()){
                cards.add(new Card(s, r));
            }
        }
    }

    /**
     * Method shuffles the Deck. Cards are
     * no longer in the initial order.
     */
    public void shuffle(){
        // shuffle the deck
        Collections.shuffle(cards);
    }

    /**
     * Method provides text representation of the Cards in the Deck.
     *
     * @return String representing Deck
     */
    public String toString(){
        StringBuilder strDeck = new StringBuilder();
        for (Card c : cards) {
            strDeck.append("[").append(c.getCardRank()).append(" ").append(c.getCardSuit()).append("]");
        }
        return strDeck.toString();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void removeCard(int idx){
        cards.remove(idx);
    }

    public void addCard(Card c){
        cards.add(c);
    }
}
