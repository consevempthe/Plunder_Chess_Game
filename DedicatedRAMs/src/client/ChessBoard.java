package client;

import client.ChessPiece.Color;

public class ChessBoard {
	
	private ChessPiece[][] board;
	private MoveHistory history = new MoveHistory();
	private King whiteKing = new King(this, Color.WHITE);
	private King blackKing = new King(this, Color.BLACK);
	
	public ChessBoard() {
		board = new ChessPiece[8][8];
	}

	public void initialize() {
		for (int i = 0; i < 8; i++) {
			placePiece(new Pawn(this, Color.WHITE), (char) ('a' + i) + "" + (char) ('1' + 1));
			placePiece(new Pawn(this, Color.BLACK), (char) ('a' + i) + "" + (char) ('1' + 6));
		}
		placePiece(new Rook(this, Color.WHITE), "a1");
		placePiece(new Rook(this, Color.BLACK), "a8");
		placePiece(new Rook(this, Color.WHITE), "h1");
		placePiece(new Rook(this, Color.BLACK), "h8");
		placePiece(new Knight(this, Color.WHITE), "b1");
		placePiece(new Knight(this, Color.BLACK), "b8");
		placePiece(new Knight(this, Color.WHITE), "g1");
		placePiece(new Knight(this, Color.BLACK), "g8");
		placePiece(new Bishop(this, Color.WHITE), "c1");
		placePiece(new Bishop(this, Color.BLACK), "c8");
		placePiece(new Bishop(this, Color.WHITE), "f1");
		placePiece(new Bishop(this, Color.BLACK), "f8");
		placePiece(new Queen(this, Color.WHITE), "d1");
		placePiece(new Queen(this, Color.BLACK), "d8");
		placePiece(whiteKing, "e1");
		placePiece(blackKing, "e8");
	}

	public MoveHistory getHistory() {
		return history;
	}

	public ChessPiece getPiece(String position) throws IllegalPositionException {
		if (!isPositionOnBoard(position))
			throw new IllegalPositionException();
		int i1 = position.charAt(0) - 'a';
		int i2 = position.charAt(1) - '1';
		return board[i2][i1];
	}

	public boolean placePiece(ChessPiece piece, String position) {
		try {
			if (getPiece(position) != null && getPiece(position).getColor().equals(piece.getColor()))
				return false;
			else if (getPiece(position) != null && !getPiece(position).getColor().equals(piece.getColor())) {
				history.setCapturedPieceInMove(getPiece(position));
				this.captureAndReplace(piece, position); // capture the piece does this need to be added to some sort of
			}											// list
			piece.setPosition(position);
		} catch (IllegalPositionException e) {
			return false;
		}
		int i1 = position.charAt(0) - 'a';
		int i2 = position.charAt(1) - '1';
		board[i2][i1] = piece;
		return true;
	}

	public void move(String from, String to) throws IllegalMoveException, IllegalPositionException {
		ChessPiece pieceToMove = null;
		try {
			pieceToMove = getPiece(from);
		} catch (IllegalPositionException e) {
			throw new IllegalMoveException();
		}
		if (pieceToMove != null && pieceToMove.legalMoves(true, true).contains(to)) {
			// TODO add a scanner to ask if the user wants to use the vest as part of the
			// move
			if (pieceToMove.getVest() != null) {
				// if the move is in vest and not the parent piece it's a vest move
				if (pieceToMove.getVest().getType().legalMoves(true, true).contains(to)
						&& !pieceToMove.legalMoves(false, true).contains(to)) {
					pieceToMove.setVest(null);
				}
			}
			history.addMoveToMoveHistory(new Move(pieceToMove, from, to, null));
			placePiece(pieceToMove, to);
			removePiece(from);	
			tryPawnPromote(to);
		} else
			throw new IllegalMoveException();
	}
	
	public void simulateMove(ChessPiece pieceToMove, String from, String to) {
		history.addMoveToMoveHistory(new Move(pieceToMove, from, to, null));
		placePiece(pieceToMove, to);
		removePiece(from);
	}

	private void removePiece(String position) {
		int row = position.charAt(1) - '1';
		int col = position.charAt(0) - 'a';
		board[row][col] = null;
	}

	public void replacePiece(ChessPiece newPiece, String position) {
		int row = position.charAt(1) - '1';
		int col = position.charAt(0) - 'a';
		this.board[row][col] = null;
		placePiece(newPiece, position);
	}

	public void captureAndReplace(ChessPiece piece, String position) throws IllegalPositionException {
		if (true) //TODO swap this to a scanner that asks if they want to plunder
		{
			//TODO check if the captured piece has a vest, if so ask the user if they want to plunder the parent piece or the vest
			ChessPiece vest = this.getPiece(position);
			if(piece.plunderableTypes.contains(vest.getClass())) //check that the piece is a plunder-able type
			{
				piece.setVest(vest);
			}	
		}
		

		this.replacePiece(piece, position);
	}

	// this function is called by move();
	private void tryPawnPromote(String position) {

		// TODO: Note that pawn just gets promoted to QUEEN right now
		// However, it can also get promoted to Bishop, Knight, and Rook,
		// depending on the user.
		ChessPiece piece = null;
		try {
			piece = getPiece(position);
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		if (piece instanceof Pawn && ((position.charAt(1) == '1' && piece.color == Color.BLACK)
				|| (position.charAt(1) == '8' && piece.color == Color.WHITE))) {
			Pawn pawn = (Pawn) piece;
			pawn.promote("QUEEN");
		}

	}

	public boolean isPositionOnBoard(String position) {
		return position.length() == 2 && position.charAt(0) >= 'a' && position.charAt(0) <= 'h'
				&& position.charAt(1) >= '1' && position.charAt(1) <= '8';
	}
	
	public boolean isCheck(Color currentColor) {
		King temp =  currentColor == Color.WHITE ?  whiteKing : blackKing;
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				ChessPiece piece = null;
				try {
					piece = board[row][col];
				}catch(NullPointerException e) {}
				if(piece != null && piece.color != currentColor && piece.getClass() != King.class && piece.legalMoves(true, false).contains(temp.getPosition())) {
					System.out.println("Check");
					return true;
				}
			}
		}
		King temp2 =  currentColor == Color.BLACK ?  whiteKing : blackKing;
		
		if(temp2.legalMoves(true, false).contains(temp.getPosition())) {
			return true;
		}
		return false;
	}

	// This method is just for testing, remove when UI is implemented
	public String toString() {
		String chess = "";
		String upperLeft = "\u250C";
		String upperRight = "\u2510";
		String horizontalLine = "\u2500";
		String horizontal3 = horizontalLine + "\u3000" + horizontalLine;
		String verticalLine = "\u2502";
		String upperT = "\u252C";
		String bottomLeft = "\u2514";
		String bottomRight = "\u2518";
		String bottomT = "\u2534";
		String plus = "\u253C";
		String leftT = "\u251C";
		String rightT = "\u2524";

		String topLine = upperLeft;
		for (int i = 0; i < 7; i++) {
			topLine += horizontal3 + upperT;
		}
		topLine += horizontal3 + upperRight;

		String bottomLine = bottomLeft;
		for (int i = 0; i < 7; i++) {
			bottomLine += horizontal3 + bottomT;
		}
		bottomLine += horizontal3 + bottomRight;
		chess += topLine + "\n";

		for (int row = 7; row >= 0; row--) {
			String midLine = "";
			for (int col = 0; col < 8; col++) {
				if (board[row][col] == null) {
					midLine += verticalLine + "\u3000";
				} else {
					midLine += verticalLine + "" + board[row][col] + "";
				}
			}
			midLine += verticalLine;
			String midLine2 = leftT;
			for (int i = 0; i < 7; i++) {
				midLine2 += horizontal3 + plus;
			}
			midLine2 += horizontal3 + rightT;
			chess += midLine + "\n";
			if (row >= 1)
				chess += midLine2 + "\n";
		}

		chess += bottomLine;
		return chess;
	}

}
