package pl.edu.agh.kis.pz1.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class PlayerTest {
    private static Player player;

    @Before
    public void init(){
        player = new Player("test player");
    }

    @After
    public void clearPlayerInfo(){
        player.clearPlayerData();
    }

    @Test
    public void testCreateNewPlayer(){
        Assert.assertTrue("Created Player doesn't have all properties cleared.",
                player.getNickname().equals("test player") &&
                        player.getGamePoints() == 0 &&
                        player.getFirstBid() == 0 &&
                        player.getSecondBid() == 0 &&
                        player.getMoney() == 100 &&
                        player.getCards().size() == 0
                );
    }

    @Test
    public void testDealCardsToPlayer() {
        player.dealCards();

        // test size
        Assert.assertEquals("Player's cards size should be 5.",
                5,
                player.getCards().size()
        );
    }

    @Test
    public void testIfDealtCardsAreDifferent() {
        player.dealCards();

        // test cards variety using Set
        Set<Card> variousCards = new HashSet<>(player.getCards());
        Assert.assertEquals("Player's cards should contain no duplicates.",
                5,
                variousCards.size());
    }

    @Test
    public void testIfCardsAreExchanged() {
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.KING));
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.JACK));
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.TEN));

        String input = "exchange cards 0 3";
        String idxs = input.substring(15);
        player.exchangeCards(idxs);

        Assert.assertNotEquals("First card should be exchanged.",
                new Card(Suit.DIAMONDS, Rank.ACE),
                player.getCards().get(0));

        Assert.assertNotEquals("Fourth card should be exchanged.",
                new Card(Suit.DIAMONDS, Rank.JACK),
                player.getCards().get(3));
    }

    @Test
    public void testIfPlayerCardsStringContainsCompulsoryElements() {
        player.dealCards();
        String cardsToString = player.yourCardsToString();

        // count brackets - counter checks balance between bracket [ and ]
        int counter = 0;
        for (char ch : cardsToString.toCharArray()){
            if (ch == '[') counter++;
            else if (ch == ']') counter--;
            // break if brackets are unbalanced for example: "[][["
            if (counter < 0) {
                break;
            }
        }

        Assert.assertTrue("Your Cards String doesn't contain compulsory elements.",
                cardsToString.contains("Your cards: ") &&
                        counter == 0
        );
    }

    @Test
    public void testSetBidOnSecondPhase() {
        Gameplay.setGamePhase(2);
        player.setBid(50);
        Assert.assertEquals("Bid not set properly.",
                50,
                player.getFirstBid());
    }

    @Test
    public void testSetBidOnFourthPhase() {
        Gameplay.setGamePhase(4);
        player.setBid(70);
        Assert.assertEquals("Bid not set properly.",
                70,
                player.getSecondBid());
    }

    @Test
    public void testClearPlayerData() {
        // set Player fields
        player.dealCards();
        player.payAnte();
        Gameplay.setGamePhase(2);
        player.setBid(23);
        Gameplay.setGamePhase(4);
        player.setBid(32);

        // clear Player data
        player.clearPlayerData();

        Assert.assertTrue("Player data hasn't been cleared properly.",
                player.getCards().size() == 0 &&
                player.getFirstBid() == 0 &&
                player.getSecondBid() == 0 &&
                player.getMoney() == 100);
    }

    @Test
    public void testPayAnte() {
        player.payAnte();

        Assert.assertEquals(95, player.getMoney());
    }

    @Test
    public void testEvaluatePlayerHand() {
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        player.getCards().add(new Card(Suit.SPADES, Rank.EIGHT));
        player.getCards().add(new Card(Suit.HEARTS, Rank.EIGHT));
        player.getCards().add(new Card(Suit.CLUBS, Rank.EIGHT));
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.ACE));

        player.evaluatePlayerHand();

        Assert.assertEquals("Player Gamepoints should be equal to: 160 (FourOfKind) + 13 (ACE is the highest) = 173",
                173,
                player.getGamePoints());
    }

    @Test
    public void testIncrementPlayerCount() {
        int beforeIncrementation = Player.getPlayerCount();

        // create new Player to increment Player Count
        new Player("");

        Assert.assertEquals("player count should increment after creating new Player",
                beforeIncrementation + 1,
                Player.getPlayerCount());
    }

    @Test
    public void testGetStringHandCombination(){
        player.clearPlayerData();
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.KING));
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.JACK));
        player.getCards().add(new Card(Suit.DIAMONDS, Rank.TEN));

        player.evaluatePlayerHand();

        Assert.assertEquals("Royal Flush",
                player.getStringHandCombination());
    }

}