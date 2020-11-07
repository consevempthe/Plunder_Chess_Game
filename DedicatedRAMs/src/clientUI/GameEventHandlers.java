package clientUI;

import client.ChessPiece;
import client.IllegalPositionException;
import client.Player.*;

/**
 * The game event handlers (used to update the UI when the back end game logic updates)
 */
public interface GameEventHandlers {
	
	/**
	 * The plunder event that updates the UI when plunder happens on the back end.
	 * 
	 * @param attackingPiece the attacking piece
	 * @param capturedPiece the captured piece
	 */
	void plunderEvent(ChessPiece attackingPiece, ChessPiece capturedPiece) throws IllegalPositionException;
	
	void checkMateEvent(Color winningColor);
	
	void drawEvent();
	
	void checkEvent(Color checkedColor, String white_player, String black_player);
}
