package client;

import java.util.ArrayList;

public class Rook extends ChessPiece {

	public Rook(ChessBoard board, Color color) {
		super(board, color);
		this.vestTypes.add(Pawn.class);
		this.vestTypes.add(Bishop.class);
		this.vestTypes.add(Queen.class);
		this.vestTypes.add(Pawn.class);
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
			moves = illegalMovesDueToCheck(moves);

		}
		
		return moves;
	}
}
