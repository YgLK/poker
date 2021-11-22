package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.util.TextUtils;
import pl.edu.agh.kis.pz1.util.Deck;
import pl.edu.agh.kis.pz1.util.Card;
import pl.edu.agh.kis.pz1.Client;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
