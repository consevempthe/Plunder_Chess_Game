package clientUI.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import client.ChessPiece;
import clientUI.PawnPromoteUI;

class PawnPromoteUITest {
	
	private ChessPiece.Color white = ChessPiece.Color.WHITE;

	@Test
	void testInstantiation() {
		PawnPromoteUI ui = new PawnPromoteUI(white);
		assertNotNull(ui);
	}

}
