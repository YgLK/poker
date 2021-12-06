package pl.edu.agh.kis.pz1.util;

import java.util.Objects;

/**
 * Class representation of the Card.
 * Each card has defined Suit and Rank.
 */
public class Card {
    private Suit cardSuit;
    private Rank cardRank;

    /**
     * Card constructor.
     * Card Suit and Rank need to be passed
     * while creating new Card.
     *
     * @param suit
     *          suit of the card
     * @param rank
     *          rank of the card
     */
    public Card(Suit suit, Rank rank){
        cardSuit = suit;
        cardRank = rank;
    }

    public void setCardSuit(Suit card_suit) {
        this.cardSuit = card_suit;
    }

    public void setCardRank(Rank card_rank) {
        this.cardRank = card_rank;
    }

    public Suit getCardSuit() {
        return cardSuit;
    }

    public Rank getCardRank() {
        return cardRank;
    }

    public String getCardStr(){
        return " [" + this.getCardRank() + " " + this.getCardSuit() + "] ";
    }

    /**
     * Overridden equals method
     * used for Cards comparison.
     * Method checks if cards are the same.
     *
     * @param o Input Card
     *
     * @return true if cards are equal
     *         else false
     */
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardSuit == card.cardSuit &&  cardRank == card.cardRank;
    }

    /**
     * Overridden hashCode method used
     * for creating unique hash for the object.
     *
     * @return unique hash corresponding to Card
     */
    @Override
    public int hashCode(){
        return Objects.hash(cardSuit, cardRank);
    }
}
