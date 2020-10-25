package clientUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.IllegalMoveException;
import client.IllegalPositionException;

/*
 * PawnPromoteUI.java is a simple UI class to open an input dialog box
 * in order for the player to specify which type of piece they wish
 * to promote their pawn.
 * @author Dedicated RAMs Team
 */

public class PawnPromoteUI {

	private client.ChessPiece.Color color;
	private final int WIDTH = 400, HEIGHT = 300;
	private JFrame window;

	// The constructor sets up the dimensions and layout for the input dialog
	// @param color - user color is used to personalize messages
	public PawnPromoteUI(client.ChessPiece.Color color) {
		this.color = color;
		
		window = new JFrame("Promote your " + this.color.toString().toLowerCase() + " pawn! "
				+ new client.Pawn(null, color).toString());
		window.setSize(WIDTH, HEIGHT);
		window.setLayout(null);
		this.centerFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
	}

	// centerFrame() is used as a helper method to set up the window dimensions
	private void centerFrame() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - WIDTH) / 2;
		int y = (dim.height - HEIGHT) / 2;
		window.setLocation(x, y);
	}
	
	// showDialog() after instantiating this class, call this method to ACTUALLY show the dialog box
	// @return String - returns the user promotion decision in the form of a String to give to Pawn.java
	public String showDialog () {
		String playerColor = this.color.toString().toLowerCase();
		Object[] choices = {"Queen", "Bishop", "Knight", "Rook"};
		String input = (String)JOptionPane.showInputDialog(window, 
				"Choose how to promote your " + playerColor + " pawn:\n", 
				playerColor + " pawn promotion!",
				JOptionPane.PLAIN_MESSAGE,
				null,
				choices,
				choices[0]
						);
		if(input != null && input.length() > 0) {
			return input.toUpperCase();
		}
		return null;
	}
	
	public static void main(String[] args) {
		client.ChessBoard cb = new client.ChessBoard(System.in);
		cb.placePiece(new client.Pawn(cb, client.ChessPiece.Color.WHITE), "a7");

		System.out.println(cb);
		try {
			cb.move("a7", "a8");
		} catch (IllegalMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cb);
	}

}
