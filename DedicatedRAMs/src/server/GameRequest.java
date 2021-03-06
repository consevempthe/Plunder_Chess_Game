package server;

import exceptions.IllegalRequestException;

import java.io.IOException;

public class GameRequest implements Request {
	private String p1;
	private String p2;
	private String gameID;
	private final Server server;
	private ServerWorker serverWorker;
	
	/**
	 * Request to start game with two users.
	 * Protocol: game player1nickname player2nickname gameID
	 * @param request - the entire request String from a Client.
	 * @param sw - p1 ServerWorker
	 * @param s - server, for finding p2 ServerWorker
	 * @throws IllegalRequestException - thrown if the request does not follow the protocol for a GameRequest.
	 */
	public GameRequest(String request, ServerWorker sw, Server s) throws IllegalRequestException {
		this.server = s;
		this.serverWorker = sw;
		String[] requestSplit = request.split(" ");
		if (requestSplit.length != 4 || !requestSplit[0].equals("game"))
			throw new IllegalRequestException();
		p1 = requestSplit[1];
		p2 = requestSplit[2];
		gameID = requestSplit[3];
	}

	/**
	 * buildResponse() searches for the other player's ServerWorker
	 * and sends a response to both players.
	 * "game success player1nickname player2nickname gameID".
	 * Players are swapped for opposite player for when the game starts on their end.
	 */
	@Override
	public String buildResponse() {
		ServerWorker op = server.findWorker(p2);
		DatabaseAccessor accessor = new DatabaseAccessor();
		String query = "create table " + "game" + gameID + " (moves VARCHAR(32));";
		try {
			System.out.println(accessor.changeDatabase(query));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		String insertionQuery = "insert into games (game_id, player1_nickname, player2_nickname) values ('"+ gameID + "', '" + p1 + "', '" + p2 + "');";
		try {
			System.out.println(accessor.changeDatabase(insertionQuery));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		if (op == null)
			//return "User " + p2 + " not online";
			return "game success " + p1 + " " + p2 + " " + gameID; //TODO make sure this works with async play...
		try {
			op.send("game success " + p1 + " " + p2 + " " + gameID + "\n");
			return "game success " + p1 + " " + p2 + " " + gameID;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "game success " + p1 + p2 + gameID;
	}

}