package client;

import java.util.ArrayList;

public class Bishop extends ChessPiece {

	public Bishop(ChessBoard board, Color color) {
		super(board, color);
		this.plunderableTypes.add(Rook.class);
		this.plunderableTypes.add(Pawn.class);
		this.plunderableTypes.add(Queen.class);
		this.plunderableTypes.add(Knight.class);
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
	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {
		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		ArrayList<String> moves = new ArrayList<>(movement.longRangeMoves("Diagonal"));
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
