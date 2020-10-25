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
        //LoginUI loginScreen = new LoginUI(client);
        

        //From Hannah: This is an example of the UI rendering the board, I think it will be easy to render this when a game is started,
        //I just needs some help getting the game created right, I tried registering, but it kept saying it was a duplicate, maybe I'm missing
        //database stuff, I figured I'd leave this in anyways.

//		Runnable r = () -> {
//			ChessBoardUI cb = new ChessBoardUI(new Game("Test", new User("test", "test@gmail.com", "123")));
//
//			JFrame f = new JFrame("Plunder Chess");
//			f.add(cb.getGui());
//			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//			f.setLocationByPlatform(true);
//
//			// ensures the frame is the minimum size it needs to be
//			// in order display the components within it
//			f.pack();
//			// ensures the minimum size is enforced.
//			f.setMinimumSize(f.getSize());
//			f.setVisible(true);
//			System.out.println(cb.toString());
//		};
//		SwingUtilities.invokeLater(r);
	}
}
