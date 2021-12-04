package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.util.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClientIdentifiers {
    // HashMap which contains EchoServices of the Client and attached to it instance of Player class
    private static LinkedHashMap<EchoService, Player> players = new LinkedHashMap<>();

    private ClientIdentifiers(){};

    // return players HashMap
    public static LinkedHashMap<EchoService, Player> getPlayers(){
        return players;
    }

    // something here doesn't work
    public static ArrayList<Player> getPlayersKeys(){
        ArrayList<Player> pl = new ArrayList<>();
        for(Player p : players.values()){
            pl.add(p);
        }
        return pl;
    }

    public static Player getPlayer(EchoService e){
        return players.get(e);
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
    public static String playersListToStr(){
        StringBuilder str = new StringBuilder("Players [" + Server.numPlayers + "]: ");
        for(Map.Entry<EchoService, Player> entry : players.entrySet()){
            str.append("[").append(entry.getValue().getNickname()).append("] "); // check if players are added
        }
        return str.toString();
    }

    public static void clearPlayersData(){
        for(Player p : players.values()){
            p.clearPlayerData();
        }
    }
}
