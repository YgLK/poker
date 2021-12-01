package pl.edu.agh.kis.pz1;

import java.io.IOException;

/**
 * Przykładowy kod do zajęć laboratoryjnych 2, 3, 4 z przedmiotu: Programowanie zaawansowane 1
 * @author Paweł Skrzyński
 */
public class Main2 {
    public static void main( String[] args ) {

        Client client1 = new Client();
        try {
            client1.joinServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
