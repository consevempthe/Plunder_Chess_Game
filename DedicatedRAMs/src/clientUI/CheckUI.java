package clientUI;

import javax.swing.JOptionPane;

import client.Player.Color;

public class CheckUI {

	public CheckUI(Color colorInCheck) {
		String color = getColor(colorInCheck);
		String oppositeColor = negateColor(colorInCheck);
		Object[] options = { "Ok" };
		String message = oppositeColor + " has you " + "(" + color + ")" + " in check! move a piece to save your king!";
		String title = color + " in Check!";
		JOptionPane.showOptionDialog(null, 
				message, 
				title, 
				JOptionPane.PLAIN_MESSAGE, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				options, 
				options[0]);
	}

	private String getColor(Color color) {
		if (color == Color.WHITE) {
			return "White";
		} else {
			return "Black";
		}
	}

	private String negateColor(Color color) {
		if (color == Color.WHITE) {
			return getColor(Color.BLACK);
		} else {
			return getColor(Color.WHITE);
		}
	}

}