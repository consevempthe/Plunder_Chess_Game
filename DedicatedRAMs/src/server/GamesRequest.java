package server;

import exceptions.IllegalRequestException;

import java.util.ArrayList;

public class GamesRequest implements Request{
	private String nickname;
	
	/*
	 * Request for all games of a specific user.
	 * Protocol: games nickname
	 * @param request - the entire request String from a Client.
	 * @throws IllegalRequestException - thrown if the request does not follow the protocol for a GamesRequest. See class description.
	 */
	public GamesRequest(String request) throws IllegalRequestException {
		String [] requestSplit = request.split(" ");
		if(requestSplit.length != 2 || !requestSplit[0].equals("games"))
			throw new IllegalRequestException();
		nickname = requestSplit[1];
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
		ArrayList<String> queryResults;
		try {
			 queryResults = accessor.queryFromDatabase("select game_id, player1_nickname, player2_nickname from games where player1_nickname='"+ nickname +"' or player2_nickname='" + nickname + "';");
		} catch (ClassNotFoundException e) {
			return "games failed";
		}
		String j = String.join(" ", queryResults);
		return "games success " + j;
	}
}
