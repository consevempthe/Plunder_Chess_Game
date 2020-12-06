package server.Tests;

import client.Client;
import exceptions.IllegalRequestException;
import org.junit.jupiter.api.Test;
import server.DatabaseAccessor;
import server.GamesRequest;
import server.SearchUserStatsRequest;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchUserStatsRequestTest {
    String request = "searchuserstats s00mome";
    SearchUserStatsRequest searchUserRequest;
    Server server = new Server(4001);
    Client client = new Client("localhost", 4001);

    DatabaseAccessor accessor = new DatabaseAccessor();

    @Test
    void testBuildResponse() throws IllegalRequestException {
        searchUserRequest = new SearchUserStatsRequest(request);
        String response = searchUserRequest.buildResponse();
        assertEquals("searchuserstats success s00mome 0 0 0", response, "Successful searchuserstats test");
    }
}
