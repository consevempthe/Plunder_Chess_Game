package client;

import java.util.ArrayList;

public class Knight extends ChessPiece {

	public Knight(ChessBoard board, Color color) {
		super(board, color);
		this.plunderableTypes.add(Rook.class);
		this.plunderableTypes.add(Bishop.class);
		this.plunderableTypes.add(Queen.class);
		this.plunderableTypes.add(Pawn.class);
	}

	@Override
	public String toString() {
		return this.getColor() == Color.WHITE ? "\u2658" : "\u265E";
	}

	@Override
	public ArrayList<String> legalMoves(boolean includeVest) {
		 ArrayList<String> moves = new ArrayList<String>();
		 
		 PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		 moves.addAll(movement.knightJumpMove());
		// include the vest moves if it exists
		if (includeVest && this.vest != null) {
			moves.addAll(this.vest.getPiece().legalMoves(false));
		}
		return moves;
	}
}
