package pl.edu.agh.kis.pz1;


import java.util.Deque;
import java.util.LinkedList;

public class PlayerQueue {
    public static Deque<EchoService> queue = new LinkedList<>();

    private PlayerQueue(){}

    public static String strQueue() {
        StringBuilder strQueue = new StringBuilder("Queue: ");
        for(EchoService e : queue){
            strQueue.append("[").append(ClientIdentifiers.getPlayer(e).getNickname()).append("] ");
        }
        return String.valueOf(strQueue);
    }
        // sometimes when its 2 players queue doesn't change order after turn

    // takes first player from the queue to the end of the queue after his turn
    public static void nextPlayer() {
        EchoService e  = PlayerQueue.queue.getFirst();
        PlayerQueue.queue.addLast(e);
        PlayerQueue.queue.removeFirst();
    }

    public static Deque<EchoService> getQueue() {
        return queue;
    }
}
