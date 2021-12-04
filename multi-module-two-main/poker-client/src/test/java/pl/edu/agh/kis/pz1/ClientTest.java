package pl.edu.agh.kis.pz1;

import static junit.framework.TestCase.assertNotNull;

import org.junit.Test;

import java.io.ByteArrayInputStream;


public class ClientTest {

    /**
     * Test for the construction of Main and the
     * main method being called
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
}


