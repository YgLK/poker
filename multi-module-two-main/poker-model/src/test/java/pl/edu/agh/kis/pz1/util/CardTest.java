package pl.edu.agh.kis.pz1.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Class used for testing Card
 * methods and functionalities.
 */
public class CardTest {

    // tests need to have better names

    @Test
    public void shouldReturnCardSuit() {
        Card c = new Card(Suit.DIAMONDS, Rank.ACE);
        assertEquals(Suit.DIAMONDS, c.getCardSuit());
    }

    @Test
    public void shouldReturnCardRank() {
        Card c = new Card(Suit.DIAMONDS, Rank.ACE);
        assertEquals(Rank.ACE, c.getCardRank());
    }

    @Test
    public void shouldSetCardSuit() {
        Card c = new Card(Suit.DIAMONDS, Rank.ACE);
        c.setCardSuit(Suit.SPADES);
        assertEquals(Suit.SPADES, c.getCardSuit());
    }

    @Test
    public void shouldSetCardRank() {
        Card c = new Card(Suit.DIAMONDS, Rank.ACE);
        c.setCardRank(Rank.JACK);
        assertEquals(Rank.JACK, c.getCardRank());
    }

}