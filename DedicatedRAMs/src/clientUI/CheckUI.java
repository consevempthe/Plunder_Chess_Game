package clientUI;

import gameLogic.Player.Color;

import javax.swing.*;

public class CheckUI {

	public CheckUI(Color colorInCheck, String white_player, String black_player) {
		Object[] options = { "Ok" };
		String message, title, playerInCheck, opponent;
		if(colorInCheck == Color.WHITE) {
			playerInCheck = white_player;
			opponent = black_player;
		} else {
			playerInCheck = black_player;
			opponent = white_player;
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