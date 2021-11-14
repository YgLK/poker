package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.util.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoService extends Thread {
    private Socket acceptedSocket;
    private DataInputStream is;
    private DataOutputStream os;
    private PrintWriter outPrint;
    private Scanner inScanner;

    private Player player;


    public EchoService(Socket acceptedSocket) {
        try {
            this.acceptedSocket = acceptedSocket;
            is = new DataInputStream(acceptedSocket.getInputStream());
            os = new DataOutputStream(acceptedSocket.getOutputStream());
            outPrint = new PrintWriter(acceptedSocket.getOutputStream(), true);
            inScanner = new Scanner(acceptedSocket.getInputStream());

            player = new Player("client_nickname");
        } catch (IOException e) {
            try {
                if (this.acceptedSocket != null)
                    acceptedSocket.close();
                if(is != null)
                    is.close();
                if(os != null)
                    os.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try (
//                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//                            Scanner in = new Scanner(clientSocket.getInputStream());
                PrintWriter out = this.getOutPrintWriter();
                Scanner in = this.getInScanner();
        ){
//            while (in.hasNextLine()) {
            while(true) {
                is = new DataInputStream(acceptedSocket.getInputStream());
                os = new DataOutputStream(acceptedSocket.getOutputStream());

                String input = in.nextLine();
                if (input.equalsIgnoreCase("exit")) {
//                    numPlayers--;
                    break;
                }
//                System.out.println("Number of players: " + numPlayers);
                System.out.println("Received message from client: " + input);
                if (input.equalsIgnoreCase("deal cards")) {
                    player.dealCards();
                    out.println("Cards has been dealt.");
                } else if (input.equalsIgnoreCase("show cards")) {
//                    numPlayers--;
                    player.printCards(out);
                } else {
                    out.println("You said:" + input);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataInputStream getIs() {
        return is;
    }

    public DataOutputStream getOs() {
        return os;
    }

    public Socket getAcceptedSocket() {
        return acceptedSocket;
    }

    public PrintWriter getOutPrintWriter() {
        return outPrint;
    }

    public Scanner getInScanner() {
        return inScanner;
    }
}