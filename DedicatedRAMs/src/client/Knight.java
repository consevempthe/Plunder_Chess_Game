package client;

import java.util.ArrayList;

public class Knight extends ChessPiece {

	public Knight(ChessBoard board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return this.getColor() == Color.WHITE ? "\u2658" : "\u265E";
	}

	@Override
	public ArrayList<String> legalMoves() {
		ArrayList<String> moves = new ArrayList<>();

		// this is the possible moves that a Knight can make
		int[][] moveSet = { { 1, -2 }, { 2, -1 }, { 2, 1 }, { 1, 2 }, { -1, -2 }, { -2, -1 }, { -2, 1 }, { -1, 2 } };

		// loop thru the moveSet by adding the moveSet to the pieces position and make
		// sure that it is a legal move.
		for (int[] m : moveSet) {
			char row = (char) ('1' + this.row + m[0]);
			char col = (char) ('a' + this.column + m[1]);

			String newPosition = col + "" + row;
			/*
			 * this loop determines if a position is legal, legality is assuring that the
			 * space is either null/empty or the space is occupied by an enemy piece.
			 */
			try {
				if (this.board.isPositionOnBoard(newPosition)) {

					if (this.board.getPiece(newPosition) != null) {
						if (this.board.getPiece(newPosition).getColor() != this.getColor())
							moves.add(newPosition);
					} else {
						moves.add(newPosition);
					}
				}
			} catch (IllegalPositionException e) {
				e.printStackTrace();
			}

		}

		// include the vest moves if it exists
		if (this.vest != null) {
			moves.addAll(this.vest.getPiece().legalMoves());
		}

		return moves;
	}
}
