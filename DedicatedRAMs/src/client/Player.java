package client;

import java.util.ArrayList;

public class Player {
	/**
	 * From CRC Cards - 10/5 Knows which color it is Has pieces and captured pieces
	 * Captures a piece Wins games
	 */
	public enum Color {
		WHITE, BLACK
	}

	public enum GameStatus {
		WIN, LOSE, DRAW, INPROGRESS
	}

	private Color color;
	private GameStatus gameStatus;
	private String nickname;
	private boolean turn;
	private ArrayList<ChessPiece> pieces;
	private ArrayList<ChessPiece> capturedPieces;

	public Player(Player.Color c, Player.GameStatus g, String n, Boolean t) {
		this.color = c;
		this.gameStatus = g;
		this.nickname = n;
		this.turn = t;
		this.pieces = new ArrayList<>();
		this.capturedPieces = new ArrayList<>();
	}

	/**
	 * Pieces remaining for the Player.
	 * 
	 * @return List of Player's remaining ChessPieces.
	 */

	public ArrayList<ChessPiece> getPieces() {
		return pieces;
	}

	/**
	 * Pieces the Player has captured.
	 * 
	 * @return List of captured ChessPieces.
	 */

	public ArrayList<ChessPiece> getCapturedPieces() {
		return capturedPieces;
	}

	public void addPieces(ChessPiece p) {
		pieces.add(p);
	}

	public void removePieces(ChessPiece p) {
		pieces.remove(p);
	}

	/**
	 * Add ChessPiece to Player's capturedPieces List.
	 * @param p The captured ChessPiece
	 */

	public void captureOpponentPiece(ChessPiece p) {
		capturedPieces.add(p);
	}

}
