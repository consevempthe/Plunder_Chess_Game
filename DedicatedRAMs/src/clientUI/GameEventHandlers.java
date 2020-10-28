package clientUI;

import client.ChessPiece;
import client.IllegalPositionException;

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
	
	//TODO add the other events (castling, enpassent, check, stalemate, ect) here
}
