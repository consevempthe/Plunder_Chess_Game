package client;

import java.util.ArrayList;


public class King extends ChessPiece {

	protected boolean turnToCheck;
	public King(ChessBoard board, Color color) {
		super(board, color);
		this.plunderableTypes.add(Rook.class);
		this.plunderableTypes.add(Knight.class);
		this.plunderableTypes.add(Bishop.class);
		this.plunderableTypes.add(Queen.class);
		this.plunderableTypes.add(Pawn.class);
		if(color == Color.WHITE)
			turnToCheck = true;
	}

	@Override
	public String toString() {
		if(color.equals(Color.WHITE))
			return "\u2654";
		else
			return "\u265A";
	}

	@Override
	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {
		ArrayList<String> moves = new ArrayList<String>();
		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		moves.addAll(movement.kingCircleOfMoves());
		
		
		
		if (includeVest && this.vest != null) {
			moves.addAll(this.vest.getPiece().legalMoves(false, false));
		}
		if(turn) {
			System.out.println("Time Before: " + System.currentTimeMillis());
			ArrayList<String> removeMoves = illegalMovesDueToCheck(moves);
			moves.removeAll(removeMoves);
			System.out.println("Time After: " + System.currentTimeMillis());
		}
		
		return moves;
	}
	
}
