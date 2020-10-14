package client;

public class Game {
	
	private ChessBoard gameBoard;
	
	public Game() {
		gameBoard = new ChessBoard();
		gameBoard.initialize();
	}
		
	public ChessBoard getGameBoard() {
		return gameBoard;
	}

}
