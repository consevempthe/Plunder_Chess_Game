package clientUI.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import client.Player;
import clientUI.CheckUI;

class CheckUITest {

	@Test
	void testNotNull() {
		CheckUI ui = new CheckUI(Player.Color.WHITE, "player1", "player2");
		assertNotNull(ui);
	}

}
