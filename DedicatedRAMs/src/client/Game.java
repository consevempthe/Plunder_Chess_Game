package client;

public class Game {
	
	private ChessBoard gameBoard;
	
	public Game() {
		gameBoard.initialize();
	}
		
	public ChessBoard getGameBoard() {
		return gameBoard;
	}

}
