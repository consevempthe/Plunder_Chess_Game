package server;

import exceptions.IllegalRequestException;

import java.util.ArrayList;
import java.util.HashSet;

/***
 * The DeleteUserRequest class is a class that is created upon a delete user request coming from a client. 
 * The request should follow this protocol: deleteuser [nickname] [password]. 
 * DeleteUserRequest will take the Request and build a response of success or failure.
 * @author DedicatedRAMs Team
 * Implements Request interface, which requires buildResponse() method.
 */
public class DeleteUserRequest implements Request {
	
	private String nickname;
	private String password;
	private ServerWorker serverWorker;
	private final Server server;
	
	/**
	 * DeleteUserRequest constructor takes the String from the request and fills nickname and password 
	 * with the second and third arguments of the request.
	 * @param request - the entire request String from a Client.
	 * @param serverWorker - serverWorker to remove nickname to upon deletion.
	 * @throws IllegalRequestException - thrown if the request does not follow the protocol for a DeleteUserRequest. 
	 */
	public DeleteUserRequest(String request, ServerWorker serverWorker, Server server) throws IllegalRequestException  {
		String[] requestSplit = request.split(" ");
		if(requestSplit.length != 3 || !requestSplit[0].equals("deleteuser"))
			throw new IllegalRequestException();
		this.nickname = requestSplit[1];
		this.password = requestSplit[2];
		this.serverWorker = serverWorker;
		this.server = server;
	}
	
	/**
	 * buildResponse() accesses the database via the class DatabaseAccessor. 
	 * It updates the database using SQL.
	 * @return String - Either "deleteuser failed" if the nickname and password did not match exactly or the database was unaccessible,
	 * or "deleteuser success [nickname]" if the nickname and password were found in the database.
	 */
	@Override
	public String buildResponse() {
		boolean c = checkUser();
		if (!c) {
			return "deleteuser failed";
		}

		boolean gamesp1 = databaseDelete("delete from games where player1_nickname ='"+ nickname +"';");
		boolean gamesp2  = databaseDelete("delete from games where player2_nickname ='"+ nickname +"';");
		boolean invitesp1  = databaseDelete("delete from invitations where nicknameTx ='"+ nickname +"';");
		boolean invitesp2  = databaseDelete("delete from invitations where nicknameRx ='"+ nickname +"';");
		boolean registration  = databaseDelete("delete from registration where nickname='"+ nickname +"' and password='" + password + "';");

		if (registration) {
			serverWorker.setNickname(null);
			
			HashSet<String> nicknames;
			nicknames = getGames(nickname);
			for (String n : nicknames) {
				ServerWorker sw = server.findWorker(n);
				if (sw != null) {
					try {
						sw.send("stopgame " + nickname + "\n");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return "deleteuser success " + nickname;
		} else {
			return "deleteuser failed";
		}
	}
	
	HashSet<String> getGames(String nickname) {
		DatabaseAccessor accessor = new DatabaseAccessor();
		HashSet<String> res = new HashSet<>();
		ArrayList<String> queryResults;
		ArrayList<String> queries = new ArrayList<>();
		queries.add("select player2_nickname from games where player1_nickname ='"+ nickname +"';");
		queries.add("select player1_nickname from games where player2_nickname ='"+ nickname +"';");

		for (String q : queries) {
			try {
				queryResults = accessor.queryFromDatabase(q);
				res.addAll(queryResults);
			} catch (ClassNotFoundException e) {
				return res;
			}
		}
		return res;
	}
	
	public boolean checkUser() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults;
		try {
			 queryResults = accessor.queryFromDatabase("select nickname, email, password from registration where nickname='"+ nickname +"' and password='" + password + "';");
		} catch (ClassNotFoundException e) {
			return false;
		}
		return queryResults.size() == 3;
	}
	
	public boolean databaseDelete(String query) {
		DatabaseAccessor accessor = new DatabaseAccessor();
		boolean res;
		try {
			 res = accessor.changeDatabase(query);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return res;
	}
}
