package client;

import java.util.ArrayList;

public class Bishop extends ChessPiece {

	public Bishop(ChessBoard board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		if (this.color == Color.WHITE) {
			return "\u2657";
		} else {
			return "\u265D";
		}
	}

	@Override
	public ArrayList<String> legalMoves() {

		ArrayList<String> moves = new ArrayList<String>();
		Queen queen = new Queen(this.board, this.color);
		try {
			queen.setPosition(this.getPosition());
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		queen.addMoves(moves, "Diagonal");

		// include the vest moves if it exists
		if (this.vest != null) {
			moves.addAll(this.vest.getPiece().legalMoves());
		}

		return moves;
	}

}
