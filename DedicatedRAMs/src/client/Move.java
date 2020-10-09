package client;
/*******
 * Move class is a class containing the information for a single move on a ChessBoard. This includes the piece moved, the starting position of the piece, and the ending position of the piece after the move is complete.
 * @author DedicatedRAMs Team
 */
public class Move {
	private ChessPiece piece;
	private String from;
	private String to;
	
	/****
	 * Move constructor that takes initializes the attributes of the Move class, piece, from, and to.
	 * @param piece - The ChessPiece moved.
	 * @param from - The starting location.
	 * @param to - The ending location.
	 */
	public Move(ChessPiece piece, String from, String to) {
		this.piece = piece;
		this.from = from;
		this.to = to;
	}
	
	/**
	 * getPiece() is the getter for piece, the ChessPiece moved.
	 * @return ChessPiece
	 */
	public ChessPiece getPiece() {
		return piece;
	}
	
	/**
	 * getFrom() is the getter for from, the location the piece started on in the move.
	 * @return String
	 */
	public String getFrom() {
		return from;
	}
	
	/**
	 * getTo() is the getter for to, the location the piece ended up on in the move.
	 * @return String
	 */
	public String getTo() {
		return to;
	}

}
