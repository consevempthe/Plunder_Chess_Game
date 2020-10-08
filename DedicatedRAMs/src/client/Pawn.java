package client;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

	public Pawn(ChessBoard board, Color color) {
		super(board, color);
	}

	public String toString() {
		return this.getColor() == Color.WHITE ? "\u2659" : "\u265F";
	}

	public ArrayList<String> legalMoves() {
		ArrayList<String> moves = new ArrayList<>();
		
		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		String move = movement.pawnPlusOne();
		if(move != null)
			moves.add(move);
		move = movement.pawnPlusTwo();
		if(move != null)
			moves.add(move);
		moves.addAll(movement.pawnCapture());

		if (this.vest != null) {
			moves.addAll(this.vest.getPiece().legalMoves());
		}

		return moves;
	}
}
