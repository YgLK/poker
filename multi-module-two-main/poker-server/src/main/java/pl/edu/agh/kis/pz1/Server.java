package pl.edu.agh.kis.pz1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static int numPlayers = 0;
    //thread safe array because while one thread is reading another
    //might add delete some entries
//    private static HashMap<Integer,PrintWriter> printWriters;
    public static ArrayList<DataOutputStream> connectedServices = new ArrayList<>();

    public static void startServer() throws IOException {

        final int PORT = 4040;
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("Server started...");
        System.out.println("Wating for clients...");

        while (true) {
            PrintWriter tmp;
            Socket clientSocket = serverSocket.accept();
            numPlayers++;
            EchoService t = new EchoService(clientSocket);
//            {
//                public void run() {
//                    try (
////                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
////                            Scanner in = new Scanner(clientSocket.getInputStream());
//                            PrintWriter out = this.getOutPrintWriter();
//                            Scanner in = this.getInScanner();
//                    ){
//                        while (in.hasNextLine()) {
//                            String input = in.nextLine();
//                            if (input.equalsIgnoreCase("exit")) {
//                                numPlayers--;
//                                break;
//                            }
//                            System.out.println("Number of players: " + numPlayers);
//                            System.out.println("Received message from client: " + input);
//                            out.println("You said:" + input);
//                        }
////                    }catch (IOException e) {
////                        e.printStackTrace();
//                    }
//                }
//            };
            connectedServices.add(t.getOs());
            t.start();
//            for(DataOutputStream pw : connectedServices){
//                String serverMsg = "DOes it work?";
//                pw.writeUTF(serverMsg);
//                pw.flush();
//            }
        }
    }
}
