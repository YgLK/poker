package pl.edu.agh.kis.pz1.util;

import jdk.internal.org.jline.utils.WriterOutputStream;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Player {
    String nickname;
    int money;
    Deck pokerDeck = new Deck();
    ArrayList<Card> cards = new ArrayList<>();

    public Player(String nick){
        nickname = nick;
        money = 100;
    }

    public void printCards(PrintWriter out){
        StringBuffer str = new StringBuffer("Your cards: ");
            for (Card c : cards) {
                str.append(" [").append(c.getCardRank()).append(" ").append(c.getCardSuit()).append("] ");
            }
        out.println(str);
    }

    public void dealCards(){
//        System.out.println("Your cards:");
        pokerDeck.shuffle();
        if(!cards.isEmpty()){
            cards.clear();
        }
        for(int i=0; i<5; i++){
            cards.add(pokerDeck.getCard());
        }
    }

    public void getAnte(){
        money -= 5; // let's say that Ante is 5
    }

    public int getMoney(){
        return money;
    }
}
