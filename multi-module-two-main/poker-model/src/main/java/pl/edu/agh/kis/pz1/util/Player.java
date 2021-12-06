package pl.edu.agh.kis.pz1.util;


import java.util.ArrayList;


/**
 * Class represents Player in the Poker game.
 */
public class Player {
    static int playerCount = 0;
    static Deck pokerDeck = new Deck();
    ArrayList<Card> cards = new ArrayList<>();
    String nickname;
    int money;
    int gamePoints;
    int firstBid;
    int secondBid;

    /**
     * Player constructor which takes Player's nickname as parameter.
     * Player's fields are initialized.
     *
     * @param nick Player's nickname
     */
    public Player(String nick){
        nickname = nick;
        money = 100;
        gamePoints = 0;
        firstBid = 0;
        secondBid = 0;
        incrementPlayerCount();
    }

    /**
     * Method used in the first GamePhase for dealing cards at the beginning of the game.
     * Dealt cards are in the Player's hand (cards ArrayList).
     */
    public void dealCards(){
        pokerDeck.shuffle();
        if(!cards.isEmpty()){
            cards.clear();
        }
        for(int i=0; i<5; i++){
            cards.add(pokerDeck.getCard());
        }
    }

    /**
     * The method used for exchanging cards
     * in the third GamePhase.
     *
     * @param cardStr Player's cards
     * @return Cards exchange method response
     */
    public String exchangeCards(String cardStr){
        if(cards.isEmpty()){
            return "You cannot exchange cards because you don't have any.";
        } else if (cardStr.isEmpty()){
            return "You didn't exchange any cards.";
        }
        String[] cardsIdxs = cardStr.split(" ");
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

    /**
     * The method produces text representation of the Player's cards.
     *
     * @return String cards representation
     */
    public String yourCardsToString(){
        StringBuilder str = new StringBuilder("Your cards: ");
        for (Card c : cards) {
            str.append(" [").append(c.getCardRank()).append(" ").append(c.getCardSuit()).append("] ");
        }
        return str.toString();
    }


    /**
     * Method sets Player's actual Bid value
     * according to the actual GamePhase.
     *
     * @param value Bid value
     */
    public void setBid(int value){
        if(Gameplay.getGamePhase() == 2){
            firstBid = value;
        } else if (Gameplay.getGamePhase() == 4){
            secondBid = value;
        }
    }

    /**
     * Method used for erasing
     * all the data corresponding to the Player.
     *
     * Fields values become as the initial ones.
     */
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

    public String getStringHandCombination(){
        if(isGamePointsHigherThan200(gamePoints)){
            return "Royal Flush";
        } else if(isGamePointsHigherThan180(gamePoints)){
            return "Straight Flush";
        } else if(isGamePointsHigherThan160(gamePoints)){
            return "Four Of Kind";
        } else if(isGamePointsHigherThan140(gamePoints)){
            return "Full House";
        } else if(isGamePointsHigherThan120(gamePoints)){
            return "Flush";
        } else if(isGamePointsHigherThan100(gamePoints)){
            return "Straight";
        } else if(isGamePointsHigherThan80(gamePoints)){
            return "Three Of Kind";
        } else if(isGamePointsHigherThan60(gamePoints)){
            return "Two Pairs";
        } else if(isGamePointsHigherThan40(gamePoints)){
            return "One Pair";
        } else {
            return "The highest card";
        }
    }


    public void evaluatePlayerHand(){
        gamePoints = HandEvaluator.evaluateHand(cards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
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

    public static void setPlayerCount(int playerCount) {
        Player.playerCount = playerCount;
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

    public boolean isGamePointsHigherThan200(int gamePoints){ return gamePoints > 200; }
    public boolean isGamePointsHigherThan180(int gamePoints){ return gamePoints > 180; }
    public boolean isGamePointsHigherThan160(int gamePoints){ return gamePoints > 160; }
    public boolean isGamePointsHigherThan140(int gamePoints){ return gamePoints > 140; }
    public boolean isGamePointsHigherThan120(int gamePoints){ return gamePoints > 120; }
    public boolean isGamePointsHigherThan100(int gamePoints){ return gamePoints > 100; }
    public boolean isGamePointsHigherThan80(int gamePoints){ return gamePoints > 80; }
    public boolean isGamePointsHigherThan60(int gamePoints){ return gamePoints > 60; }
    public boolean isGamePointsHigherThan40(int gamePoints){ return gamePoints > 40; }
}
