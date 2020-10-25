package client;

import java.io.IOException;

import clientUI.LoginUI;
import clientUI.ChessBoardUI;
import java.util.Scanner;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;


public class ClientMain {
	public static void main(String[] args)
			throws IOException, InterruptedException, IllegalMoveException, IllegalPositionException {
//        Client client = new Client("localhost", 8818);
//        if(!client.connect())
//        	System.err.println("Connection Failed.");
//        else
//        	System.out.println("Connection Succeeded.");
//        Scanner in = new Scanner(System.in);
//        System.out.println("Welcome to XGame - Plunder Chess!\n");
//        String nextLine;
//        while(client.getUser().getNickname() == null) {
//        	System.out.println("Please Login using your nickname and password in the form: login [nickname] [password] or register in the form: register [nickname] [email] [password].");
//        	nextLine  = in.nextLine();
//        	client.request(nextLine + "\n");
//        	Thread.sleep(1000);
//        	System.out.println(client.getUser().getNickname());
//        }
//
//        System.out.println("Starting Game!");
//        client.getUser().createGame("Game1");
//        client.getUser().getGame("Game1").startGame();
//        nextLine  = "hi";
//        while(nextLine != "quit") { //TODO replace this with the board UI
//        	if(client.getUser().isReady()) {
//        	nextLine  = in.nextLine();
//        	client.request(nextLine + "\n");
//        	}
//        }
//
//        if(in.nextLine().equals("quit")) {
//        	in.close();
//        	System.exit(0);
//        }


        //From Hannah: This is an example of the UI rendering the board, I think it will be easy to render this when a game is started,
        //I just needs some help getting the game created right, I tried registering, but it kept saying it was a duplicate, maybe I'm missing
        //database stuff, I figured I'd leave this in anyways.

		Runnable r = () -> {
			ChessBoardUI cb = new ChessBoardUI(new Game("Test", new User("test", "test@gmail.com", "123")));

			JFrame f = new JFrame("Plunder Chess");
			f.add(cb.getGui());
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.setLocationByPlatform(true);

			// ensures the frame is the minimum size it needs to be
			// in order display the components within it
			f.pack();
			// ensures the minimum size is enforced.
			f.setMinimumSize(f.getSize());
			f.setVisible(true);
			System.out.println(cb.toString());
		};
		SwingUtilities.invokeLater(r);
	}
}
