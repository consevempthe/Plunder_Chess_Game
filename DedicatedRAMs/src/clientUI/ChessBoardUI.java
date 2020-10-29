package clientUI;

import client.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class ChessBoardUI implements GameEventHandlers {

	private Game game;
	private final JPanel window = new JPanel(new BorderLayout(3, 3));
	private JButton[][] chessBoardSquares = new JButton[8][8];
	private JPanel chessBoard;
	private static final String COLS = "ABCDEFGH";
	private JButton selectedButton;
	private Object[] plunderOptions = {"Yes", "No"};
	private Object[] confirmOptions = {"Continue", "Cancel"};

	/**
	 * Creates a new instance of the ChessBoardUI class, which takes a game
	 * instance.
	 */
	public ChessBoardUI(Game game) {
		this.game = game;
		this.initializeGui();
		this.fillInPieces();
		this.game.addListener(this);
	}

	/**
	 * Gets the window so it can be rendered in the UI thread.
	 * 
	 * @return the ChessBoard UI
	 */
	public JComponent getGui() {
		return window;
	}
	
	/**
	 * The plunder event that updates the UI when plunder happens on the back end.
	 * 
	 * @param attackingPiece the attacking piece
	 * @param capturedPiece the captured piece
	 */
	@Override
	public void plunderEvent(ChessPiece attackingPiece, ChessPiece capturedPiece) throws IllegalPositionException {
		ArrayList<Class<?>> vestTypes = attackingPiece.getVestTypes();
		boolean pieceIsPlunderable  = vestTypes.contains(capturedPiece.getClass());
		boolean vestIsPlunderable = (capturedPiece.getVest() != null && vestTypes.contains(capturedPiece.getVest().getType().getClass()));
		boolean attackerPrivileged = attackingPiece.getVest() != null;
		
		int plunderResponse = JOptionPane.showOptionDialog(window,
			    "Would you like to plunder?",
			    "Plunder",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    plunderOptions,
			    plunderOptions[1]);
		
		if(plunderResponse == JOptionPane.YES_OPTION) {
			if((pieceIsPlunderable || vestIsPlunderable) && attackerPrivileged) {
				int confirmResponse = JOptionPane.showOptionDialog(window,
					    "Plundering will remove current vest of " + attackingPiece.getVest().getName() + " continue?",
					    "Confirm Plunder",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    confirmOptions,
					    confirmOptions[1]);
				
				if(confirmResponse == JOptionPane.YES_OPTION)
				{
					attackingPiece.setVest(null);
				}
				else
				{
					return;
				}		
			}
			
			Object[] vestOptions = new Object[1];
			if(pieceIsPlunderable && !vestIsPlunderable)
			{
				vestOptions[0] = capturedPiece.getName();
			}
				
			if(pieceIsPlunderable && vestIsPlunderable)
			{
				vestOptions = new Object[2];
				vestOptions[0] = capturedPiece.getName();
				vestOptions[1] = capturedPiece.getVest().getName();
			}
				
			int vestChoice = JOptionPane.showOptionDialog(window,
				    "Select a vest from the following: ",
				    "Confirm Plunder",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    vestOptions,
				    vestOptions.length == 2 ? vestOptions[1] : vestOptions[0]);
			
			if (vestChoice == 0 && pieceIsPlunderable) 
				attackingPiece.setVest(capturedPiece);
			else if(vestChoice == 1 && vestIsPlunderable) 
				attackingPiece.setVest(capturedPiece.getVest().getType());
		}
	}

	/**
	 * Initializes the GUI, this method creates a base blank board in gray scale All
	 * the square are buttons we can add a call back to make game moves
	 */
	private void initializeGui() {
		window.setBorder(new EmptyBorder(5, 5, 5, 5));
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		window.add(tools, BorderLayout.PAGE_START);
		tools.addSeparator();

		chessBoard = new JPanel(new GridLayout(0, 9));
		chessBoard.setBorder(new LineBorder(Color.BLACK));
		window.add(chessBoard);

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				JButton square = new JButton();
				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				square.setIcon(icon);
				square.setOpaque(true);
				chessBoardSquares[row][col] = square;

				if ((row + col) % 2 == 0) {
					square.setBackground(Color.LIGHT_GRAY);
					square.putClientProperty("color", Color.LIGHT_GRAY);
				} else {
					square.setBackground(Color.GRAY);
					square.putClientProperty("color", Color.GRAY);
				}
				
				square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		}
	}

	/**
	 * This method fills in the pieces on the board based on the perspective color,
	 * The black board is a mirror image of the white, with the row and column
	 * labels backwards as well
	 */
	private void fillInPieces() {
		chessBoard.add(new JLabel(""));

		if (game.getCurrentPlayerColor() == client.Player.Color.WHITE) {
			for (int c = 0; c < 8; c++) {
				chessBoard.add(new JLabel(COLS.substring(c, c + 1), SwingConstants.CENTER));
			}

			for (int r = 7; r >= 0; r--)
				for (int c = 0; c < 8; c++) {
					{
						if (c == 0) {
							chessBoard.add(new JLabel("" + (r + 1), SwingConstants.CENTER));
						}
						
						addPiecesToBoard(r, c);
						chessBoard.add(chessBoardSquares[r][c]);
					}
				}
		} else {
			for (int c = 7; c >= 0; c--) {
				chessBoard.add(new JLabel(COLS.substring(c, c + 1), SwingConstants.CENTER));
			}

			for (int r = 0; r < 8; r++)
				for (int c = 7; c >= 0; c--) {
					{
						if (c == 7) {
							chessBoard.add(new JLabel("" + (r + 1), SwingConstants.CENTER));
						}
						
						addPiecesToBoard(r, c);
						chessBoard.add(chessBoardSquares[r][c]);
					}
				}
		}
	}


	/**
	 * Helper Method for fillInPieces(): adds the image of ChessPiece to any
	 * location that has a ChessPiece to the board. Adds an actionListener to the
	 * square to allow user input.
	 *
	 * @param row - of the piece
	 * @param col - of the piece
	 */
	private void addPiecesToBoard(int row, int col) {
		JButton square = chessBoardSquares[row][col];
		square.setOpaque(true);

		ChessPiece piece = this.game.getPieceAtLocation(row, col);

		if (piece != null) {
			square.setIcon(piece.toImage());
		}

		square.putClientProperty("SquareLoc", new Square(row, col));
		square.addActionListener(new SelectSquare());
	}

	/**
	 * Given a boolean statement, if false set the board tiles to their original color, if true highlight movement for
	 * the piece.
	 * @param select - true to highlight movement, false for normal board.
	 */
	protected void highlightPieceMovement(boolean select) {
		Color tile_color;
		if(select) {
			tile_color = Color.ORANGE;
		} else {
			tile_color = (Color) selectedButton.getClientProperty("color");
		}

		selectedButton.setBackground(tile_color);

		Square s = (Square) selectedButton.getClientProperty("SquareLoc");
		ChessPiece selectedPiece = game.getPieceByPosition(s.getPosition());

		for(String move : selectedPiece.legalMoves(true, true)) {
			int row = move.charAt(1) - '1';
			int col = move.charAt(0) - 'a';
			JButton square = chessBoardSquares[row][col];
			if(select) {
				if(selectedPiece.moveIsLegal(move, true)) {
					tile_color = Color.CYAN;

					//Always default to the chess piece color before the vest color (if the move is solely a vest move color accordingly)
					if(selectedPiece.getVest() != null && selectedPiece.getVest().moveIsLegal(move) && !selectedPiece.moveIsLegal(move, false)) {
						tile_color = selectedPiece.getVest().getUiColor();
					}
				}
			}
			else {
				tile_color = (Color) square.getClientProperty("color");
			}
			square.setBackground(tile_color);
		}
	}

	/**
	 * Helper class used to get Positions of empty squares for movement on the board
	 * UI.
	 */
	private static class Square {
		private int row, col;

		public Square(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public String getPosition() {
			char row = (char) ('1' + this.row);
			char col = (char) ('a' + this.col);

			return col + "" + row;
		}

		public String toString() {
			return this.row + " " + this.col;
		}
	}

	/**
	 * This Class implements ActionListener to override the actionPerformed()
	 * method. When a user clicks on a ChessPiece or empty square the
	 * actionPerformed() method will select the piece or move that piece.
	 */
	private class SelectSquare implements ActionListener {
		/**
		 * Allows the user to select or move a piece.
		 * 
		 * @param e - the MouseClick
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton square = (JButton) e.getSource();
			Square selection = (Square) square.getClientProperty("SquareLoc");
			ChessPiece selectedPiece = game.getPieceByPosition(selection.getPosition());

			if (selectedPiece != null && selectedPiece.getColor() == game.getCurrentPlayerColor()) {
				selectPiece(square);
				return;
			}

			if (selectedButton != null) {
				movePiece(square);
			}
		}

		/**
		 * Move is only called when the user has selected a piece. This method is where
		 * the rest of our Chess Logic should go.
		 * 
		 * @param selectedSquare - the square selected via the actionPerformed class
		 */
		private void movePiece(JButton selectedSquare) {
			Square current = (Square) selectedButton.getClientProperty("SquareLoc");
			ChessPiece currentPiece = game.getPieceByPosition(current.getPosition());
			String currentPos = currentPiece.getPosition();

			Square selectedMove = (Square) selectedSquare.getClientProperty("SquareLoc");
			String newPos = selectedMove.getPosition();

			highlightPieceMovement(false);

			if (game.move(currentPos, newPos)) {

				// TODO add check for illegalMovesDueToCheck
				if(currentPiece instanceof Pawn && ((Pawn) currentPiece).hasEnPassant()) {
					movePawnEnPassant(currentPiece, newPos);
				}

				if(currentPiece instanceof King && ((King) currentPiece).hasCastled() &&
						(newPos.equals("c1") || newPos.equals("g1") || newPos.equals("g8") || newPos.equals("c8"))) {
					moveKingCastling(currentPiece);
				}

				selectedButton.setIcon(null);
				selectedButton.setText(null);
				selectedButton.removeAll();
				selectedButton = null;
				selectedSquare.setIcon(currentPiece.toImage());
				
				// Set the vest state if applicable
				if(currentPiece.getVest() != null)
				{
					selectedSquare.setFont(new Font(selectedSquare.getFont().getName(),Font.BOLD,selectedSquare.getFont().getSize())); 
					selectedSquare.setHorizontalTextPosition(SwingConstants.CENTER);
					selectedSquare.setVerticalTextPosition(SwingConstants.BOTTOM);
					selectedSquare.setIconTextGap(-15);
					selectedSquare.setText(currentPiece.getVest().getName());
					selectedSquare.setForeground(currentPiece.getVest().getUiColor());
				}

				// TODO add Checkmate/Check/Etc
			} else {
				highlightPieceMovement(false);
			}
		}

		/**
		 * moves the rook in the UI
		 *
		 * calls moveCastling() in Game class to get the string positions for the rook's current and new position
		 *
		 * @param king - the king that is castling
		 */
		private void moveKingCastling(ChessPiece king) {
			String[] moveRook = game.moveCastling((King) king);
			if(moveRook != null) {
				String currentRookPos = moveRook[0];
				int row_current = currentRookPos.charAt(1) - '1';
				int col_current = currentRookPos.charAt(0) - 'a';

				String newRookPos = moveRook[1];
				int row_new = newRookPos.charAt(1) - '1';
				int col_new = newRookPos.charAt(0) - 'a';

				JButton rookSquareCurrent = chessBoardSquares[row_current][col_current];
				JButton rookSquareNew = chessBoardSquares[row_new][col_new];
				ChessPiece rook = game.getPieceAtLocation(row_new, col_new);
				rookSquareNew.setIcon(rook.toImage());
				rookSquareCurrent.setIcon(null);
			}
		}

		private void movePawnEnPassant(ChessPiece pawn, String newPos) {
			String pawnToCapture = game.moveEnPassant(pawn, newPos);
			if(!pawnToCapture.isEmpty()) {
				int row = pawnToCapture.charAt(1) - '1';
				int col = pawnToCapture.charAt(0) - 'a';

				JButton pawnCaptured = chessBoardSquares[row][col];
				pawnCaptured.setIcon(null);
			}
		}

		/**
		 * If a player is selecting a piece for the first time highlight the movement, the board will not have any
		 * highlighed movement
		 * @param selectedSquare - the square being selected by the user
		 */
		private void selectPiece(JButton selectedSquare) {
			if(selectedButton != null) {
				highlightPieceMovement(false);
			}

			selectedButton = selectedSquare;
			highlightPieceMovement(true);
		}
	}
}
