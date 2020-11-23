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
	Color playerColor;
	Color currentPlayerColor;
	Player white_player;
	Player black_player;
	private ArrayList<GameEventHandlers> listeners = new ArrayList<>();

	public enum inviteStatus {
		SENT, ACCEPTED
	}

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

	@Override
	public void checkMateEvent(Color winningColor) {
		// Notify everybody that may be interested.
		for (GameEventHandlers handle : listeners)
			handle.checkMateEvent(winningColor);
	}

	@Override
	public void drawEvent() {
		// Notify everybody that may be interested.
		for (GameEventHandlers handle : listeners)
			handle.drawEvent();
	}

	public void addListener(GameEventHandlers toAdd) {
		listeners.add(toAdd);
	}

	public void startGame() {
		gameStatus.setStatus(Status.INPROGRESS);
		gameStatus.setStart(new Date(System.currentTimeMillis()));
	}

	/**
	 * Returns the positions of the Rook
	 * 
	 * @param king - the king
	 * @return - array with the rook current position and its new position
	 */
	public String[] moveCastling(King king) {
		return king.getRookCastlingPositions();
	}

	/**
	 * Returns the position to capture a piece after an enPassant move.
	 * 
	 * @param pawn   - the pawn moving
	 * @param newPos - the position the pawn is moving to
	 * @return - String position of the Piece the pawn is capturing.
	 */
	public String moveEnPassant(ChessPiece pawn, String newPos) {
		String pawnCapture;

		if (pawn.getColor().equals(Color.WHITE)) {
			pawnCapture = (newPos.charAt(0)) + "" + (char) (-1 + newPos.charAt(1));
		} else {
			pawnCapture = (newPos.charAt(0)) + "" + (char) (1 + newPos.charAt(1));
		}

		return pawnCapture;
	}

	@Override
	public void checkEvent(Color checkedColor, String white_player, String black_player) {
		for (GameEventHandlers handle : listeners) {
			handle.checkEvent(checkedColor, white_player, black_player);
		}
	}

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

			if (gameBoard.isCheck(Player.Color.WHITE) && getPlayerColor() == Player.Color.WHITE) {
				this.checkEvent(Color.WHITE, white_player.getNickname(), black_player.getNickname());

			} else if (gameBoard.isCheck(Player.Color.BLACK) && getPlayerColor() == Player.Color.BLACK) {
				this.checkEvent(Color.BLACK, white_player.getNickname(), black_player.getNickname());

			}
			
			incrementTurn();
			return true;
		} catch (IllegalMoveException | IllegalPositionException e) {
			return false;
		}
	}

	public void incrementTurn() {
		turnCount++;
		if (currentPlayerColor.equals(white_player.getColor())) {
			currentPlayerColor = black_player.getColor();
		} else {
			currentPlayerColor = white_player.getColor();
		}

		gameBoard.setTurnWhite(turnCount % 2 == 0);
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
	 * 
	 * @param id - sets the game ID
	 */
	public void setGameId(String id) {
		this.gameID = id;
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

	public Color getCurrentPlayerColor() {
		return currentPlayerColor;
	}
}
