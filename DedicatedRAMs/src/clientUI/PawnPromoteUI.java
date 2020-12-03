package clientUI;

import gameLogic.Player.Color;

import javax.swing.*;

/**
 * PawnPromoteUI.java is a simple UI class to open an input dialog box
 * in order for the player to specify which type of piece they wish
 * to promote their pawn.
 * @author Dedicated RAMs Team
 */
public class PawnPromoteUI extends FrameUI {

	private Color color;
	private JFrame window;

	/**
	 * Constructor creates the dimensions and layout of the the dialog box.
	 * @param color - the color of the current player
	 */
	public PawnPromoteUI(Color color) {
		this.color = color;
		
		window = new JFrame("Promote your " + this.color.toString().toLowerCase() + " pawn! "
				+ new gameLogic.Pawn(null, color).toString());
		window.setSize(400, 300);
		window.setLayout(null);
		centerFrame(window);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
	}

	/**
	 * shows the dialog box to the user
	 * @return - returns the decision as a string to Pawn's upgrade method.
	 */
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

}
