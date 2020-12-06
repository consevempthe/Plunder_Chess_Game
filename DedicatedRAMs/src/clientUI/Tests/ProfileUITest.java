package clientUI.Tests;

import client.Client;
import client.User;
import clientUI.ProfileUI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Server;
import server.ServerWorker;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProfileUITest {

    private int port = 4457;
    private Server server = new Server(port);
    private Client client = new Client("localhost", port);

    @BeforeEach
    public void setup() {
        server.start();
        client.connect();
    }

    @Test
    void test() {
        client.user = new User("cathy", "", "");
        ProfileUI profileUI = new ProfileUI(client);
        assertNotNull(profileUI);
        assertNotNull(profileUI.frame);
    }
}
