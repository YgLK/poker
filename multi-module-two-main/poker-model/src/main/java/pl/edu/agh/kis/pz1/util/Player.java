package pl.edu.agh.kis.pz1.util;


import java.util.ArrayList;


public class Player {
    static int playerCount = 0;
    static Deck pokerDeck = new Deck();
    ArrayList<Card> cards = new ArrayList<>();
    String nickname;
    int money;
    int gamePoints;
    int firstBid;
    int secondBid;


    public Player(String nick){
        nickname = nick;
        money = 100;
        gamePoints = 0;
        firstBid = 0;
        secondBid = 0;
        incrementPlayerCount();
    }


    public void dealCards(){
        pokerDeck.shuffle();
        if(!cards.isEmpty()){
            cards.clear();
        }
        for(int i=0; i<5; i++){
            cards.add(pokerDeck.getCard());
        }
    }

    public String exchangeCards(String cardStr){
        if(cards.isEmpty()){
            return "You cannot exchange cards because you don't have any.";
        } else if (cardStr.isEmpty()){
            return "You didn't exchange any cards.";
        }
        String[] cardsIdxs = cardStr.split(" ");
        // TODO: check if idxes are in the 0-4 interval (0-4 idx)
        // TODO: check if there are only int in the String
        StringBuilder announce = new StringBuilder("You exchanged: ");
        for(String c : cardsIdxs){
            int idx = Integer.parseInt(c);

            announce.append(cards.get(idx).getCardStr());
            // return card from exchange to the deck
            pokerDeck.addCard(cards.get(idx));
            // replace card to exchange with new one
            cards.set(idx, pokerDeck.getCard());
        }
        return announce.toString();
    }

    public String yourCardsToString(){
        StringBuilder str = new StringBuilder("Your cards: ");
        for (Card c : cards) {
            str.append(" [").append(c.getCardRank()).append(" ").append(c.getCardSuit()).append("] ");
        }
        return str.toString();
    }

    public void setBid(int value){
        if(Gameplay.getGamePhase() == 2){
            firstBid = value;
        } else if (Gameplay.getGamePhase() == 4){
            secondBid = value;
        }
    }

    public void clearPlayerData(){
        pokerDeck.factory();
        cards.clear();
        money = 100;
        gamePoints = 0;
        firstBid=0;
        secondBid=0;
    }

    public void payAnte(){
        // let's say that Ante is 5
        money -= 5;
    }

    public void evaluatePlayerHand(){
        gamePoints = HandEvaluator.evaluateHand(cards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getMoney(){
        return money;
    }

    public String getNickname() {return nickname;}

    public int getGamePoints(){
        return gamePoints;
    }

    public static void incrementPlayerCount(){
        playerCount++;
    }

    public static int getPlayerCount() {
        return playerCount;
    }

    public void setFirstBid(int value){
        firstBid = value;
    }

    public int getFirstBid() {
        return firstBid;
    }

    public void setSecondBid(int value){
        secondBid = value;
    }

    public int getSecondBid(){
        return secondBid;
    }

    public int getfirstBid(){
        return firstBid;
    }

}
