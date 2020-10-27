package client;

import client.Player.Color;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Knight extends ChessPiece {

	public Knight(ChessBoard board, Color color) {
		super(board, color);
		this.vestTypes.add(Rook.class);
		this.vestTypes.add(Bishop.class);
		this.vestTypes.add(Queen.class);
		this.vestTypes.add(Pawn.class);
	}

	@Override
	public String toString() {
		return this.getColor() == Color.WHITE ? "\u2658" : "\u265E";
	}
	
	@Override
	public ImageIcon toImage() {
		ImageIcon icon = this.getColor() == Color.WHITE ? new ImageIcon(getClass().getResource("/images/whiteKnight.png"))
				: new ImageIcon(getClass().getResource("/images/blackKnight.png"));
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);
	}
	
	@Override
	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {

		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		ArrayList<String> moves = new ArrayList<>(movement.knightJumpMove());
		// include the vest moves if it exists
		if (includeVest && this.vest != null) {
			moves.addAll(this.vest.getType().legalMoves(false, false));
		}
		if(turn) {
			moves = illegalMovesDueToCheck(moves);

		}
		
		return moves;
	}
}
