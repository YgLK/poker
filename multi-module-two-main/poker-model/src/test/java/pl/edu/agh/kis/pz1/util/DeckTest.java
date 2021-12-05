package pl.edu.agh.kis.pz1.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class DeckTest{

    // tests need to have better names

    @Test
    public void shouldReturnCards() {
        Deck d = new Deck();
        ArrayList<Card> cards = d.getCards();
        // check if there are 52 cards in the Deck
        Assert.assertEquals(52, cards.size());
    }

    @Test
    public void shouldFactorySortDeck() {
        Deck d = new Deck();
        // shuffle Deck
        d.shuffle();
        // sort the Deck
        d.factory();
        // Deck is initially sorted
        Deck e = new Deck();
        // check if brand-new Deck cards are in the same order as a Deck which factory method was called
        Assert.assertEquals(e.getCards(), d.getCards());
    }

    @Test
    public void shouldShuffleTheDeck() {
        Deck d = new Deck();
        // shuffle Deck
        d.shuffle();
        // Deck e is initially sorted
        Deck e = new Deck();
        // check if brand-new Deck cards are not in the same order as shuffled Deck
        Assert.assertNotEquals(d.getCards(), e.getCards());
    }

    @Test
    public void shouldDeckHasSize52() {
        Deck d = new Deck();
        // shuffle Deck
        d.shuffle();
        Assert.assertEquals(52, d.getCards().size());
    }

}