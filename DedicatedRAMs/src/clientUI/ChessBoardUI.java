package clientUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
	private Color perspectiveColor = Color.BLACK; // this should be coming from the player...
	private JButton selectedPiece;

	/**
	 * Creates a new instance of the ChessBoardUI class, which takes a game instance.
	 */
	public ChessBoardUI(Game game) {
		this.game = game;
		this.initializeGui();
		this.fillInPieces();
	}


	/**
	 * Gets the window so it can be rendered in the UI thread.
	 * @return the ChessBoard UI
	 */
	public JComponent getGui() {
		return window;
	}

	/**
	 * Initializes the GUI, this method creates a base blank board in gray scale
	 * All the square are buttons we can add a call back to make game moves
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

		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				JButton square = new JButton();
				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				square.setIcon(icon);
				square.setOpaque(true);
				square.setBorderPainted(false);
				chessBoardSquare[row][col] = square;

				if((row + col) % 2 == 0) {
					square.setBackground(java.awt.Color.lightGray);
				} else {
					square.setBackground(java.awt.Color.gray);
				}
			}
		}
	}

	/**
	 * This method fills in the pieces on the board based on the perspective color,
	 * The black board is a mirror image of the white, with the row and column labels backwards as well
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
	 * Helper Method for fillInPieces(): adds the piece to the board
	 * @param row - of the piece
	 * @param col - of the piece
	 */
	private void addPiecesToBoard(int row, int col) {
		JButton square = chessBoardSquare[row][col];
		square.setOpaque(true);
		square.setBorderPainted(false);

		ChessPiece piece = this.game.getPieceAtLocation(row, col);

		if (piece != null) {
			square.setIcon(piece.toImage());
		}

		square.putClientProperty("SquareLoc", new Square(row, col));
		square.addActionListener(new SelectSquare());
	}

	private static class Square {
		private int row, col;
		private ChessPiece piece;
		public Square(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public String getPosition() {
			char row = (char)('1' + this.row);
			char col = (char)('a' + this.col);

			return col + "" + row;
		}

		public String toString() {
			return this.row + " " + this.col;
		}

		public void setPiece(ChessPiece piece) {
			this.piece = piece;
		}

		public boolean hasPiece() {
			return this.piece != null;
		}
	}

	private class SelectSquare implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton square = (JButton) e.getSource();
			Square selection = (Square) square.getClientProperty("SquareLoc");
			ChessPiece piece = game.getPieceByPosition(selection.getPosition());
			//TODO make sure that it is the current player and they are selecting a piece that isn't there color do nothing
			if(piece != null) {
				selectedPiece = square;
				return;
			}

			if(selectedPiece != null) {
				movePiece(square);
			}
		}

		private void movePiece(JButton selectedSquare) {
			Square current = (Square) selectedPiece.getClientProperty("SquareLoc");
			ChessPiece currentPiece = game.getPieceByPosition(current.getPosition());
			String currentPos = currentPiece.getPosition();

			Square selectedMove = (Square) selectedSquare.getClientProperty("SquareLoc");
			String newPos = selectedMove.getPosition();
			if(currentPiece.moveIsLegal(newPos)) {
				game.move(currentPos, newPos);

				selectedPiece.setIcon(null);
				selectedPiece = null;
				selectedSquare.setIcon(currentPiece.toImage());
			}
		}
	}
}
