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
//        System.out.println( "Szablon projektu z dwiem metodami main i zależnościami wbudowanymi w wykonywalny jar" );
//        //wywołanie metody generującej hash SHA-512
//        System.out.println("HASH 512 dla słowa test: " + TextUtils.sha512Hash("test"));

//        cardsTest();
        Client client1 = new Client();
        try {
            client1.joinServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    public static void cardsTest() {
        int players_count = 4, card_count = 5;
        Deck card_deck = new Deck();
        card_deck.shuffle(); // shuffle the deck
        Map<Integer, List<Card>> players = new HashMap<>();
        for (int i = 0; i < players_count; i++) {
            for (int j = 0; j < card_count; j++) {
                Card picked_card = card_deck.getCards().get(0);
                if (players.get(i) == null) {
                    players.put(i, new ArrayList<Card>(){
                        {
                            // add the card to the player no. i
                            add(picked_card);
                        }
                    });
                } else {
                    players.get(i).add(picked_card);
                }
                card_deck.getCards().remove(0); // remove picked card from the deck
            }
        }

        // print given cards
        for (int k = 0; k < players_count; k++) {
            System.out.println("Player " + (k + 1) + " cards:");
            for (Card c : players.get(k)) {
                System.out.println(c.getCardRank() + " " + c.getCardSuit());
            }
            System.out.println();
        }
    }

}
