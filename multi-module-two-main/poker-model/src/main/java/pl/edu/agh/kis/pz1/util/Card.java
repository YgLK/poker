package pl.edu.agh.kis.pz1.util;

import java.util.Objects;

/**
 * Class representation of a Card
 * @version 1.0
 * @author J. Szpunar
 */
public class Card {
    private Suit cardSuit;
    private Rank cardRank;

    /**
     * Construct a Card class.
     *
     * @param suit
     *          suit of the card
     * @param rank
     *          rank of the card
     */
    Card(Suit suit, Rank rank){
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

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardSuit == card.cardSuit &&  cardRank == card.cardRank;
    }

    @Override
    public int hashCode(){
        return Objects.hash(cardSuit, cardRank);
    }
}
