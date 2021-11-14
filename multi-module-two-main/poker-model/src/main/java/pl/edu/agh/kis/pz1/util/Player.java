package pl.edu.agh.kis.pz1.util;

import jdk.internal.org.jline.utils.WriterOutputStream;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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

    public void printCards(){
        StringBuffer str = new StringBuffer("Your cards: ");
        for (Card c : cards) {
            str.append(" [").append(c.getCardRank()).append(" ").append(c.getCardSuit()).append("] ");
        }
        System.out.println(str);
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

    public void exchangeCards(String cardStr, PrintWriter out){
        // when deck is empty return (it might happen when somebody want to exchange
        // cards even though cards are not dealt yet)
//        if(cards.isEmpty()){
//            out.println("You cannot exchange cards because you don't have any.");
//            return;
//        }

        String[] cardsIdxs = cardStr.split(" ");
        System.out.println("Idxes are splitted.");
        // TODO: check if idxes are in the 0-4 interval (0-4 idx)
        // TODO: check if there are only int in the String
        StringBuilder announce = new StringBuilder("You exchanged: ");
        for(String c : cardsIdxs){
            int idx = Integer.parseInt(c);
            System.out.println(idx);
            // comunicate
            announce.append(cards.get(idx).getCardStr());
            // return card from exchange to the deck
            pokerDeck.addCard(cards.get(idx));
            // replace card to exchange with new one
            cards.set(idx, pokerDeck.getCard());
        }
        out.println(announce);
        this.printCards();
    }

    public void getAnte(){
        money -= 5; // let's say that Ante is 5
    }

    public int getMoney(){
        return money;
    }

    public String getNickname() {return nickname;}
}
