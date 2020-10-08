package client;

import java.util.ArrayList;

public class MoveHistory {

	private ArrayList<Move> moveHistory;
	
	public MoveHistory() {
		this.moveHistory = new ArrayList<Move>();
	}
	
	public ArrayList<Move> getMoveHistory() {
		return moveHistory;
	}

	public void setMoveHistory(ArrayList<Move> moveHistory) {
		this.moveHistory = moveHistory;
	}

	public void addMoveToMoveHistory(Move moveToAdd) {
		
	}
	
}
