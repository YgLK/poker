package pl.edu.agh.kis.pz1;

import org.junit.*;
import pl.edu.agh.kis.pz1.util.Player;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class used for testing ClientIdentifier
 * methods and functionalities.
 */
public class ClientIdentifiersTest{
    static ArrayList<Player> players = new ArrayList<>();

    @BeforeClass
    public static void init(){
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

        // add entries to HashMap players in ClientIdentifiers
        for(Player p : players){
            ClientIdentifiers.getPlayers().put(new EchoService(new Socket()), p);
        }
    }

    @AfterClass
    public static void clearData(){
        PlayerQueue.getQueue().clear();
        ClientIdentifiers.getPlayers().clear();
    }

    @Test
    public void testGetPlayersSizeEqualsPlayerCount() {
        HashMap<EchoService, Player> mapPlayers = ClientIdentifiers.getPlayers();
        Assert.assertEquals(4, mapPlayers.size());
    }

    @Test
    public void testGetPlayersKeysSizeEqualsPlayerCount() {
        ArrayList<Player> playerInstances = ClientIdentifiers.getPlayersKeys();
        Assert.assertEquals(4, playerInstances.size());
    }

    @Test
    public void testGetPlayersKeysIfCardsCountIsValid() {
        ArrayList<Player> playerInstances = ClientIdentifiers.getPlayersKeys();
        Assert.assertEquals(5, playerInstances.get(0).getCards().size());
    }

    @Test
    public void testPlayersListToStr() {
        String playersStr = ClientIdentifiers.playersListToStr();

        // count brackets - counter checks balance between bracket [ and ]
        int counter = 0;
        for (char ch : playersStr.toCharArray()){
            if (ch == '[') counter++;
            else if (ch == ']') counter--;
            // break if brackets are unbalanced for example: "[][["
            if (counter < 0) {
                break;
            }
        }

        Assert.assertTrue("Player String doesn't contain compulsory elements.",
                playersStr.contains("Players [" + Server.numPlayers + "]: ") &&
                        counter == 0
        );
    }

    @Test
    public void testClearPlayersData() {
        ClientIdentifiers.clearPlayersData();

        boolean checkIfCardsCleared = true;
        ArrayList<Player> playerInstances = ClientIdentifiers.getPlayersKeys();
        for(Player p : playerInstances){
            if(p.getCards().size() != 0) {
                checkIfCardsCleared = false;
            }
        }
        Assert.assertEquals("Not all player's cards are cleared.",true, checkIfCardsCleared);
    }
}