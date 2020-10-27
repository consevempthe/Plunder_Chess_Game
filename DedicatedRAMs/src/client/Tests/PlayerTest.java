package client.Tests;

import client.ChessBoard;
import client.Player;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


class PlayerTest {

	private ChessBoard board;
	private Player p1;
	@BeforeEach
	public void setUp() {
		String input = "n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		board  = new ChessBoard(in);
		p1 = new Player(client.Player.Color.BLACK, "Nicknamey");
	}
	
	//@Test
	/*void addToMine() throws IllegalPositionException {
		Queen q = new Queen(board, client.ChessPiece.Color.BLACK);
		q.setPosition("d8");
		p1.addPieces(q);
		assertEquals(1, p1.getPieces().size(), "Pieces List size should be 1.");
		assertTrue(p1.getPieces().contains(q), "Pieces List should contain Queen obj just added");
	}
	
	@Test
	void addToCaptured() throws IllegalPositionException {
		Queen q = new Queen(board, client.ChessPiece.Color.WHITE);
		q.setPosition("d1");
		p1.captureOpponentPiece(q);
		assertEquals(1, p1.getCapturedPieces().size(), "capturedPieces List size should be 1.");
		assertTrue(p1.getCapturedPieces().contains(q), "capturedPieces List should contain Queen obj just added");
	}
	
	@Test
	void removeFromMine() throws IllegalPositionException {
		Queen q = new Queen(board, client.ChessPiece.Color.BLACK);
		q.setPosition("c6");
		p1.addPieces(q);
		assertEquals(1, p1.getPieces().size(), "Pieces List size should be 1.");
		assertTrue(p1.getPieces().contains(q), "Pieces List should contain Queen obj just added");
		p1.removePieces(q);
		assertEquals(0, p1.getPieces().size(), "Pieces List size should be 0.");
		assertFalse(p1.getPieces().contains(q), "Pieces List should contain Queen obj just added");
	}*/

}
