package pl.edu.agh.kis.pz1;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class PlayerQueue {
    public static Deque<EchoService> queue = new LinkedList<>();

    public static String strQueue() {
        StringBuilder strQueue = new StringBuilder("Queue: ");
        for(EchoService e : queue){
            strQueue.append("[").append(ClientIdentifiers.getPlayer(e).getNickname()).append("] ");
        }
        return String.valueOf(strQueue);
    }

    // takes first player from the queue to the end of the queue after his turn
    public static void nextPlayer() {
        EchoService e  = PlayerQueue.queue.getFirst();
        PlayerQueue.queue.addLast(e);
        if(!PlayerQueue.queue.isEmpty()){
            PlayerQueue.queue.removeFirst();
        }
    }

    // something doesn't work here atm
    public static void passPlayer(){
        queue.pop();
    }
}
