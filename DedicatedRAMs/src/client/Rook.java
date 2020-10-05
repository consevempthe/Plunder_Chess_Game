package client;

import java.util.ArrayList;

public class Rook extends ChessPiece {

	public Rook(ChessBoard board, Color color) {
		super(board, color);
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
	public ArrayList<String> legalMoves() {
		
		ArrayList<String> moves = new ArrayList<String>();
		
		// This for loop checks for valid horizontal movement in the positive x direction
		for(int i = this.column + 1; i < 8; i++) {
			if(!this.addMove(i, this.row, moves)) {
				break;
			}
		}
		
		// This for loop checks for valid horizontal movement in the negative x direction
		for(int i = this.column - 1; i >= 0; i--) {
			if(!this.addMove(i, this.row, moves)) {
				break;
			}
		}
		
		// This for loop checks for valid vertical movement in the positive y direction 
		for(int i = this.row + 1; i < 8; i++) {
			if(!this.addMove(this.column, i, moves)) {
				break;
			}
		}
		
		// This for loop checks for valid vertical movement in the negative y direction
		for(int i = this.row - 1; i >= 0; i--) {
			if(!this.addMove(this.column, i, moves)) {
				break;
			}
		}
		
		// when vests are added, the logic for additional movement capabilities can go here.
		
		return moves;
	}
	
	private boolean addMove (int column, int row, ArrayList<String> listRef) {
		
		String newPosition = "";
		ChessPiece otherPiece = null;
		
		// get new position in terms of a1-h8
		newPosition = (char)('a' + column) + "" + (char)('1' + row);
		
		if(this.board.isPositionOnBoard(newPosition)) {
			try {
				otherPiece = this.board.getPiece(newPosition);
			} catch (IllegalPositionException e) {
				e.printStackTrace();
			}
			if(otherPiece == null) {
				listRef.add(newPosition);
				return true;
			}
			if(otherPiece.color == this.color) {
				return false;
			}
			if(otherPiece.color != this.color) {
				listRef.add(newPosition);
				return false;
			}
		}
		
		return false;
		
	}

}
