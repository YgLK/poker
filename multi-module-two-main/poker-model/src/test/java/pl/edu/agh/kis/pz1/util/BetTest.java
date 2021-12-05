package pl.edu.agh.kis.pz1.util;

import org.junit.*;

import java.util.ArrayList;
import java.util.logging.Logger;


public class BetTest{
    private static final Logger LOGGER = Logger.getLogger(BetTest.class.getName());
    public static ArrayList<Player> players = new ArrayList<>();

    @BeforeClass
    public static void init(){
        LOGGER.info("BetTest startup");
        // add players to the ArrayList
        String[] nicknames =new String[]{"0", "1", "2", "3", "4"};
        for(int i=0; i < 5; i++){
            players.add(new Player(nicknames[i]));
        }
        // set bet values for each Player
        players.get(0).setFirstBid(23);
        players.get(1).setFirstBid(83);
        players.get(2).setFirstBid(22);
        players.get(3).setFirstBid(53);
        players.get(4).setFirstBid(36);

        players.get(0).setSecondBid(50);
        players.get(1).setSecondBid(23);
        players.get(2).setSecondBid(42);
        players.get(3).setSecondBid(12);
        players.get(4).setSecondBid(60);
    }

    @Test
    public void shouldReturnMaxFirstBet() {
        Assert.assertEquals(83,Bet.getMaxFirstBet(players));
    }

    @Test
    public void shouldReturnMaxSecondBet(){
        Assert.assertEquals(60, Bet.getMaxSecondBet(players));
    }

    @AfterClass
    public static void teardown(){
        LOGGER.info("BetTest teardown");
        players.clear();
    }

}