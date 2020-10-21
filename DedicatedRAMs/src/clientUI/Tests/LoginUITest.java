package clientUI.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import client.Client;
import clientUI.LoginUI;

class LoginUITest {

	@Test
	void test() {
		//Basically, everything is private except the constructor. The interface of the LoginUI class to other classes is limited.
		LoginUI loginUI = new LoginUI(new Client("localhost", 8188));
		assertNotNull(loginUI);
	}

}
