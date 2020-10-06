package client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.ChessPiece.Color;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import client.Player.GameStatus;

class PlayerTest {

	private ChessBoard board;
	private Player p1;
	@BeforeEach
	public void setUp() {
		board = new ChessBoard();
		p1 = new Player(Player.Color.BLACK, GameStatus.INPROGRESS, "Nicknamey", true);
	}
	
	@Test
	void addToMine() throws IllegalPositionException {
		Queen q = new Queen(board, client.ChessPiece.Color.BLACK);
		q.setPosition("d8");
		p1.addMyPieces(q);
		assertEquals(1, p1.getMyPieces().size(), "myPieces List size should be 1.");
		assertEquals(true, p1.getMyPieces().contains(q), "myPieces List should contain Queen obj just added");
	}
	
	@Test
	void addToCaptured() throws IllegalPositionException {
		Queen q = new Queen(board, client.ChessPiece.Color.WHITE);
		q.setPosition("d1");
		p1.captureOpponentPiece(q);
		assertEquals(1, p1.getCapturedPieces().size(), "capturedPieces List size should be 1.");
		assertEquals(true, p1.getCapturedPieces().contains(q), "capturedPieces List should contain Queen obj just added");
	}
	
	@Test
	void removeFromMine() throws IllegalPositionException {
		Queen q = new Queen(board, client.ChessPiece.Color.BLACK);
		q.setPosition("c6");
		p1.addMyPieces(q);
		assertEquals(1, p1.getMyPieces().size(), "myPieces List size should be 1.");
		assertEquals(true, p1.getMyPieces().contains(q), "myPieces List should contain Queen obj just added");
		p1.myPieceCaptured(q);
		assertEquals(0, p1.getMyPieces().size(), "myPieces List size should be 0.");
		assertEquals(false, p1.getMyPieces().contains(q), "myPieces List should contain Queen obj just added");
	}

}
