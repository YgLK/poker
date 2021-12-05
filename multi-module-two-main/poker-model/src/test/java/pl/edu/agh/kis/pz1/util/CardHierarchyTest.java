package pl.edu.agh.kis.pz1.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;


public class CardHierarchyTest {
    private static final Logger LOGGER = Logger.getLogger(BetTest.class.getName());
    public static ArrayList<Card> cards = new ArrayList<>();

    // run after each test
    @After
    public void clearHand(){
        HandEvaluator.clear();
        cards.clear();
    }

    @Test
    public void testIsRoyalFlush() {
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Occurring Royal Flush in the cards isn't recognized.",
                CardHierarchy.isRoyalFlush(HandEvaluator.getRankCounter(), HandEvaluator.getSuitCounter()));
    }

    @Test
    public void testIsNotRoyalFlush() {
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertFalse("Royal Flush is recognized in the cards even though it doesn't occur",
                CardHierarchy.isRoyalFlush(HandEvaluator.getRankCounter(), HandEvaluator.getSuitCounter()));
    }

    @Test
    public void testIsStraightFlush() {
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.EIGHT));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Occurring Straight Flush in the cards isn't recognized.",
                CardHierarchy.isStraightFlush(HandEvaluator.getRankCounter(), HandEvaluator.getSuitCounter()));
    }

    @Test
    public void testIsNotStraightFlush() {
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.SEVEN));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertFalse("Straight Flush is recognized in the cards even though it doesn't occur.",
                CardHierarchy.isStraightFlush(HandEvaluator.getRankCounter(), HandEvaluator.getSuitCounter()));
    }

    @Test
    public void testIsFourOfKind() {
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        cards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        cards.add(new Card(Suit.SPADES, Rank.THREE));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Occurring Four of Kind in the cards isn't recognized.",
                CardHierarchy.isFourOfKind(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsNotFourOfKind() {
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        cards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        cards.add(new Card(Suit.HEARTS, Rank.JACK));
        cards.add(new Card(Suit.SPADES, Rank.JACK));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertFalse("Four of Kind is recognized in the cards even though it doesn't occurs.",
                CardHierarchy.isFourOfKind(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsFullHouse() {
        cards.add(new Card(Suit.SPADES, Rank.FOUR));
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));
        cards.add(new Card(Suit.HEARTS, Rank.FOUR));
        cards.add(new Card(Suit.SPADES, Rank.KING));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Occurring Full House in the cards isn't recognized.",
                CardHierarchy.isFullHouse(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsNotFullHouse() {
        cards.add(new Card(Suit.SPADES, Rank.FOUR));
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));
        cards.add(new Card(Suit.HEARTS, Rank.FOUR));
        cards.add(new Card(Suit.SPADES, Rank.THREE));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertFalse("Full House in the cards is recognized even though it doesn't occur.",
                CardHierarchy.isFullHouse(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsFlush() {
        cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.THREE));
        cards.add(new Card(Suit.DIAMONDS, Rank.SIX));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Occurring Flush in the cards isn't recognized.",
                CardHierarchy.isFlush(HandEvaluator.getSuitCounter()));
    }

    @Test
    public void testIsNotFlush() {
        cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.THREE));
        cards.add(new Card(Suit.HEARTS, Rank.SIX));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.ACE));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertFalse("Flush in the cards is recognized even though it doesn't occur.",
                CardHierarchy.isFlush(HandEvaluator.getSuitCounter()));
    }

    @Test
    public void testIsStraight() {
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        cards.add(new Card(Suit.CLUBS, Rank.EIGHT));
        cards.add(new Card(Suit.HEARTS, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.TEN));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Existing Straight Flush in the cards isn't recognized.",
                CardHierarchy.isStraight(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsNotStraight() {
        cards.add(new Card(Suit.SPADES, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        cards.add(new Card(Suit.CLUBS, Rank.QUEEN));
        cards.add(new Card(Suit.HEARTS, Rank.NINE));
        cards.add(new Card(Suit.SPADES, Rank.TEN));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertFalse("Straight in the cards is recognized even though it doesn't occur.",
                CardHierarchy.isStraight(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsThreeOfKind() {
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.THREE));
        cards.add(new Card(Suit.CLUBS, Rank.QUEEN));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Existing Three of Kind in the cards isn't recognized.",
                CardHierarchy.isThreeOfKind(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsNotThreeOfKind() {
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertFalse("Three of Kind in the cards is recognized even though it doesn't occur.",
                CardHierarchy.isThreeOfKind(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsTwoPairs() {
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Existing Two Pairs in the cards isn't recognized.",
                CardHierarchy.isTwoPairs(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsNotTwoPairs() {
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertFalse("Two Pairs in the cards is recognized even though it doesn't occur.",
                CardHierarchy.isTwoPairs(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsOnePair() {
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.CLUBS, Rank.ACE));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Existing One Pair in the cards isn't recognized.",
                CardHierarchy.isOnePair(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsNotOnePair() {
        cards.add(new Card(Suit.SPADES, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.HEARTS, Rank.ACE));
        cards.add(new Card(Suit.SPADES, Rank.TWO));
        cards.add(new Card(Suit.CLUBS, Rank.FOUR));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertFalse("One Pair in the cards is recognized even though it doesn't occur.",
                CardHierarchy.isOnePair(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testGetHighestRank() {
        cards.add(new Card(Suit.SPADES, Rank.TEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.CLUBS, Rank.THREE));
        cards.add(new Card(Suit.HEARTS, Rank.TWO));
        cards.add(new Card(Suit.SPADES, Rank.THREE));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertEquals(Rank.KING,
                CardHierarchy.getHighestRank(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsFiveRanksInRow() {
        cards.add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        cards.add(new Card(Suit.SPADES, Rank.NINE));
        cards.add(new Card(Suit.HEARTS, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));
        cards.add(new Card(Suit.CLUBS, Rank.JACK));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertTrue("Five cards in a row isn't recognized.",
                CardHierarchy.isFiveRanksInRow(HandEvaluator.getRankCounter()));
    }

    @Test
    public void testIsNotFiveRanksInRow() {
        cards.add(new Card(Suit.DIAMONDS, Rank.FOUR));
        cards.add(new Card(Suit.DIAMONDS, Rank.KING));
        cards.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        cards.add(new Card(Suit.DIAMONDS, Rank.JACK));
        cards.add(new Card(Suit.DIAMONDS, Rank.TEN));

        HandEvaluator.initializeCounters();
        HandEvaluator.groupCards(cards);

        Assert.assertFalse("Five cards in a row shouldn't be recognized in this case.",
                CardHierarchy.isFiveRanksInRow(HandEvaluator.getRankCounter()));
    }

}