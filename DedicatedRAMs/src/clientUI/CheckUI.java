package clientUI;

import gameLogic.Player.Color;

import javax.swing.*;

public class CheckUI {

	public CheckUI(Color colorInCheck, String whitePlayer, String blackPlayer) {
		Object[] options = { "Ok" };
		String message, title, playerInCheck, opponent;
		if(colorInCheck == Color.WHITE) {
			playerInCheck = whitePlayer;
			opponent = blackPlayer;
		} else {
			playerInCheck = blackPlayer;
			opponent = whitePlayer;
		}
		message = opponent + " has you in check! move a piece to save your king!";
		title = playerInCheck + " in Check!";
		JOptionPane.showOptionDialog(null, 
				message, 
				title,
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				options, 
				options[0]);
	}

}