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
					if(isPositionTakable(positionToTest)) { //Must add "check" condition to not add a move that would give "check".
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
	
}
