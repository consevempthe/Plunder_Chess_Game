package gameLogic;

import gameLogic.Player.Color;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Queen extends ChessPiece {

	public Queen(ChessBoard board, Color color) {
		super(board, color);
		this.vestTypes.add(Knight.class);
		this.vestTypes.add(Pawn.class); //for the possibility of future en passant
	}
	
	@Override
	public ImageIcon toImage() {
		ImageIcon icon = this.getColor() == Color.WHITE ? new ImageIcon(getClass().getResource("/images/whiteQueen.png"))
				: new ImageIcon(getClass().getResource("/images/blackQueen.png"));
		Image image = icon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);
	}
	
	@Override
	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {
		ArrayList<String> moves = new ArrayList<>();
		PieceMovement movement = new PieceMovement(board, this);
		moves.addAll(movement.longRangeMoves("Straight"));
		moves.addAll(movement.longRangeMoves("Diagonal"));
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
