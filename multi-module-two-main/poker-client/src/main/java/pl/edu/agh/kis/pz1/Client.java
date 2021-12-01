package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.util.Gameplay;
import pl.edu.agh.kis.pz1.util.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    String nickname;

    Client(){
        System.out.println(getInstructions());
        // set nickname
        System.out.print("Enter your nickname: ");
        Scanner sc = new Scanner(System.in);
        nickname = sc.nextLine();
    }

    public void joinServer() throws IOException {
        // set hostname and port to which client will be joining
        final String HOST = "127.0.0.1";
        final int PORT = 4040;

//        System.out.println("Welcome to the Poker game room. Type 'queue' to check your position.");
        try (
                // initialize needed data
                Socket socket = new Socket(HOST, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner in = new Scanner(socket.getInputStream());
                Scanner s = new Scanner(System.in)
        ) {
            out.println(nickname); // pass nickname to the server
            System.out.println("You've paid Ante. Type 'get cards' to take cards from the table.");
            String input = "";
            while (true) {
                // get input from Client
                System.out.print(nickname + " - Input: ");
                    input = s.nextLine();
                // pass input to the server
                out.println(input);
                if (input.equalsIgnoreCase("exit")){
                    break;
                }

                String str;
                // doesnt work multiline still
                if(in.hasNextLine() && !((str=in.nextLine()).equals(""))){
                    do {
                        System.out.println(str);
                    }while(System.in.available() != 0);
                }

                // code below doesnt work because while never ends
                // but it's close to achieve the goal to print multiple lines at once to client
//                while(in.hasNextLine()){
//                    System.out.println(in.nextLine());
//                    if(input.equals("exit"))
//                        break;
//                }
            }
        }
    }

   public static String getInstructions(){
       return "Five-card draw (Poker) INSTRUCTIONS\n" +
               "    GAMEPLAN:\n" +
               "1. Every player pays Ante and take cards from the table.\n" +
               "2. First round of betting.\n" +
               "3. Players exchange cards.\n" +
               "4. Second round of betting.\n" +
               "5. Determine a winner. \n" +
               "    USEFUL COMMANDS:\n" +
               "'get cards' - take card from the table\n" +
               "'exchange cards _ _ _' - exchange cards from your hand (cards are numbered 0 to 4) e.g. 'exchange cards 1 3'\n" +
               "'queue' - show players queue\n" +
               "'balance' - show balance\n" +
               "'bet _value_' - bid the amount of _value_\n" +
               "'bid status' - info about betting phase\n" +
               "'winner' - show winner\n" +
               "'phase' - get current phase of the game \n" +
               "    Good luck!   \n";
    }
}
