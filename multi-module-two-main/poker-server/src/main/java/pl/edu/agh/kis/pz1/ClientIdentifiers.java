package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.util.Player;

import java.io.PrintWriter;
import java.util.LinkedHashMap;

public class ClientIdentifiers {

    // HashMap which contains EchoServices of the Client and attached to it instance of Player class
    private static LinkedHashMap<EchoService, Player> players = new LinkedHashMap<>();

    // return players HashMap
    public static LinkedHashMap<EchoService, Player> getPlayers(){
        return players;
    }

    // add client to the players
    public static void addClient(EchoService es, Player pl) {
        ClientIdentifiers.players.put(es, pl);
    }

    // remove client from the players
    public static void removeClient(EchoService es) {
        ClientIdentifiers.players.remove(es);
    }

    // print information about current clients
    public static void printPlayers(PrintWriter out){
        StringBuilder str = new StringBuilder("Players [" + Server.numPlayers + "]: ");
        for(EchoService es : players.keySet()){
            str.append("[").append(players.get(es).getNickname()).append("] "); // check if players are added
        }
        out.println(str);
    }

}
