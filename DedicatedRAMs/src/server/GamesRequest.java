package server;

import java.util.ArrayList;

public class GamesRequest implements Request{
	private String nickname;
	
	/*
	 * Request for all games of a specific user.
	 * Protocol: games nickname
	 */
	public GamesRequest(String request) throws IllegalRequestException {
		String [] requestSplit = request.split(" ");
		if(requestSplit.length != 2 || !requestSplit[0].equals("games"))
			throw new IllegalRequestException();
		nickname = requestSplit[1];
	}
	
	@Override
	public String buildResponse() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults = null;
		try {
			 queryResults = accessor.queryFromDatabase("select game_id from games where player1_nickname='"+ nickname +"' or player2_nickname='" + nickname + "';");
		} catch (ClassNotFoundException e) {
			return "games failed";
		}
		String j = String.join(" ", queryResults);
		return "games success " + j;
	}
}
