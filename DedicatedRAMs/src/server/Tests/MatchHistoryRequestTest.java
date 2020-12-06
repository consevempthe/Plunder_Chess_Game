package server.Tests;

import client.Client;
import exceptions.IllegalRequestException;
import org.junit.jupiter.api.Test;
import server.DatabaseAccessor;
import server.MatchHistoryRequest;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchHistoryRequestTest {
        String request = "matchhistory testply9";
        MatchHistoryRequest matchHistoryRequest;
        Server server = new Server(4001);
        Client client = new Client("localhost", 4001);

        DatabaseAccessor accessor = new DatabaseAccessor();

        @Test
        void testBuildResponse() throws IllegalRequestException {
            matchHistoryRequest = new MatchHistoryRequest(request);
            String response = matchHistoryRequest.buildResponse();
            assertEquals("matchhistory success ", response);
        }
}
