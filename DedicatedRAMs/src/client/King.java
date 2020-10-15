package client;

import java.util.ArrayList;


public class King extends ChessPiece {
	
	public King(ChessBoard board, Color color) {
		super(board, color);
		this.vestTypes.add(Rook.class);
		this.vestTypes.add(Knight.class);
		this.vestTypes.add(Bishop.class);
		this.vestTypes.add(Queen.class);
		this.vestTypes.add(Pawn.class);
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
		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		ArrayList<String> moves = new ArrayList<>(movement.kingCircleOfMoves());

		try {
			if(!this.hasMoved) {
				moves.addAll(movement.kingCastles());
			}
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}

		if (includeVest && this.vest != null) {
			moves.addAll(this.vest.getType().legalMoves(false, false));
		}

		if(turn) {
			moves = illegalMovesDueToCheck(moves);
		}
		
		return moves;
	}
	
}
