package client;

import clientUI.ChessBoardUI;
import server.RemoteSSHConnector;

import javax.swing.*;
import java.io.IOException;


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

		//NOTE from ethan : run client main to test the chessboard

		User user = new User("Ethan", "test@mail.com", "password");
		Game test = new Game("1234", user);
		test.setPlayers(new Player(Player.Color.WHITE, "Ethan"), new Player(Player.Color.BLACK, "Axel"));

		Runnable r = () -> {
			ChessBoardUI cb = new ChessBoardUI(test, client);

			JFrame f = new JFrame("Plunder Chess");
			f.add(cb.getGui());
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.setLocationByPlatform(true);

			f.pack();
			f.setMinimumSize(f.getSize());
			f.setVisible(true);
			System.out.println(cb.toString());
		};
		SwingUtilities.invokeLater(r);
	}
}
