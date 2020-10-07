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
	public ArrayList<String> legalMoves() {
		String position = getPosition();
		ArrayList<String> moves = new ArrayList<String>();
		char col = position.charAt(0);
		char row = position.charAt(1);
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if(i != 0 || j != 0) {
					String positionToTest = (char)(col + i) + "" + (char)(row + j);
					if(isPositionTakable(positionToTest)) {
						moves.add(positionToTest);
					}
				}
			}
		}
		
		// include the vest moves if it exists
		if (this.vest != null) {
			moves.addAll(this.vest.getPiece().legalMoves());
		}
		
		return moves;
	}
	
	private boolean isPositionTakable(String position) {
		if(!board.isPositionOnBoard(position))
			return false;
		try {
			ChessPiece piece = board.getPiece(position);
			if(piece != null && piece.color.equals(this.color))
				return false;
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
