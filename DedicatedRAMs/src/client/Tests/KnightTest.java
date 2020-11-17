package client.Tests;

import gameLogic.ChessBoard;
import gameLogic.Knight;
import gameLogic.Player.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KnightTest {

    ChessBoard chess;
    Knight k_w;
    Knight k_b;

    @BeforeEach
    void setup() {
		chess  = new ChessBoard();
        k_w = new Knight(chess, Color.WHITE);
        k_b = new Knight(chess, Color.BLACK);
    }

    @Test
    void legalMoves() {
        chess.placePiece(k_w, "b1");
        assertEquals(3, k_w.legalMoves(true, true).size());
        assertTrue(k_w.legalMoves(true, true).contains("a3"));
        assertTrue(k_w.legalMoves(true, true).contains("c3"));
        assertTrue(k_w.legalMoves(true, true).contains("d2"));

        chess.placePiece(k_b, "b8");
        assertEquals(3, k_b.legalMoves(true, true).size());
        assertTrue(k_b.legalMoves(true, true).contains("a6"));
        assertTrue(k_b.legalMoves(true, true).contains("c6"));
        assertTrue(k_b.legalMoves(true, true).contains("d7"));

        chess.placePiece(k_w, "h1");
        assertEquals(2, k_w.legalMoves(true, true).size());
    }

    @Test
    void noLegalMoves() {
        chess.placePiece(k_w, "c4");
        chess.placePiece(new Knight(chess, Color.WHITE), "b6");
        chess.placePiece(new Knight(chess, Color.WHITE), "d6");
        chess.placePiece(new Knight(chess, Color.WHITE), "a5");
        chess.placePiece(new Knight(chess, Color.WHITE), "e5");
        chess.placePiece(new Knight(chess, Color.WHITE), "b2");
        chess.placePiece(new Knight(chess, Color.WHITE), "d2");
        chess.placePiece(new Knight(chess, Color.WHITE), "a3");
        chess.placePiece(new Knight(chess, Color.WHITE), "e3");

        assertEquals(0, k_w.legalMoves(true, true).size());

    }

    @Test
    void legalCaptureMoves() {
        chess.placePiece(k_w, "g1");
        chess.placePiece(k_b, "f3");
        assertEquals(3, k_w.legalMoves(true, true).size());
        assertTrue(k_w.legalMoves(true, true).contains("f3"));
    }
}