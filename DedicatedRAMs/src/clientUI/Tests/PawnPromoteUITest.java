package clientUI.Tests;

import clientUI.PawnPromoteUI;
import gameLogic.Player.Color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PawnPromoteUITest {
	
	private Color white = Color.WHITE;

	@Test
	void testInstantiation() {
		PawnPromoteUI ui = new PawnPromoteUI(white);
		assertNotNull(ui);
	}

}
