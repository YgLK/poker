package pl.edu.agh.kis.pz1;

import org.junit.*;
import pl.edu.agh.kis.pz1.util.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class used for testing EchoService
 * methods and functionalities.
 */
public class EchoServiceTest{
    // create writers to test printing
    static StringWriter out;
    static PrintWriter writer;


    @Before
    public void init(){
        PlayerQueue.setQueue(new LinkedList<>());
        // create writers to test printing
        out = new StringWriter();
        writer = new PrintWriter(out);

        // create EchoServices
        EchoService pl1 = new EchoService(new Socket());
        EchoService pl2 = new EchoService(new Socket());
        EchoService pl3 = new EchoService(new Socket());
        EchoService pl4 = new EchoService(new Socket());

        // add them to the queue
        PlayerQueue.getQueue().add(pl1);
        PlayerQueue.getQueue().add(pl2);
        PlayerQueue.getQueue().add(pl3);
        PlayerQueue.getQueue().add(pl4);

        //initialize ClientIdentifiers
        ClientIdentifiers.getPlayers().put(pl1, new Player("first"));
        ClientIdentifiers.getPlayers().put(pl2, new Player("second"));
        ClientIdentifiers.getPlayers().put(pl3, new Player("third"));
        ClientIdentifiers.getPlayers().put(pl4, new Player("fourth"));

        // initialize phase1 Set in Gameplay class, because of dependency in different methods in Gameplay
        Gameplay.getPhase1().add(ClientIdentifiers.getPlayers().get(pl1));
        Gameplay.getPhase1().add(ClientIdentifiers.getPlayers().get(pl2));
        Gameplay.getPhase1().add(ClientIdentifiers.getPlayers().get(pl3));
        Gameplay.getPhase1().add(ClientIdentifiers.getPlayers().get(pl4));
    }

    @After
    public void teardown(){
        Gameplay.clearGameplayData();
        Gameplay.getRestartVotes().clear();
        ClientIdentifiers.clearPlayersData();
        ClientIdentifiers.getPlayers().clear();
        PlayerQueue.getQueue().clear();
    }

    @Test
    public void testFirstPhaseReturnedMessageFromPrinter() {
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();
        firstInQueue.firstPhase("deal cards", writer);
        // get text returned to the printer
        String result = out.toString().trim();

        Assert.assertEquals("Printer should return information about dealing Cards.", "Cards have been dealt. ['show cards' will tell you what you got]", result);
    }

    @Test
    public void testBetPhases2and4ReturnedMessageFromPrinter() {
        // set Bet phase
        Gameplay.setGamePhase(2);
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        firstInQueue.secondFourthPhase("bet 32", writer);
        // get text returned to the printer
        String result = out.toString().trim();

        Assert.assertEquals("Printer should return information about placed bet.", "Bet of value 32 has been placed.", result);
    }

    @Test
    public void testPlacingBet() {
        // set Bet phase
        Gameplay.setGamePhase(4);
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        // place bet
        firstInQueue.placeBet("bet 60", Gameplay.getGamePhase(), writer);
        // get text returned to the printer
        String result = out.toString().trim();

        Assert.assertEquals("Printer should return information about placed bet.", "Bet of value 60 has been placed.", result);
    }

    @Test
    public void testThirdPhaseReturnedMessageFromPrinter() {
        // set Bet phase
        Gameplay.setGamePhase(3);
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        // add sample cards to the first player in the queue
        Player currentPlayer = ClientIdentifiers.getPlayers().get(firstInQueue);
        currentPlayer.getCards().clear();
        currentPlayer.getCards().add(new Card(Suit.DIAMONDS, Rank.TEN));
        currentPlayer.getCards().add(new Card(Suit.SPADES, Rank.THREE));
        currentPlayer.getCards().add(new Card(Suit.HEARTS, Rank.QUEEN));
        currentPlayer.getCards().add(new Card(Suit.CLUBS, Rank.JACK));
        currentPlayer.getCards().add(new Card(Suit.DIAMONDS, Rank.TWO));

        // exchange zero and third card
        firstInQueue.thirdPhase("exchange cards 0 3", writer);
        // get text returned to the printer
        String result = out.toString().trim();

        Assert.assertEquals("Printer should return information about exchanged Cards",
                "You exchanged:  [TEN DIAMONDS]  [JACK CLUBS]",
                result);
    }

    @Test
    public void testFifthPhaseReturnedMessageFromPrinter() {
        // set Bet phase
        Gameplay.setGamePhase(5);
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        // vote for the restart
        firstInQueue.fifthPhase("restart", writer);
        // get server answer from the printer to string
        String result = out.toString().trim();

        Assert.assertTrue("Printer should return information about voting for the restart state",
                result.contains("1/") &&
                result.contains(" voted for rematch."));
    }

    @Test
    public void testOtherCommandsWithUnknownCommand() {
        // set Bet phase
        Gameplay.setGamePhase(1);
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        // restart game to clear winner position and players cards
        firstInQueue.restartGame();


        // call other commands -try command that doesn't exist e.g.  "call police"
        String input = "call police";
        firstInQueue.otherCommands("call police", writer);

        // get server answer from the printer to string
        String result = out.toString().trim();

        Assert.assertEquals("Printer should return reaction to unknown command.",
                "Unknown command '" + input + "' OR you're in the wrong game phase to use it.",
                result);
    }

    @Test
    public void testGetMaxBetReturnMaxBetValue() {
        Gameplay.setGamePhase(2);

        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        // place bet
        firstInQueue.placeBet("bet 60", Gameplay.getGamePhase(), writer);


        Assert.assertEquals("Highest value of current bet should be returned",
                60,
                firstInQueue.getMaxBet());
    }

    @Test
    public void testStrGetQueueReturnQueueInfo() {
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();
        // restart queue to the beginning order

        Assert.assertTrue("Queue information should be returned.",
                firstInQueue.strGetQueue().contains("Your turn. Queue: ")
        );
    }

    @Test
    public void testGetActualBetValue() {
        // set game phase
        Gameplay.setGamePhase(2);
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        // place bet
        firstInQueue.placeBet("bet 35", 2, writer);

        Assert.assertEquals("Highest value of current bet should be returned",
                35,
                firstInQueue.getActualBetValue());
    }

    @Test
    public void testCardsExchangeExchangedCardsAreNotTheSame() {
        // set game phase
        Gameplay.setGamePhase(3);
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        // get player's cards
        Player currentPlayer = ClientIdentifiers.getPlayers().get(firstInQueue);
        ClientIdentifiers.getPlayers().get(firstInQueue).setCards(new ArrayList<Card>());
        ArrayList<Card> playerCards = ClientIdentifiers.getPlayers().get(firstInQueue).getCards();
        // clear cards
        playerCards.clear();
        // add sample cards
        playerCards.add(new Card(Suit.DIAMONDS, Rank.ACE));
        playerCards.add(new Card(Suit.CLUBS, Rank.TWO));
        playerCards.add(new Card(Suit.SPADES, Rank.QUEEN));
        playerCards.add(new Card(Suit.HEARTS, Rank.EIGHT));
        playerCards.add(new Card(Suit.CLUBS, Rank.TEN));

        // exchange cards
        firstInQueue.cardsExchange("exchange cards 1 3 4", writer);

        Assert.assertNotEquals("Second card should be exchanged.",
                new Card(Suit.CLUBS, Rank.TWO),
                currentPlayer.getCards().get(1));

        Assert.assertNotEquals("Fourth card should be exchanged.",
                new Card(Suit.HEARTS, Rank.EIGHT),
                currentPlayer.getCards().get(3));

        Assert.assertNotEquals("Fifth card should be exchanged.",
                new Card(Suit.CLUBS, Rank.TEN),
                currentPlayer.getCards().get(4));
    }

    @Test
    public void testDealCardsReturnInfoDealtCards() {

        // set game phase
        Gameplay.setGamePhase(1);
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        // restart game
        firstInQueue.restartGame();

        // get cards
        firstInQueue.dealCards(writer);
        // get server answer from the printer to string
        String result = out.toString().trim();

        Assert.assertEquals("Information about dealt card should be printed.",
                "Cards have been dealt. ['show cards' will tell you what you got]",
                result);
    }

    @Test
    public void testStrServerLogReturnStringLog() {
        // set game phase e.g. 5
        Gameplay.setGamePhase(5);
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        String stringLog = firstInQueue.strServerLog(firstInQueue.getName(), "balance");
        Assert.assertTrue("Method should return sample server log after calling \"balance\" command",
                stringLog.contains("[Phase ") &&
                        stringLog.contains("] ") &&
                        stringLog.contains(" call: ")
                );
    }

    @Test
    public void testExitServerCheckIfRemovePlayer() {
        int originalPlayerCount = Server.getNumPlayers();

        // set game phase e.g. 5
        Gameplay.setGamePhase(5);
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        // player exits server
        firstInQueue.exitServer(firstInQueue.getName());

        Assert.assertEquals("Player count should decrement",
                originalPlayerCount-1,
                Server.getNumPlayers());
    }

    @Test
    public void testRestartGameCheckIfDataCleared() {
        // get first player in the queue
        EchoService firstInQueue = PlayerQueue.getQueue().getFirst();

        firstInQueue.restartGame();

        Assert.assertTrue("Data should be cleared.",
                Gameplay.getPhase1().size() == 0 &&
                        Gameplay.getPhase2().size() == 0 &&
                        Gameplay.getPhase3().size() == 0 &&
                        Gameplay.getPhase4().size() == 0 &&
                        Gameplay.getGamePhase() == 1 &&
                        Gameplay.getRestartVotes().size() == 0
                        );
    }
}