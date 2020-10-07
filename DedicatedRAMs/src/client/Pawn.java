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

		int movement = this.getColor() == Color.WHITE ? 1 : -1;
		int initial_start = this.getColor() == Color.WHITE ? 1 : 6;

		int[][] moveSet = { { movement, 0 }, { movement, 1 }, { movement, -1 } };

		for (int[] m : moveSet) {
			int r = this.row + m[0]; // r and c hold the column and row integers - for checking if pawn can do
										// certain moves
			int c = this.column + m[1];
			char row = (char) ('1' + r);
			char col = (char) ('a' + c);

			String possible_move = col + "" + row;

			try {
				if (this.board.isPositionOnBoard(possible_move)) {
					if (this.board.getPiece(possible_move) != null) { // first check makes sure that the pawn is only
																		// capturing enemy pieces in another column
						if (this.board.getPiece(possible_move).getColor() != this.getColor() && c != this.column) {
							moves.add(possible_move);
						}
					} else {
						if (c == this.column) { // this check makes sure the piece can do the double move
							if (this.row == initial_start) {
								char add_one_row = (char) ('1' + (r + movement));
								String twice = col + "" + add_one_row;
								if (this.board.getPiece(twice) == null) {
									moves.add(twice);
								}
							}

							moves.add(possible_move);
						}
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
