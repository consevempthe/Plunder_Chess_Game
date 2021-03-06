package gameLogic;

import gameLogic.Player.Color;
import exceptions.*;

import javax.swing.*;
import java.util.ArrayList;

public abstract class ChessPiece {

	protected ChessBoard board;
	protected int row;
	protected int column;
	protected Color color;
	protected Vest vest = null;
	protected boolean hasMoved = false;
	protected boolean noMovementDueToCheck = false;
	protected ArrayList<Class<? extends ChessPiece>> vestTypes;

	public ChessPiece (ChessBoard board, Color color) {
		this.board = board;
		this.color = color;
		this.vestTypes = new ArrayList<>();
	}

	/**
	 * Determines if a piece can obtain a vest
	 * @param piece - the piece in question
	 * @return - true if that piece is one of the types that can be plundered, false otherwise.
	 */
	public boolean hasVestType(ChessPiece piece) {
		return vestTypes.contains(piece.getClass());
	}
	
	public String getName() {
		String typeString = this.getClass().toString();
		return typeString.substring(typeString.lastIndexOf(".") + 1);
	}

	/**
	 * Getter Method: returns Color of piece.
	 * @return Color - Can be either BLACK, or WHITE.
	 */
	public Color getColor() {
		return color;
	}


	/**
	 * Getter Method: Returns the string position of a piece depending on the int row/column that piece is in
	 * @return string position - i.e. "a1".
	 */
	public String getPosition() {
		char c = (char) ('a' + this.column);
		char r = (char)('1' + this.row);
		return c + "" + r;
	}

	/**
	 * @return returns hasMoved variable
	 */
	public boolean getHasMoved() {
		return this.hasMoved;
	}

	
	/**
	 * returns if the chess piece has a vest
	 * @return if the vest is not equal to null return true
	 */
	public boolean hasVest()
	{
		return this.vest != null;
	}
	
	/**
	 * returns if a move is legal for a vest
	 * @return if the vest contains the position in it's legal moves return true
	 */
	public boolean isVestMoveLegal(String position)
	{
		if(this.hasVest())
			return this.vest.moveIsLegal(position);
		else
			return false;
	}

	/**
	 * Getter Method: Returns the vest type if a vest exists
	 * @return a vest type as a chess piece  if the vest is not null, to be used to set a vest type
	 */
	public ChessPiece getVest()
	{
		if(this.hasVest())
			return this.vest.getType();
		else
			return null;
	}
	
	/**
	 * Getter Method: Returns the vest position if a vest exists
	 * @return a vest position if the vest is not null
	 */
	public String getVestPosition()
	{
		if(this.hasVest())
			return this.vest.getVestPosition();
		else
			return "";
	}
	
	/**
	 * Getter Method: Returns the vest name if a vest exists, to be used on the UI
	 * @return a vest name if the vest is not null.
	 */
	public String getVestName()
	{
		if(this.hasVest())
			return this.vest.getName();
		else
			return "";
	}
	
	/**
	 * Getter Method: Returns the vest color if a vest exists, to be used on the UI
	 * @return a vest color if the vest is not null.
	 */
	public java.awt.Color getVestColor()
	{
		if(this.hasVest())
			return this.vest.getUiColor();
		else
			return java.awt.Color.BLACK;
	}
	
	/**
	 * Setter Method: Sets the color of the vest piece if one exists
	 */
	public void setVestPieceColor(Color color)
	{
		if(this.hasVest()) {
			this.vest.setVestPieceColor(color);
		}
	}

	/**
	 * Determines that a piece has moved. Should not be called to set it to false.
	 * Only called by ChessBoard move method after the piece has been moved.
	 * @param hasMoved - boolean stating the piece has moved
	 */
	public void setHasMoved(Boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	/**
	 * Setter Method: Given a string position this method sets the int row/column for the piece to the that position
	 *
	 * If the piece has a vest it will change the vests position as well
	 *
	 * @param position - 2 character string - i.e. "a2"
	 * @throws IllegalPositionException - if given an incorrect string - i.e. "hi"
	 */
	public void setPosition(String position) throws IllegalPositionException {
		if(!board.isPositionOnBoard(position))
			throw new IllegalPositionException("Tried to set ChessPiece position off of the board.");
		this.row = position.charAt(1) - '1';
		this.column = position.charAt(0) - 'a';
		
		// if the vest exists set the position to the same position as the parent piece
		if(this.hasVest())
		{
			this.vest.setVestPosition(position);
		}
	}

	/**
	 * Setter Method: sets the vest of piece to a type
	 * @param type - the Class of ChessPiece that the current piece has plundered
	 * @throws IllegalPositionException - because it calls getPosition()
	 */
	public void setVest(ChessPiece type) throws IllegalPositionException
	{
		this.vest = type != null ? new Vest(type) : null;
		// if the vest exists set the position to the same position as the parent piece
		if(this.hasVest())
			this.vest.setVestPosition(this.getPosition());
	}

	/**
	 * Helper Method: Checks to make sure that a square is empty or doesn't contain a piece of the same color.
	 *
	 * This method is invoked in PieceMovement class by pawnCapture(), kingCircleOfMoves(), performMoveAddition(),
	 * and knightJumpMoves().
	 *
	 * @param position - the position of the square in question
	 * @return - true if the position null or an enemy piece, and false otherwise.
	 */
	public boolean isPositionTakable(String position) {
		try {
			ChessPiece piece = board.getPiece(position);
			if(piece != null && piece.color.equals(this.color))
				return false;
		} catch (IllegalPositionException e) {
			return false;
		}
		return true;
	}

	public boolean moveIsLegal(String position, boolean includeVest) {
		return this.legalMoves(includeVest, true).contains(position);
	}

	/**
	 * Helper Method: Iterates through a list of legalMoves for a chessPiece and simulates moves to determine if that
	 * move would cause check incidentally. If the move does cause check it is removed from the list of legal moves.
	 *
	 * Since this method is actually moving the pieces the remaining code is used to return pieces to their original
	 * spaces after the simulated move.
	 *
	 * This method calls -- ChessBoard isCheck() -- to determine if the move causes a check.
	 *
	 * @param legalMoves - array of the potential legal moves
	 * @return array of legal moves without any move that might cause check
	 */
	public ArrayList<String> illegalMovesDueToCheck(ArrayList<String> legalMoves) {
		Vest currentVest = this.vest;
		ArrayList<String> newLegalMoves = new ArrayList<>(legalMoves);

		for(String newPos: legalMoves) {
			String currentPos = this.getPosition();
			
			if(board.moveProducesCheck(this, currentPos, newPos)) {
				noMovementDueToCheck = true;
				newLegalMoves.remove(newPos);
			}

			this.vest = currentVest;
		}
		return newLegalMoves;
	}

	public boolean hasIllegalMovesDueToCheck() {
		return noMovementDueToCheck;
	}

	public void resetIllegalMoveCheck() {
		noMovementDueToCheck = false;
	}
	

	/**
	 * Determines the legal moves of a ChessPiece which is obtained by calling PieceMovement methods to determine the
	 * legal moves depending on the Piece and any special moves that piece might because of plundering.
	 * @param includeVest - boolean - true means to include plundered moves, false will not include that movement.
	 * @param turn - boolean - this is used for determining illegalMovesDueToCheck().
	 * @return list of String positions that are legal moves.
	 */
	abstract public ArrayList<String> legalMoves(boolean includeVest, boolean turn);

	/**
	 * UI method - to get the image for the given piece.
	 * @return - Image for the given ChessPiece.
	 */
	abstract public ImageIcon toImage();

	/**
	 * Setter method - to set the color of a given piece.
	 * @param color2 - the new color of the piece
	 */	
	public void setColor(Color color2) {
		color = color2;
	}
	
}