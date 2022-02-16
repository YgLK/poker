package pl.edu.agh.kis.pz1;


import java.util.Deque;
import java.util.LinkedList;

/**
 * Class representing Player queue.
 */
public class PlayerQueue {
    /** Queue which provides right players' turn order. */
    public static Deque<EchoService> queue = new LinkedList<>();

    /**
     * Private Constructor to avoid Class instantiation.
     */
    private PlayerQueue(){}

    /**
     * Method which produces text representation of the current Player Queue.
     *
     * @return String with the actual Queue
     */
    public static String strQueue() {
        StringBuilder strQueue = new StringBuilder("Queue: ");
        int order = 1;
        for(EchoService e : queue){
            strQueue.append("[").append(order).append("] ").append(ClientIdentifiers.getPlayer(e).getNickname()).append(" ");
            order++;
        }
        return String.valueOf(strQueue);
    }

    /**
     * Method which takes care of the queue order.
     *
     * Takes first player from the queue to the end of the queue after his turn and
     * second player in the queue becomes first.
     */
    public static void nextPlayer() {
        EchoService e  = PlayerQueue.queue.getFirst();
        PlayerQueue.queue.addLast(e);
        PlayerQueue.queue.removeFirst();
    }

    public static Deque<EchoService> getQueue() {
        return queue;
    }

    public static void setQueue(Deque<EchoService> queue) {
        PlayerQueue.queue = queue;
    }
}
