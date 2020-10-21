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
	private Player[] players = new Player[2];
	public enum inviteStatus {
		SENT, ACCEPTED
	}
	
	
	public Game(String gameID, User user) {
		this.gameID = gameID;
		this.user = user;
		gameBoard = new ChessBoard(System.in);
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
		} catch (IllegalMoveException | IllegalPositionException e) {
			return false;
		}
		incrementTurn();
		return true;
	}
	
	public void incrementTurn() {
		turnCount++;
		gameBoard.setTurnWhite(turnCount%2 == 0);
	}
		
	public ChessBoard getGameBoard() {
		return gameBoard;
	}

	public String getGameID() {
		return gameID;
	}
	/**
	 * Setter method: sets the Game's ID.
	 * @param id
	 */
	public void setGameId(String id) {
		this.gameID = id;
	}
	/**
	 * Getter method: returns array of Players associated with this Game.
	 * @return Array of two Players.
	 */
	public Player[] getPlayers() {
		return players;
	}
	/**
	 * Setter method: sets a Player by index
	 * @param p - Player object
	 * @param i - Index of player array that should be set.
	 */
	public void setPlayers(Player p, int i) {
		players[i] = p;
	}
	/**
	 * Getter method: returns the status information of the Game.
	 * @return GameStatus object with information about the Game.
	 */
	public GameStatus getGameStatus() {
		return gameStatus;
	}
	/**
	 * Getter method: returns the number of turns counted in the Game.
	 * @return Integer of Game turns.
	 */
	public int getTurnCount() {
		return turnCount;
	}

}
