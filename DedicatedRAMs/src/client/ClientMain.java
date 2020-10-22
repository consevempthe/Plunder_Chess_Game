package client;

import java.io.IOException;

import clientUI.LoginUI;
import server.RemoteSSHConnector;


public class ClientMain {
	public static void main(String[] args) throws IOException, InterruptedException, IllegalMoveException, IllegalPositionException
    { 
		RemoteSSHConnector connector = new RemoteSSHConnector(8818, 8000, "concord.cs.colostate.edu", "concord.cs.colostate.edu");
        connector.connect();
		Client client = new Client("localhost", 8818); 
        if(!client.connect())
        	System.err.println("Connection Failed.");
        else
        	System.out.println("Connection Succeeded.");
        LoginUI loginScreen = new LoginUI(client);
        /*Scanner in = new Scanner(System.in);
        System.out.println("Welcome to XGame - Plunder Chess!\n");
        String nextLine;
        while(client.getUser().getNickname() == null) {
        	System.out.println("Please Login using your nickname and password in the form: login [nickname] [password] or register in the form: register [nickname] [email] [password].");
        	nextLine  = in.nextLine();
        	client.request(nextLine + "\n");
        	Thread.sleep(1000);
        }
        
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
        }*/
    } 
}
