package gameLogic;

import client.User;
import clientUI.GameEventHandlers;
import exceptions.IllegalMoveException;
import exceptions.IllegalPositionException;
import gameLogic.GameStatus.Status;
import gameLogic.Player.Color;

import java.util.ArrayList;
import java.util.Date;

/**
 * Game is a class that allows Users to play chess on a Chessboard with another
 * Player. It has a GameStatus, which tells whether it is in progress or not. A
 * gameID is based on the name given by the player but should be unique.
 * 
 * @author DedicatedRAMs
 *
 */
public class Game implements GameEventHandlers {

	private String gameID;
	private int turnCount = 0;
	private ChessBoard gameBoard;
	protected GameStatus gameStatus = new GameStatus();
	private User user;
	private Color playerColor;
	private Color currentPlayerColor;
	private Player white_player;
	private Player black_player;
	private ArrayList<GameEventHandlers> listeners = new ArrayList<>();

	public Game(String gameID, User user) {
		this.gameID = gameID;
		this.user = user;
		gameBoard = new ChessBoard();
		gameBoard.initialize();
		gameStatus.setStatus(Status.NOTSTARTED);
		this.gameBoard.addListener(this);
	}

	/**
	 * The plunder event that updates the UI when plunder happens on the back end.
	 *
	 * @param attackingPiece the attacking piece
	 * @param capturedPiece  the captured piece
	 */
	@Override
	public String plunderEvent(ChessPiece attackingPiece, ChessPiece capturedPiece) throws IllegalPositionException {
		// Notify everybody that may be interested.
		for (GameEventHandlers handle : listeners)
			handle.plunderEvent(attackingPiece, capturedPiece);
		return gameID;
	}

	/**
	 * Handles the CheckMate event and updates UI after a player has won.
	 * @param winningColor - the Color of player that won
	 */
	@Override
	public void checkMateEvent(Color winningColor) {
		// Notify everybody that may be interested.
		for (GameEventHandlers handle : listeners)
			handle.checkMateEvent(winningColor);
	}

	/**
	 * Handles a stalemate and updates the UI after the game logic has decided that neither player can move
	 */
	@Override
	public void drawEvent() {
		// Notify everybody that may be interested.
		for (GameEventHandlers handle : listeners)
			handle.drawEvent();
	}

	/**
	 * Handles checkmate by determining if a player is in check and tells the UI to notify the player and highlight the king
	 * which is done in GameUI
	 *
	 * @param checkedColor - the color that is in check
	 * @param white_player - nickname of white player
	 * @param black_player - nickname of black player
	 */
	@Override
	public void checkEvent(Color checkedColor, String white_player, String black_player) {
		for (GameEventHandlers handle : listeners) {
			handle.checkEvent(checkedColor, white_player, black_player);
		}
	}

	public void addListener(GameEventHandlers toAdd) {
		listeners.add(toAdd);
	}

	/**
	 * Current not being used but sets status to INPROGRESS and beings the timer
	 */
	public void startGame() {
		gameStatus.setStatus(Status.INPROGRESS);
		gameStatus.setStart(new Date(System.currentTimeMillis()));
	}

	/**
	 * The main method to moves the pieces for the GameUI to talk with the ChessBoard logic. This method is called when a user moves
	 * a piece on the GameUI and then calls the ChessBoard Class move method to move the pieces.
	 * @param currentPos - the current position of a piece being moved
	 * @param newPos - the new position that the currentPos piece is being moved too
	 * @param plunderOption - whether or not that piece is plundering another piece.
	 * @return - true if successful, false otherwise
	 */
	public boolean move(String currentPos, String newPos, String plunderOption) {
		System.out.println(listeners.size());
		try {
			gameBoard.move(currentPos, newPos, plunderOption);

			if (gameBoard.isCheckMate(Color.BLACK)) {
				this.checkMateEvent(Color.BLACK);
				if (this.getPlayerColor() == Color.BLACK) {
					gameStatus.setStatus(Status.WIN);
				}

				incrementTurn();
				return true;
			} else if (gameBoard.isCheckMate(Color.WHITE)) {
				this.checkMateEvent(Color.WHITE);
				if (this.getPlayerColor() == Color.WHITE) {
					gameStatus.setStatus(Status.WIN);
				}

				incrementTurn();
				return true;
			}

			if (gameBoard.isDraw(Color.BLACK) || gameBoard.isDraw(Color.WHITE)) {
				this.drawEvent();
				gameStatus.setStatus(Status.DRAW);
				incrementTurn();
				return true;
			}

			if(getPlayerColor() == Color.WHITE) {
				this.checkEvent(Color.WHITE, white_player.getNickname(), black_player.getNickname());
			} else {
				this.checkEvent(Color.BLACK, white_player.getNickname(), black_player.getNickname());
			}

			incrementTurn();
			return true;
		} catch (IllegalMoveException | IllegalPositionException e) {
			return false;
		}
	}

	/**
	 * Method to increment the turn - is called with the Game Class Move method. This allows tells the GameLogic whose turn
	 * it is
	 */
	public void incrementTurn() {
		turnCount++;
		if (currentPlayerColor.equals(white_player.getColor())) {
			currentPlayerColor = black_player.getColor();
		} else {
			currentPlayerColor = white_player.getColor();
		}

		gameBoard.setTurnWhite(turnCount % 2 == 0);
	}

	/**
	 * Method called by GameUI to determine if a piece can plunder another piece
	 * @param attacker - the piece attacking
	 * @param captured - the piece captured
	 * @return - true if the piece can plunder, false otherwise.
	 */
	public boolean canPlunder(ChessPiece attacker, ChessPiece captured) {
		return gameBoard.isPlunderable(attacker, captured);
	}

	/**
	 * For GameUI to determine if it is check
	 * @param color - the color being checked for check state
	 * @return - true if it is Check, and false otherwise
	 */
	public boolean isCheck(Color color) {
		return gameBoard.isCheck(color);
	}

	/**
	 * For GameUI to determine pawn promotion
	 * @param position - the position of the piece
	 */
	public void tryPawnPromotion(String position) {
		gameBoard.tryPawnPromote(position);
	}

	/**
	 * get a piece from the chessboard based on row and col
	 * @param row - the row
	 * @param col - the col
	 * @return - the ChessPiece at that position.
	 */
	public ChessPiece getPieceAtLocation(int row, int col) {
		return gameBoard.getPiece(row, col);
	}

	/**
	 * get a piece based on the alphanumeric string position (i.e. "a1)
	 * @param position - string position
	 * @return - the ChessPiece at that Position.
	 */
	public ChessPiece getPieceByPosition(String position) {
		int col = position.charAt(0) - 'a';
		int row = position.charAt(1) - '1';
		return gameBoard.getPiece(row, col);
	}

	/**
	 * - Used by clientUI when sending moves.
	 * @return - the ID for the game
	 */
	public String getGameID() {
		return gameID;
	}

	/**
	 * Getter method: returns the color being played on this client
	 * 
	 * @return Color
	 */
	public Color getPlayerColor() {
		return playerColor;
	}

	/**
	 * Setter method: sets a Player by index
	 * 
	 * @param w - Player going first
	 * @param b - Player going second
	 */
	public void setPlayers(Player w, Player b) {
		this.white_player = w;
		this.black_player = b;
		if (this.user.getNickname().equals(this.white_player.getNickname())) {
			this.playerColor = Color.WHITE;
		} else
			this.playerColor = Color.BLACK;
		currentPlayerColor = Color.WHITE;
	}
	
	public Player getWhitePlayer()
	{
		return this.white_player;
	}
	
	public Player getBlackPlayer()
	{
		return this.black_player;
	}

	/**
	 * Getter method: returns the status information of the Game.
	 * 
	 * @return GameStatus object with information about the Game.
	 */
	public GameStatus getGameStatus() {
		return gameStatus;
	}

	/**
	 * Getter method: returns the number of turns counted in the Game.
	 * 
	 * @return Integer of Game turns.
	 */
	public int getTurnCount() {
		return turnCount;
	}

	/**
	 * getOpponent() returns the nickname of the opponent for use in requests
	 *
	 * @return - nickname of opponent.
	 */
	public String getOpponent() {
		if (!this.user.getNickname().equals(this.white_player.getNickname())) {
			return white_player.getNickname();
		} else
			return black_player.getNickname();
	}

	/**
	 * isPlayersTurn() decides whether a it is a players turn based on color they
	 * are playing as in this game.
	 *
	 * @return returns true if it is the player's turn
	 */
	public boolean isPlayersTurn() {
		return currentPlayerColor.equals(playerColor);
	}

	/**
	 * gets the player color
	 * @return - the color of the current player.
	 */
	public Color getCurrentPlayerColor() {
		return currentPlayerColor;
	}
}
