package server;

import java.io.IOException;

public class GameRequest implements Request {
	private String p1;
	private String p2;
	private String gameID;
	private final Server server;
	private ServerWorker serverWorker;

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

	@Override
	public String buildResponse() {
		ServerWorker op = server.findWorker(p2);
		if (op == null)
			return "User " + p2 + "not online";
		try {
			op.send("game success " + p2 + " " + p1 + " " + gameID + "\n");
			return "game success " + p1 + " " + p2 + " " + gameID;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// game success player1name player2name gameId
		// for oppo switch p2n p1n
		return "game success " + p1 + p2 + gameID;
	}

}