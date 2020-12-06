package server;

import java.util.ArrayList;

import exceptions.IllegalRequestException;

public class SearchUserStatsRequest implements Request {
	private String nickname;
	
	/**
	 * SearchUserStatsRequest constructor takes the String from the request and fills nickname as the second argument of the request.
	 * @param request - the entire request String from a Client.
	 * @throws IllegalRequestException - thrown if the request does not follow the protocol for a SearchUserStatsRequest.
	 */
	public SearchUserStatsRequest(String request) throws IllegalRequestException  {
		String[] requestSplit = request.split(" ");
		if(requestSplit.length != 2 || !requestSplit[0].equals("searchuserstats"))
			throw new IllegalRequestException();
		this.nickname = requestSplit[1];
	}
	
	/**
	 * buildResponse() accesses the database via the class DatabaseAccessor.
	 * @return String - Either "searchuserstats failed" or "searchuserstats success [win] [loss] [draw]" if the nickname was found in the database.
	 */
	@Override
	public String buildResponse() {
		String win = "0";
		String loss = "0";
		String draw = "0";

		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults;
		try {
			queryResults = accessor.queryFromDatabase("select count(win) from games where win ='" + nickname + "';");
		} catch (ClassNotFoundException e) {
			return "searchuserstats failed";
		}
		if (queryResults.size() == 1) {
			win = queryResults.get(0);
		}

		accessor = new DatabaseAccessor();
		try {
			 queryResults = accessor.queryFromDatabase("select count(loss) from games where loss ='" + nickname + "';");
		} catch (ClassNotFoundException e) {
			return "searchuserstats failed";
		}
		if (queryResults.size() == 1) {
			loss = queryResults.get(0);
		}

		accessor = new DatabaseAccessor();
		try {
			queryResults = accessor.queryFromDatabase("select count(draw) from games where (player1_nickname = '" + nickname +
					 "' or player2_nickname ='" + nickname + "') and draw = 'Y';'");
		} catch (ClassNotFoundException e) {
			return "searchuserstats failed";
		}
		if(queryResults.size() == 1) {
			draw = queryResults.get(0);
		} else {
			draw = "0";
		}
		return "searchuserstats success " + nickname +" " + win + " " + loss + " " + draw;
	}
}
