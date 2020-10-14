package client;

import java.io.IOException;
import java.util.Scanner;


public class ClientMain {
	public static void main(String[] args) throws IOException, InterruptedException, IllegalMoveException, IllegalPositionException
    { 
        Client client = new Client("localhost", 8818); 
        if(!client.connect())
        	System.err.println("Connection Failed.");
        else
        	System.out.println("Connection Succeeded.");
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to XGame - Plunder Chess!\n");
        System.out.println("Please Login using your nickname and password in the form: login [nickname] [password] or register in the form: register [nickname] [email] [password].");
        String loginOrRegister = in.nextLine() + "\n";
        client.request(loginOrRegister);
        //String response = client.response();
        //while(true) {
        //String request = in.nextLine() + "\n";
        //client.request(request);
        	//String msg = client.response();
        	//System.out.println(msg);
        	//if(msg.equals("quit\n"))
        	//		break;
        //}
        
        //Movement loop added by Hannah for testing my vest user logic remove when we have the UI/messages
        //to and from the clients through the server comment out the client/server stuff to use for testing
        
//        Scanner sc = new Scanner(System.in); 
//        ChessBoard board = new ChessBoard();
//        board.initialize();
//        board.move("e2", "e4");
//        board.move("d1", "h5");
//        board.move("g7", "g6");
//        System.out.println(board.toString());
//        
//        String response = "";
//        while(response != "!")
//        {
//        	System.out.print("Enter from location:");
//            String from = sc.nextLine();
//            
//            System.out.print("Enter to location:");
//            String to = sc.nextLine();
//            
//            try {
//				board.move(from, to);
//			} catch (IllegalMoveException e) {
//				  System.out.println("Illegal Move");
//				  continue;
//			} catch (IllegalPositionException e) {
//				  System.out.println("Illegal Position");
//				  continue;
//			}
//            
//            
//            System.out.println(board.toString());
//            System.out.print("Move Again? (! to quit)");
//            response = sc.nextLine();
//        }
    } 
}
