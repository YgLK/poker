package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    String nickname = "";

    Client(){
        System.out.println("Enter your nickname:");
        Scanner sc = new Scanner(System.in);
        nickname = sc.nextLine();
    }

    public void joinServer() throws IOException {
        final String HOST = "127.0.0.1";
        final int PORT = 4040;

        System.out.println("Client started.");
        try (
                Socket socket = new Socket(HOST, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner in = new Scanner(socket.getInputStream());
                Scanner s = new Scanner(System.in);
        ) {
            while (true) {
                System.out.print("Input: ");
                String input = s.nextLine();
                out.println(input);
                if (input.equalsIgnoreCase("exit")) break;
//                System.out.println("Echoed from server: " );
                System.out.println(in.nextLine());
            }
        }
    }
}
