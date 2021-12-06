package pl.edu.agh.kis.pz1;

import org.junit.*;
import pl.edu.agh.kis.pz1.util.Player;

import java.net.Socket;
import java.util.LinkedList;


/**
 * Class used for testing PlayerQueue
 * methods and functionalities.
 */
public class PlayerQueueTest {

    @Before
    public void init(){
        PlayerQueue.setQueue(new LinkedList<>());
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
    }

    @AfterClass
    public static void clearData(){
        PlayerQueue.getQueue().clear();
        ClientIdentifiers.getPlayers().clear();
    }

    @Test
    public void testFirstBecomeLastPlayer() {
        EchoService firstInQ = PlayerQueue.getQueue().getFirst();
        PlayerQueue.nextPlayer();

        Assert.assertEquals("First in the queue should become the last one.",
                firstInQ,
                PlayerQueue.getQueue().getLast());
    }

    @Test
    public void testStrQueue() {
        String queueStr = PlayerQueue.strQueue();

        // count brackets - counter checks balance between bracket [ and ]
        int counter = 0;
        for (char ch : queueStr.toCharArray()){
            if (ch == '[') counter++;
            else if (ch == ']') counter--;
            // break if brackets are unbalanced for example: "[][["
            if (counter < 0) {
                break;
            }
        }

        Assert.assertTrue("Queue String doesn't contain compulsory elements.",
                queueStr.contains("Queue: ") &&
                        counter == 0
        );
    }


}