package pl.edu.agh.kis.pz1;

import static junit.framework.TestCase.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;


public class ClientTest {

    /**
     * Test for the construction of Client and the
     * constructor being called
     *
     */
    @Test
    public void shouldCreateClientObject(){
        // set nickname to "Player Test"
        System.setIn(new ByteArrayInputStream("Player Test".getBytes()));
        // create Client class instance
        Client client = new Client();
        assertNotNull("Main method called on class Client.", client);
    }

    /**
     * Test for returning String
     * containing game instructions
     *
     */
    @Test
    public void shouldReturnInstructions() {
        // check if method returns Strings containing specific lines of instructions
        Assert.assertTrue(Client.getInstructions().contains("Five-card draw (Poker) INSTRUCTIONS"));
        Assert.assertTrue(Client.getInstructions().contains("GAMEPLAN"));
        Assert.assertTrue(Client.getInstructions().contains("USEFUL COMMANDS:"));
        Assert.assertTrue(Client.getInstructions().contains("'stay' - inform that you don't want exchange any cards in the third phase"));
        Assert.assertTrue(Client.getInstructions().contains("Good luck!"));
    }
}


