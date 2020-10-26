package clientUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.*;

import client.ChessPiece;
import client.Game;
import client.ChessPiece.Color;
import client.IllegalPositionException;


public class ChessBoardUI {

	private Game game;
	private final JPanel window = new JPanel(new BorderLayout(3, 3));
	private JButton[][] chessBoardSquare = new JButton[8][8];
	private JPanel chessBoard;
	private static final String COLS = "ABCDEFGH";
	private Color perspectiveColor = Color.WHITE; // this should be coming from the player...
	private JButton selectedButton;
	private ChessPiece selectedPiece;

	/**
	 * Creates a new instance of the ChessBoardUI class, which takes a game
	 * instance.
	 */
	public ChessBoardUI(Game game) {
		this.game = game;
		this.initializeGui();
		this.fillInPieces();
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
		chessBoard.setBorder(new LineBorder(java.awt.Color.BLACK));
		window.add(chessBoard);

		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				JButton square = new JButton();
				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				square.setIcon(icon);
				square.setOpaque(true);
				chessBoardSquare[row][col] = square;

				if ((row + col) % 2 == 0) {
					square.setBackground(java.awt.Color.lightGray);
				} else {
					square.setBackground(java.awt.Color.gray);
				}
				
				square.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
			}
		}
	}
	
	/**
	 * This method checks the a square is in the legal moves of a piece and colors it accordingly
	 * @param chessPiece - the chess piece whose legal moves are checked against
	 * @param squareCol - of the square column
	 * @param squareRow - of the square row
	 * @param square - the square to be colored
	 */
	private void checkPositionAndColorSquare(ChessPiece chessPiece, int squareCol, int squareRow, JButton square)
	{
		if (chessPiece.moveIsLegal(this.convertToPosition(squareCol, squareRow))) {
			square.setBackground(java.awt.Color.cyan);

			if (chessPiece.getVest() != null) {
				if (chessPiece.getVest().moveIsLegal(this.convertToPosition(squareCol, squareRow))) {
					square.setBackground(chessPiece.getVest().getUiColor());
				}
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

		if (this.perspectiveColor == Color.WHITE) {
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
						chessBoard.add(chessBoardSquare[r][c]);
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
						chessBoard.add(chessBoardSquare[r][c]);
					}
				}
		}
	}
	
	/**
	 * This resets all the buttons back to gray scale
	 */
	private void setGrayScaleSpaces()
	{
		for (int col = 0; col < 8; col++) {
			for (int row = 0; row < 8; row++) {
				JButton square = chessBoardSquare[row][col];
				if ((row + col) % 2 == 0) {
					square.setBackground(java.awt.Color.lightGray);
				} else {
					square.setBackground(java.awt.Color.gray);
				}
				
				square.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
			}
		}
	}
	
	/**
	 * This method colors the spaces according to the selected piece's legal moves
	 */
	private void setColorSpaces()
	{
		for (int c = 0; c < 8; c++) {
			for (int r = 0; r < 8; r++) {
				JButton square = chessBoardSquare[r][c];
				if (selectedPiece != null) {
					this.checkPositionAndColorSquare(selectedPiece, c, r, square);
				}
				
				square.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
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
		JButton square = chessBoardSquare[row][col];
		square.setOpaque(true);

		ChessPiece piece = this.game.getPieceAtLocation(row, col);

		if (piece != null) {
			square.setIcon(piece.toImage());
		}

		square.putClientProperty("SquareLoc", new Square(row, col));
		square.addActionListener(new SelectSquare());
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
	 * Helper method to convert a board col and row to a location.
	 * 
	 * @returns the position as a string
	 */
	private String convertToPosition(int col, int row) {
		char c = (char) ('a' + col);
		char r = (char) ('1' + row);
		return c + "" + r;
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
			selectedPiece = game.getPieceByPosition(selection.getPosition());

			// TODO add to if statement - if (piece).color == player color
			if (selectedPiece != null) {
				setGrayScaleSpaces();
				System.out.println("Selected a piece");
				selectedButton = square;
				setColorSpaces();
				return;
			}

			if (selectedButton != null) {
				System.out.println("move a piece");
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

			if (game.move(currentPos, newPos)) {

				// TODO add check for illegalMovesDueToCheck

				selectedButton.setIcon(null);
				selectedButton = null;
				selectedSquare.setIcon(currentPiece.toImage());
				setGrayScaleSpaces();

				// TODO add Checkmate/Check/Plunder/Etc
			}
		}
	}
}
