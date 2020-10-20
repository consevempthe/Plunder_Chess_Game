package client;

import java.util.ArrayList;
import client.ChessPiece.Color;

/**
 * MoveHistory class is a class containing the moves of a game in an ArrayList. The moveHistory list should be added to as players make moves.
 * @author DedicatedRAMs Team
 *
 */
public class MoveHistory {

	private ArrayList<Move> moveHistory;
	
	private ArrayList<Move> whiteMoves;
	
	private ArrayList<Move> blackMoves;
	
	private int lastPawnMove = 0;
	
	private int lastCaptureMove = 0;
	
	private int repeatCountWhiteEvenMove = 1;
	
	private int repeatCountWhiteOddMove = 1;
	
	private int repeatCountBlackEvenMove = 1;
	
	private int repeatCountBlackOddMove = 1;
	
	/**
	 * Default Constructor for MoveHistory creates an empty list of Moves.
	 */
	public MoveHistory() {
		this.moveHistory = new ArrayList<>();
		this.whiteMoves = new ArrayList<>();
		this.blackMoves = new ArrayList<>();
	}
	
	/**
	 * getMoveHistory() is the getter for the moveHistory arrayList so that we can view the lists content outside of the MoveHistory class.
	 * @return ArrayList<Move>
	 */
	public ArrayList<Move> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * setMoveHistory() is the setter for the moveHistory ArrayList so that we can set the list if the game is being resumed, potentially. Check later to see if necessary.
	 * @param moveHistory - the list of moves in a game.
	 */
	public void setMoveHistory(ArrayList<Move> moveHistory) {
		this.moveHistory = moveHistory;
	}

	/**
	 * addMoveToMoveHistory() adds a Move to the end of the ArrayList as a game progesses.
	 * This method also keeps track of the repetition of a move, and the last move of a pawn to be used to check draw
	 * For the repetition the list of white moves and black moves are tracked and for each odd and even move I keep track of the 
	 * to value of the to location and count the repeated values
	 * @param moveToAdd - the move to add to the move history.
	 */
	public void addMoveToMoveHistory(Move moveToAdd) {
		moveHistory.add(moveToAdd);
		if(moveToAdd.getPieceMoved().getClass() == Pawn.class)
		{
			this.lastPawnMove = moveHistory.size();
		}
		
		if(moveToAdd.getPieceMoved().color == Color.WHITE)
		{
			if(this.whiteMoves.size() > 1)
			{
				Move compare = this.whiteMoves.get(this.whiteMoves.size() - 2); //get the second to last move to compare
				if(compare.getNewPos().equals(moveToAdd.getNewPos()) && moveToAdd.getPieceMoved().getClass() == compare.getPieceMoved().getClass())
				{
					if(this.whiteMoves.size() % 2 == 0)
					{
						this.repeatCountWhiteOddMove++; //odd because the current move hasn't been added yet
					}
					else
					{
						this.repeatCountWhiteEvenMove++;
					}
				}
				else
				{
					if(this.whiteMoves.size() % 2 == 0)
					{
						this.repeatCountWhiteOddMove = 1; //odd because the current move hasn't been added yet
					}
					else
					{
						this.repeatCountWhiteEvenMove = 1;
					}
				}
			}
			
			this.whiteMoves.add(moveToAdd);
		}
		else
		{
			if(this.blackMoves.size() > 1)
			{
				Move compare = this.blackMoves.get(this.blackMoves.size() - 2); //get the second to last move to compare
				if(compare.getNewPos().equals(moveToAdd.getNewPos()) && moveToAdd.getPieceMoved().getClass() == compare.getPieceMoved().getClass())
				{
					if(this.blackMoves.size() % 2 == 0)
					{
						this.repeatCountBlackOddMove++; //odd because the current move hasn't been added yet
					}
					else
					{
						this.repeatCountBlackEvenMove++;
					}
				}
				else
				{
					if(this.blackMoves.size() % 2 == 0)
					{
						this.repeatCountBlackOddMove = 1; //odd because the current move hasn't been added yet
					}
					else
					{
						this.repeatCountBlackEvenMove = 1;
					}
				}
			}
			
			this.blackMoves.add(moveToAdd);
		}
	}
	
	public void removeEnd() {
		Move move = moveHistory.remove(moveHistory.size() -1);
		if(move.getPieceMoved().getClass() == Pawn.class)
		{
			this.lastPawnMove = moveHistory.size();
		}
		
		if(move.getCaptured() != null)
		{
			this.lastCaptureMove = moveHistory.size();
		}
	}
	
	public void setCapturedPieceInMove(ChessPiece captured) {
		moveHistory.get(moveHistory.size() - 1).setCaptured(captured);
		this.lastCaptureMove = moveHistory.size();
	}
	
	public Move getLastMove() {
		return (moveHistory.get(0) != null) ? moveHistory.get(moveHistory.size() - 1): null;
	}
	
	/*
	 * *
	 * checkFiftyMoveRule() if the fifty move rule applies, if there have been 50 or more moves where a pawn hasn't moved or a piece hasn't been captured
	 * then the fifty move rule is true
	 * @return a value indicating whether or not fifty move rule applies
	 */
	public boolean checkFiftyMoveRule()
	{
		int size = this.moveHistory.size();
		if(size >= 50)
		{
			return size - this.lastPawnMove >= 50 && size - this.lastCaptureMove >= 50;
		}
		
		return false;
	}
	
	/*
	 * *
	 * checkFiftyMoveRule() if the three fold repetition applies, this occurs if the both players more the same pieces back and forth to the same position
	 * three times. (See logic where the move history is added the checks the repetition
	 * @return a value indicating whether or not three fold repetition applies
	 */
	public boolean checkThreefoldRepetition()
	{
		return (this.repeatCountBlackEvenMove >= 3 || this.repeatCountBlackOddMove >= 3)
				&& (this.repeatCountWhiteEvenMove >= 3 || this.repeatCountWhiteOddMove >= 3);
		
	}
	
}
