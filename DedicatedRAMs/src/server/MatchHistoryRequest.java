package server;

import exceptions.IllegalRequestException;

import java.util.ArrayList;

public class MatchHistoryRequest implements Request {
    private String nickname;

    /**
     * MatchHistoryRequest constructor takes the String from the request and fills nickname as the second argument of the request.
     * @param request - the entire request String from a Client.
     * @throws IllegalRequestException - thrown if the request does not follow the protocol for a MatchHistory.
     */
    public MatchHistoryRequest(String request) throws IllegalRequestException  {
        String[] requestSplit = request.split(" ");
        if(!requestSplit[0].equals("matchhistory"))
            throw new IllegalRequestException();
        this.nickname = requestSplit[1];
    }

    /**
     * buildResponse() accesses the database via the class DatabaseAccessor.
     * @return String - Either "matchhistory failed" or
     * "matchhistory success [id] [player1_nickname] [player2_nickname] [win] [loss] [draw]"
     * if the nickname was found in the database.
     */
    @Override
    public String buildResponse() {
        String win = "";
        String loss = "";
        String draw = "";

        DatabaseAccessor accessor = new DatabaseAccessor();
        ArrayList<String> queryResults;
        try {
            String query = "select game_id, player1_nickname, player2_nickname, win, loss, draw from games where (player1_nickname = '"
                    + nickname + "' or player2_nickname = '" + nickname + "') order by win desc, loss desc, draw desc;";
            queryResults = accessor.queryFromDatabase(query);
        } catch (ClassNotFoundException e) {
            return "matchhistory failed";
        }

        String result = "";

        for (String s : queryResults) {
            if(s == null) {
                s = "TBD";
            }
            result = result + s + " ";
        }
        return "matchhistory success " + result;
    }
}

