package client.Tests;

import client.ChessBoard;
import client.ChessPiece;
import client.Knight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class KnightTest {

    ChessBoard chess;
    Knight k_w;
    Knight k_b;

    @BeforeEach
    void setup() {
    	String input = "n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		chess  = new ChessBoard(in);
        k_w = new Knight(chess, ChessPiece.Color.WHITE);
        k_b = new Knight(chess, ChessPiece.Color.BLACK);
    }

    @Test
    void testToString() {
        assertEquals("\u2658", k_w.toString());
        assertEquals("\u265E", k_b.toString());
    }

    @Test
    void legalMoves() {
        chess.placePiece(k_w, "b1", false);
        assertEquals(3, k_w.legalMoves(true, true).size());
        assertTrue(k_w.legalMoves(true, true).contains("a3"));
        assertTrue(k_w.legalMoves(true, true).contains("c3"));
        assertTrue(k_w.legalMoves(true, true).contains("d2"));

        chess.placePiece(k_b, "b8", false);
        assertEquals(3, k_b.legalMoves(true, true).size());
        assertTrue(k_b.legalMoves(true, true).contains("a6"));
        assertTrue(k_b.legalMoves(true, true).contains("c6"));
        assertTrue(k_b.legalMoves(true, true).contains("d7"));

        chess.placePiece(k_w, "h1", false);
        assertEquals(2, k_w.legalMoves(true, true).size());
    }

    @Test
    void noLegalMoves() {
        chess.placePiece(k_w, "c4", false);
        chess.placePiece(new Knight(chess, ChessPiece.Color.WHITE), "b6", false);
        chess.placePiece(new Knight(chess, ChessPiece.Color.WHITE), "d6", false);
        chess.placePiece(new Knight(chess, ChessPiece.Color.WHITE), "a5", false);
        chess.placePiece(new Knight(chess, ChessPiece.Color.WHITE), "e5", false);
        chess.placePiece(new Knight(chess, ChessPiece.Color.WHITE), "b2", false);
        chess.placePiece(new Knight(chess, ChessPiece.Color.WHITE), "d2", false);
        chess.placePiece(new Knight(chess, ChessPiece.Color.WHITE), "a3", false);
        chess.placePiece(new Knight(chess, ChessPiece.Color.WHITE), "e3", false);

        assertEquals(0, k_w.legalMoves(true, true).size());

    }

    @Test
    void legalCaptureMoves() {
        chess.placePiece(k_w, "g1", false);
        chess.placePiece(k_b, "f3", false);
        assertEquals(3, k_w.legalMoves(true, true).size());
        assertTrue(k_w.legalMoves(true, true).contains("f3"));
    }
}