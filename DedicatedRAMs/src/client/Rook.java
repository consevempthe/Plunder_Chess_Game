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
		// TODO Auto-generated method stub
		return null;
	}

}
