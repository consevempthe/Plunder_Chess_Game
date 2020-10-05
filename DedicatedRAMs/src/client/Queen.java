package client;

import java.util.ArrayList;

public class Queen extends ChessPiece {

	public Queen(ChessBoard board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		if(color.equals(Color.WHITE))
			return "\u2655";
		else
			return "\u265B";	
	}

	@Override
	public ArrayList<String> legalMoves() {
		ArrayList<String> moves = new ArrayList<String>();
		addMoves(moves, "Straight");
		addMoves(moves, "Diagonal");
		return moves;
	}
	
	public ArrayList<String> addMoves(ArrayList<String> moves, String type){
		int colA = 1, colB = 0, rowA = 1, rowB = 0;
		if(type.equals("Diagonal")) {
			colB = 1; rowB = 1;
		}
		boolean c1 = true,c2 = true,r1 = true,r2 = true;
		String position = getPosition();
		char col = position.charAt(0);
		char row = position.charAt(1);
		while(c1 || c2 || r1 || r2) {
			if(c1)
				c1 = performMoveAddition(moves, (char)(col + colA) + "" + (char)(row - rowB));
			if(c2)
				c2 = performMoveAddition(moves, (char)(col - colA) + "" + (char)(row + rowB));
			if(r1)
				r1 = performMoveAddition(moves, (char)(col + colB) + "" + (char)(row + rowA));
			if(r2)
				r2 = performMoveAddition(moves, (char)(col - colB) + "" + (char)(row - rowA));
			colA++;
			rowA++;
			if(type.equals("Diagonal")) {
				colB++; rowB++;
			}
		}
		return moves;
	}
	
	private boolean performMoveAddition(ArrayList<String> moves, String position) {
		if(!board.isPositionOnBoard(position))
			return false;
		try {
			if(board.getPiece(position) != null && board.getPiece(position).color == this.color)
				return false;
			moves.add(position);
			if(board.getPiece(position) != null && board.getPiece(position).color != this.color)
				return false;
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		return true;
	}
	

}

