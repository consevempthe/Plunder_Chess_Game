package clientUI.Tests;

import client.Player.Color;
import clientUI.PawnPromoteUI;
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
