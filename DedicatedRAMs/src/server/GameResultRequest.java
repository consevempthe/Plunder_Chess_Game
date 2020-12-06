package server;

import exceptions.IllegalRequestException;

public class GameResultRequest implements Request {
    private String gameID;
    private String winnerName;
    private String loserName;
    private String draw;

    /**
     * Request for writing game result.
     * Protocol: gameresult gameid winnerName loserName draw
     * @param request - the entire request String from a Client.
     * @throws IllegalRequestException - thrown if the request does not follow the protocol for a Request. See class description.
     */
    public GameResultRequest(String request) throws IllegalRequestException {
        String [] requestSplit = request.split(" ");
        if(requestSplit.length != 5 || !requestSplit[0].equals("gameresult"))
            throw new IllegalRequestException();
        gameID = requestSplit[1];
        winnerName = requestSplit[2];
        loserName = requestSplit[3];
        draw = requestSplit[4];

    }

    /**
     * buildResponse() accesses the database via the class DatabaseAccessor.
     * It queries the database using SQL for game_ids where nickname is player1 or player2.
     * @return String - Either "games failed" if db cannot be accessed or
     * "login success [game ids as strings separated by spaces]".
     */
    @Override
    public String buildResponse() {
        DatabaseAccessor accessor = new DatabaseAccessor();
        boolean queryResults;
        try {
            String q = "update games set win='"
                    + winnerName + "', loss='" + loserName + "', draw='" + draw
                    + "' where game_id='" + gameID + "';";
            queryResults = accessor.changeDatabase(q);
        } catch (ClassNotFoundException e) {
            return "games failed";
        }
        return (queryResults) ? "gameresult success" : "gameresult fail";
    }
}
