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
		 ArrayList<String> moves = new ArrayList<String>();
		 
		 PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		 moves.addAll(movement.knightJumpMove());
//	        int[][] moveSet = { {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2} };
//	        for (int[] move : moveSet) {
//	            char row = (char) ('1' + this.row + move[0]);
//	            char col = (char) ('a' + this.column + move[1]);
//	            String newPosition = col + "" + row;
//	            if(isPositionTakable(newPosition)) {
//	            	moves.add(newPosition);
//			}
//
//		}
		// include the vest moves if it exists
		if (this.vest != null) {
			moves.addAll(this.vest.getPiece().legalMoves());
		}
		return moves;
	}
}
