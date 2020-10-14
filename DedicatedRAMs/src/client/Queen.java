package client;

import java.util.ArrayList;

public class Queen extends ChessPiece {

	public Queen(ChessBoard board, Color color) {
		super(board, color);
		this.plunderableTypes.add(Knight.class);
		this.plunderableTypes.add(Pawn.class); //for the possibility of future en passant
	}

	@Override
	public String toString() {
		if (color.equals(Color.WHITE))
			return "\u2655";
		else
			return "\u265B";
	}

	@Override
	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {
		ArrayList<String> moves = new ArrayList<String>();
		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		moves.addAll(movement.longRangeMoves("Straight"));
		moves.addAll(movement.longRangeMoves("Diagonal"));
		// include the vest moves if it exists
		if (includeVest && this.vest != null) {
			moves.addAll(this.vest.getType().legalMoves(false, false));
		}

		if(turn) {
			ArrayList<String> removeMoves = illegalMovesDueToCheck(moves);
			moves.removeAll(removeMoves);
		}
		
		return moves;
	}

}
