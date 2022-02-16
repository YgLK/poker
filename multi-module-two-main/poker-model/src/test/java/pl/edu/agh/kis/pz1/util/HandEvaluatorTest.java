package pl.edu.agh.kis.pz1.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Class used for testing HandEvaluator
 * methods and functionalities.
 */
public class HandEvaluatorTest {
    ArrayList<Card> cards = new ArrayList<>();

    @Before
    public void init(){
        HandEvaluator.initializeCounters();
    }

    @After
    public void clearCards(){
        cards.clear();
    }

    @Test
    public void checkCountersInitialization() {
        Assert.assertEquals("rankCounter size is not 13",
                13,
                HandEvaluator.getRankCounter().size());

        Assert.assertEquals("suitCounter size is not 4",
                4,
                HandEvaluator.getSuitCounter().size());
    }

    @Test
    public void checkCardsGroupingRanks() {
        cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.CLUBS, Rank.QUEEN));

        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Grouping Ranks doesn't count properly.",
                HandEvaluator.getRankCounter().get(Rank.EIGHT).equals(1) &&
                HandEvaluator.getRankCounter().get(Rank.QUEEN).equals(2) &&
                HandEvaluator.getRankCounter().get(Rank.TEN).equals(1) &&
                HandEvaluator.getRankCounter().get(Rank.NINE).equals(1) &&
                HandEvaluator.getRankCounter().get(Rank.ACE).equals(0));
    }

    @Test
    public void checkCardsGroupingSuits() {
        cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.CLUBS, Rank.JACK));

        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Grouping Suits doesn't count properly.",
                HandEvaluator.getSuitCounter().get(Suit.CLUBS).equals(1) &&
                        HandEvaluator.getSuitCounter().get(Suit.SPADES).equals(1) &&
                        HandEvaluator.getSuitCounter().get(Suit.HEARTS).equals(1) &&
                        HandEvaluator.getSuitCounter().get(Suit.DIAMONDS).equals(2)
                );
    }

    @Test
    public void checkHandEvaluation() {
        cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));
        cards.add(new Card(Suit.HEARTS, Rank.EIGHT));
        cards.add(new Card(Suit.CLUBS, Rank.EIGHT));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));

        Assert.assertEquals("Hand evaluation should return: 160 (FourOfKind) + 13 (ACE is the highest) = 173",
                173,
                HandEvaluator.evaluateHand(cards));
    }

    @Test
    public void checkIfReturnPointsForHighestCard() {
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.THREE));
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        cards.add(new Card(Suit.CLUBS, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.TWO));

        HandEvaluator.groupCards(cards);

        Assert.assertEquals("Should return points for the highest Card (in this case Queen = 11)",
                11,
                HandEvaluator.getHighestCardPoints());
    }

    @Test
    public void checkClear() {
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.THREE));
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        cards.add(new Card(Suit.CLUBS, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.TWO));

        HandEvaluator.evaluateHand(cards);
        HandEvaluator.clear();

        Assert.assertTrue("RankCounter and SuitCounter should be cleared and " +
                        "have size 0.",
                HandEvaluator.getRankCounter().size() == 0 &&
                        HandEvaluator.getSuitCounter().size() == 0
                );
    }

    @Test
    public void testGetRankCounterSize() {
        Assert.assertEquals("rankCounter size is not 13",
                13,
                HandEvaluator.getRankCounter().size());
    }

    @Test
    public void testGetSuitCounterSize() {
        Assert.assertEquals("suitCounter size is not 4",
                4,
                HandEvaluator.getSuitCounter().size());
    }
}