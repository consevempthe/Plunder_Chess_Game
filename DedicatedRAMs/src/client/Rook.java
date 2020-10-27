package client;

import client.Player.Color;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class Rook extends ChessPiece {

	public Rook(ChessBoard board, Color color) {
		super(board, color);
		this.vestTypes.add(Pawn.class);
		this.vestTypes.add(Bishop.class);
		this.vestTypes.add(Queen.class);
		this.vestTypes.add(Pawn.class);
	}

	@Override
	public String toString() {
		if (this.color == Color.WHITE) {
			return "\u2656";
		} else {
			return "\u265C";
		}
	}
	
	@Override
	public ImageIcon toImage() {
		URL test = getClass().getResource("/images/blackRook.png");
		ImageIcon icon = this.getColor() == Color.WHITE ? new ImageIcon(getClass().getResource("/images/whiteRook.png"))
				: new ImageIcon(getClass().getResource("/images/blackRook.png"));
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);
	}

	@Override
	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {
		PieceMovement movement = new PieceMovement(board.getHistory(), board, this);
		ArrayList<String> moves = new ArrayList<>(movement.longRangeMoves("Straight"));
		// include the vest moves if it exists
		if(includeVest && this.vest != null)
		{
			moves.addAll(this.vest.getType().legalMoves(false, false));
		}
		
		if(turn) {
			moves = illegalMovesDueToCheck(moves);

		}
		
		return moves;
	}
}
