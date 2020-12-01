package gameLogic;

import gameLogic.Player.Color;
import exceptions.*;

import javax.swing.*;
import java.awt.*;
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
	public ImageIcon toImage() {
		ImageIcon icon = this.getColor() == Color.WHITE ? new ImageIcon(getClass().getResource("/images/whiteKing.png"))
				: new ImageIcon(getClass().getResource("/images/blackKing.png"));
		Image image = icon.getImage(); // transform it 
		Image newImg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		return new ImageIcon(newImg);
	}

	@Override
	public ArrayList<String> legalMoves(boolean includeVest, boolean turn) {
		PieceMovement movement = new PieceMovement(board, this);
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
