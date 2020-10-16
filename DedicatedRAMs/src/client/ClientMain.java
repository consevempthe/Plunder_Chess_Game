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
        String nextLine;
        while(client.getUser().getNickname() == null) {
        	System.out.println("Please Login using your nickname and password in the form: login [nickname] [password] or register in the form: register [nickname] [email] [password].");
        	nextLine  = in.nextLine();
        	client.request(nextLine + "\n");
        	Thread.sleep(1000);
        	System.out.println(client.getUser().getNickname());
        }
        
        System.out.println("Starting Game!");
        client.getUser().createGame("Game1");
        client.getUser().getGame("Game1").startGame();
        nextLine  = "hi";
        while(nextLine != "quit") {
        	if(client.getUser().isReady()) {
        	nextLine  = in.nextLine();
        	client.request(nextLine + "\n");
        	}
        }
        
        if(in.nextLine().equals("quit")) {
        	in.close();
        	System.exit(0);
        }
    } 
}
