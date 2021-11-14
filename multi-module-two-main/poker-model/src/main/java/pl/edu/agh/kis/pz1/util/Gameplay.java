package pl.edu.agh.kis.pz1.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;


//import pl.edu.agh.kis.pz1.EchoService;


public class Gameplay {
    private Deck pokerDeck;
//    private LinkedHashMap<Player, EchoService> players;  // I am not sure if it is good idea to have this collection here
    private LinkedHashMap<Player, Integer> players;  // I am not sure if it is good idea to have this collection here


    // create hashmap with HashMap<Client, Player>
    // to have clarity which client is which player

    Gameplay(){
//        players = new LinkedHashMap<>();
        pokerDeck = new Deck();
    }

    public void startGame(){
        for(Player pl : players.keySet()){
            // players give ante at the beginning of the game
            pl.getAnte();
        }
        // shuffle the cards
        pokerDeck.shuffle();
        // deal the cards to the players
        dealCards();
    }

    public void dealCards(){
        // give each player 5 cards
        for(Player pl : players.keySet()){
            for(int i=0; i<5; i++){
                pl.cards.add(pokerDeck.getCard());
            }
        }
    }

}
