package pl.edu.agh.kis.pz1.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CardTest {

    // tests need to have better names

    @Test
    public void getCardSuit() {
        Card c = new Card(Suit.DIAMONDS, Rank.ACE);
        assertEquals(Suit.DIAMONDS, c.getCardSuit());
    }

    @Test
    public void getCardRank() {
        Card c = new Card(Suit.DIAMONDS, Rank.ACE);
        assertEquals(Rank.ACE, c.getCardRank());
    }

    @Test
    public void setCardSuit() {
        Card c = new Card(Suit.DIAMONDS, Rank.ACE);
        c.setCardSuit(Suit.SPADES);
        assertEquals(Suit.SPADES, c.getCardSuit());
    }

    @Test
    public void setCardRank() {
        Card c = new Card(Suit.DIAMONDS, Rank.ACE);
        c.setCardRank(Rank.JACK);
        assertEquals(Rank.JACK, c.getCardRank());
    }

}