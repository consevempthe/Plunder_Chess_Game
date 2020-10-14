package client;

import java.util.ArrayList;

public class Rook extends ChessPiece {

	public Rook(ChessBoard board, Color color) {
		super(board, color);
		this.plunderableTypes.add(Pawn.class);
		this.plunderableTypes.add(Bishop.class);
		this.plunderableTypes.add(Queen.class);
		this.plunderableTypes.add(Pawn.class);
	}

	@Override
	public String toString() {
		if (this.color == ChessPiece.Color.WHITE) {
			return "\u2656";
		} else {
			return "\u265C";
		}
	}

	@Override
	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {
		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		ArrayList<String> moves = new ArrayList<>(movement.longRangeMoves("Straight"));
		// include the vest moves if it exists
		if(includeVest && this.vest != null)
		{
			moves.addAll(this.vest.getType().legalMoves(false, false));
		}
		
		if(turn) {
			ArrayList<String> removeMoves = illegalMovesDueToCheck(moves);
			moves.removeAll(removeMoves);
		}
		
		return moves;
	}
}
