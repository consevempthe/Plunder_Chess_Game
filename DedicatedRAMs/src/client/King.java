package client;

import java.util.ArrayList;


public class King extends ChessPiece {

	public King(ChessBoard board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		if(color.equals(Color.WHITE))
			return "\u2654";
		else
			return "\u265A";
	}

	@Override
	public ArrayList<String> legalMoves(boolean includeVest) {
		ArrayList<String> moves = new ArrayList<String>();
		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		moves.addAll(movement.kingCircleOfMoves());
		if (includeVest && this.vest != null) {
			moves.addAll(this.vest.getPiece().legalMoves(false));
		}
		return moves;
	}
	
}
