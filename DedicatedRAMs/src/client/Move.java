package client;
/*******
 * Move class is a class containing the information for a single move on a ChessBoard. This includes the piece moved, the starting position of the piece, and the ending position of the piece after the move is complete.
 * @author DedicatedRAMs Team
 */
public class Move {
	private ChessPiece pieceMoved;
	private String currentPos;
	private String newPos;
	private ChessPiece captured;
	
	/****
	 * Move constructor that takes initializes the attributes of the Move class, piece, from, and to.
	 * @param pieceMoved - The ChessPiece moved.
	 * @param currentPos - The starting location.
	 * @param newPos - The ending location.
	 */
	public Move(ChessPiece pieceMoved, String currentPos, String newPos, ChessPiece captured) {
		this.pieceMoved = pieceMoved;
		this.currentPos = currentPos;
		this.newPos = newPos;
	}
	
	/**
	 * getPiece() is the getter for piece, the ChessPiece moved.
	 * @return ChessPiece
	 */
	public ChessPiece getPieceMoved() {
		return pieceMoved;
	}
	
	/**
	 * getFrom() is the getter for from, the location the piece started on in the move.
	 * @return String
	 */
	public String getCurrentPos() {
		return currentPos;
	}
	
	/**
	 * getTo() is the getter for to, the location the piece ended up on in the move.
	 * @return String
	 */
	public String getNewPos() {
		return newPos;
	}

	public ChessPiece getCaptured() {
		return captured;
	}

	public void setCaptured(ChessPiece captured) {
		this.captured = captured;
	}
}
