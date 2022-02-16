package pl.edu.agh.kis.pz1;

import static junit.framework.TestCase.assertNotNull;
import org.junit.Test;

/**
 * Class used for testing Server
 * methods and functionalities.
 */
public class ServerTest {


    /**
     * Test for the construction of Server and the
     * main method being called
     *
     */
    @Test
    public void shouldCreateServerObject(){
        Server serv = new Server();
        assertNotNull("Main method called in class Server.", serv);
    }
}
