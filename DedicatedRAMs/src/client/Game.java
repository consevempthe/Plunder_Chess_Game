package client;

import client.GameStatus.Status;
import clientUI.GameEventHandlers;
import client.Player.Color;

import java.util.ArrayList;
import java.util.Date;

/**
 * Game is a class that allows Users to play chess on a Chessboard with another Player. It has a GameStatus, which tells whether it is in progress or not. A gameID is based on the name given by the player but should be unique.
 * @author DedicatedRAMs
 *
 */
public class Game implements GameEventHandlers {
	
	private String gameID; 
	private int turnCount = 0;
	private ChessBoard gameBoard;
	protected GameStatus gameStatus = new GameStatus();
	User user;
	Player currentPlayer;
	Player white_player;
	Player black_player;
	private ArrayList<GameEventHandlers> listeners = new ArrayList<GameEventHandlers>();
	public enum inviteStatus {
		SENT, ACCEPTED
	}
	
	public Game(String gameID, User user) {
		this.gameID = gameID;
		this.user = user;
		gameBoard = new ChessBoard(System.in);
		gameBoard.initialize();
		gameStatus.setStatus(Status.NOTSTARTED);
		this.gameBoard.addListener(this);
	}
	
	@Override
	public void plunderEvent(ChessPiece attackingPiece, ChessPiece capturedPiece) throws IllegalPositionException {
		  // Notify everybody that may be interested.
        for (GameEventHandlers handle : listeners)
            handle.plunderEvent(attackingPiece, capturedPiece);
	}
	
	 public void addListener(GameEventHandlers toAdd) {
	        listeners.add(toAdd);
	 }

	
	public void startGame() {
		gameStatus.setStatus(Status.INPROGRESS);
		gameStatus.setStart(new Date(System.currentTimeMillis()));
	}
	
	public boolean move(String from, String to) {
		try {
			gameBoard.move(from, to);
		} catch (IllegalMoveException | IllegalPositionException e) {
			e.printStackTrace();
			return false;
		}
		incrementTurn();
		return true;
	}
	
	public void incrementTurn() {
		turnCount++;
		if(currentPlayer == white_player) {
			currentPlayer = black_player;
		} else {
			currentPlayer = white_player;
		}

		gameBoard.setTurnWhite(turnCount%2 == 0);
	}
		
	public ChessBoard getGameBoard() {
		return gameBoard;
	}

	public ChessPiece getPieceAtLocation(int row, int col) {
		return gameBoard.getPiece(row, col);
	}

	public ChessPiece getPieceByPosition(String position) {
		int col = position.charAt(0) - 'a';
		int row = position.charAt(1) - '1';
		return gameBoard.getPiece(row, col);
	}

	public String getGameID() {
		return gameID;
	}
	/**
	 * Setter method: sets the Game's ID.
	 * @param id - sets the game ID
	 */
	public void setGameId(String id) {
		this.gameID = id;
	}
	/**
	 * Getter method: returns array of Players associated with this Game.
	 * @return Array of two Players.
	 */
	public Color getCurrentPlayerColor() {
		return this.currentPlayer.getColor();
	}
	/**
	 * Setter method: sets a Player by index
	 * @param w - Player going first
	 * @param b - Player going second
	 */
	public void setPlayers(Player w, Player b) {
		this.white_player = w;
		this.black_player = b;
		this.currentPlayer = this.white_player;
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
