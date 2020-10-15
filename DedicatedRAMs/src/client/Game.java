package client;

import java.util.Date;
import client.GameStatus.Status;

/**
 * Game is a class that allows Users to play chess on a Chessboard with another Player. It has a GameStatus, which tells whether it is in progress or not. A gameID is based on the name given by the player but should be unique.
 * @author DedicatedRAMs
 *
 */
public class Game {
	
	private String gameID; 
	private int turnCount = 0;
	private ChessBoard gameBoard;
	protected GameStatus gameStatus = new GameStatus();
	User user;
	Player player1;
	Player player2;
	
	
	public Game(String gameID, User user) {
		this.gameID = gameID;
		this.user = user;
		gameBoard = new ChessBoard();
		gameBoard.initialize();
		gameStatus.setStatus(Status.NOTSTARTED);
	}
	
	public void startGame() {
		gameStatus.setStatus(Status.INPROGRESS);
		gameStatus.setStart(new Date(System.currentTimeMillis()));
	}
	
	public boolean move(String from, String to) {
		try {
			gameBoard.move(from, to);
		} catch (IllegalMoveException e) {
			return false;
		} catch (IllegalPositionException e) {
			return false;
		}
		return true;
	}
	
	public void incrementTurn() {
		turnCount++;
	}
		
	public ChessBoard getGameBoard() {
		return gameBoard;
	}

	public String getGameID() {
		return gameID;
	}

}
