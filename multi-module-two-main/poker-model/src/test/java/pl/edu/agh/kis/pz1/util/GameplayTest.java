package pl.edu.agh.kis.pz1.util;

import org.junit.*;

import java.util.ArrayList;

/**
 * Class used for testing Gameplay
 * methods and functionalities.
 */
public class GameplayTest {
    static ArrayList<Player> players = new ArrayList<>();

    @BeforeClass
    public static void  initializePlayers(){
        // clear data from previous tests
        Gameplay.clearGameplayData();
        Player.setPlayerCount(0);

        // add players to the array list
        players.add(new Player("First"));
        players.add(new Player("Second"));
        players.add(new Player("Third"));
        players.add(new Player("Fourth"));

        // deal cards and set sample bids
        for(Player p : players){
            p.dealCards();
            p.setFirstBid(29);
            p.setSecondBid(26);
        }
    }

    @After
    public void resetValues(){
        Gameplay.getPhase1().clear();
    }

    @Test
    public void testPassPhase1() {
        Gameplay.setGamePhaseToOne();
        int phase = Gameplay.getGamePhase();

        for(Player p : players){
            Gameplay.passPhase1(p);
        }
        phase = Gameplay.getGamePhase();
        Assert.assertEquals("Game Phase should be equal to 2.",
                2,
                Gameplay.getGamePhase());
    }

    @Test
    public void testPassPhase2andIncrementGamePhase() {
        initPhase1ForNextTests();

        Gameplay.setGamePhase(2);
        for(Player p : players){
            Gameplay.passPhase2(p);
        }

        Assert.assertEquals("Game Phase should be equal to 3.",
                3,
                Gameplay.getGamePhase());
    }

    @Test
    public void testPassPhase3andIncrementGamePhase() {
        Gameplay.setGamePhase(3);
        for(Player p : players){
            Gameplay.passPhase3(p);
        }

        Assert.assertEquals("Game Phase should be equal to 4.",
                4,
                Gameplay.getGamePhase());
    }

    @Test
    public void testPassPhase4andIncrementGamePhase() {
        initPhase1ForNextTests();

        Gameplay.setGamePhase(4);
        for(Player p : players){
            Gameplay.passPhase4(p);
        }

        Assert.assertEquals("Game Phase should be equal to 5.",
                5,
                Gameplay.getGamePhase());
    }

    @Test
    public void testIncrementGamePhase() {
        int origGamePhase = Gameplay.getGamePhase();
        Gameplay.incrementGamePhase();

        Assert.assertEquals("Game Phase should be incremented.",
                origGamePhase + 1,
                Gameplay.getGamePhase());
    }

    @Test
    public void testStrGamePhaseThird() {
        Gameplay.setGamePhase(3);
        String phaseInfo = Gameplay.strGamePhase();
        Assert.assertEquals("String should contain information about third GamePhase.",
                "[Exchange] Players exchange cards.",
                phaseInfo);
    }

    @Test
    public void testStrGamePhaseFifth() {
        Gameplay.setGamePhase(5);
        String phaseInfo = Gameplay.strGamePhase();
        Assert.assertEquals("String should contain information about third GamePhase.",
                "[Finish] Determine a winner. ['winner' to check if you won!]",
                phaseInfo);
    }

    @Test
    public void testIfAppropriatePlayerIsWinner() {
        for(Player p : players){
            Gameplay.passPhase1(p);
        }
        // add sample cards for each player from Gameplay class
        for(Player p : Gameplay.getPhase1()){
            setSampleCardsForPlayer(p);
        }
        // add winner cards to the Third player
        players.get(3).getCards().clear();
        players.get(3).getCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
        players.get(3).getCards().add(new Card(Suit.DIAMONDS, Rank.KING));
        players.get(3).getCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        players.get(3).getCards().add(new Card(Suit.DIAMONDS, Rank.JACK));
        players.get(3).getCards().add(new Card(Suit.DIAMONDS, Rank.TEN));

        Assert.assertEquals("Player with the best cards isn't a winner.",
                players.get(3),
                Gameplay.pickWinner());
    }


    @Test
    public void testClearGameplayData() {
        Gameplay.clearGameplayData();

        Assert.assertTrue("Gameplay data hasn't been cleared properly.",
                Gameplay.getGamePhase() == 1 &&
                        Gameplay.getPhase1().size() == 0 &&
                        Gameplay.getPhase2().size() == 0 &&
                        Gameplay.getPhase3().size() == 0 &&
                        Gameplay.getPhase4().size() == 0
                );
    }

    public void initPhase1ForNextTests(){
        // phase1 set needed for testPassPhase2 and testPassPhase4
        for(Player p : players){
            Gameplay.passPhase1(p);
        }
    }


    public void setSampleCardsForPlayer(Player p){
        // clear player's cards
        p.getCards().clear();
        // add sample cards to his hand
        p.getCards().add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        p.getCards().add(new Card(Suit.DIAMONDS, Rank.THREE));
        p.getCards().add(new Card(Suit.HEARTS, Rank.SIX));
        p.getCards().add(new Card(Suit.CLUBS, Rank.JACK));
        p.getCards().add(new Card(Suit.DIAMONDS, Rank.ACE));
    }
}