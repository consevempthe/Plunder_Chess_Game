package clientUI;

import client.ChessPiece;
import client.IllegalPositionException;

public interface GameEventHandlers {
	void plunderEvent(ChessPiece attackingPiece, ChessPiece capturedPiece) throws IllegalPositionException;
}
