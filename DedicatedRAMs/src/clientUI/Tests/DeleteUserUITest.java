package clientUI.Tests;

import client.Client;
import clientUI.DeleteUserUI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeleteUserUITest {
    private int port = 4457;
    private Client client = new Client("localhost", port);

    @Test
    void test() {
        DeleteUserUI DeleteUserUI = new DeleteUserUI(client);
        assertNotNull(DeleteUserUI);
        assertNotNull(DeleteUserUI.frame);
    }
}
