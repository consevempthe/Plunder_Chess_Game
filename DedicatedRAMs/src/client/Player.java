package client;

import java.util.ArrayList;

public class Player {
	/**
	 * From CRC Cards - 10/5
	 * Knows which color it is
	 * Has pieces and captured pieces
	 * Captures a piece
	 * Wins games
	 */
	public enum Color {WHITE, BLACK};
	public enum GameStatus {WIN, LOSE, DRAW, INPROGRESS};
	private Color color;
	private GameStatus gameStatus;
	private String nickname;
	private boolean turn;
	private ArrayList<ChessPiece> myPieces;
	private ArrayList<ChessPiece> capturedPieces;
	
	public Player(Player.Color c, Player.GameStatus g, String n, Boolean t) {
		this.color = c;
		this.gameStatus = g;
		this.nickname = n;
		this.turn = t;
		this.myPieces = new ArrayList<ChessPiece>();
		this.capturedPieces = new ArrayList<ChessPiece>();
	}
	
	public ArrayList<ChessPiece> getMyPieces(){
		return myPieces;
	}
	
	public ArrayList<ChessPiece> getCapturedPieces(){
		return capturedPieces;
	}
	
	public void addMyPieces(ChessPiece p) {
		myPieces.add(p);
	}
	
	public void captureOpponentPiece(ChessPiece p) {
		capturedPieces.add(p);
	}
	
	public void myPieceCaptured(ChessPiece p) {
		myPieces.remove(p);
	}

}
