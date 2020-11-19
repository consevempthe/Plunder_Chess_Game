package server;

import exceptions.IllegalRequestException;

import java.io.IOException;

public class GameEndRequest implements Request {
	private String opponent;
	private String gameID;
	private String winningColor;
	private String outcome;
	private final Server server;
	
	/**
	 * Request to start game with two users.
	 * Protocol: game player1nickname player2nickname gameID
	 * @param request - the entire request String from a Client.
	 * @param s - server, for finding p2 ServerWorker
	 * @throws IllegalRequestException - thrown if the request does not follow the protocol for a GameRequest.
	 */
	public GameEndRequest(String request, Server s) throws IllegalRequestException {
		this.server = s;
		String[] requestSplit = request.split(" ");
		if (requestSplit.length != 5 || !requestSplit[0].equals("end"))
			throw new IllegalRequestException();
		outcome = requestSplit[1];
		opponent = requestSplit[2];
		gameID = requestSplit[3];
		winningColor = requestSplit[4];
	}

	/**
	 * buildResponse() searches for the other player's ServerWorker
	 * and sends a response to both players.
	 * "game success player1nickname player2nickname gameID".
	 * Players are swapped for opposite player for when the game starts on their end.
	 */
	@Override
	public String buildResponse() {
		ServerWorker op = server.findWorker(opponent);
		if (op == null)
			return "end failed to send";
		try {
			op.send("end " + outcome + " " + opponent + " " + gameID + " " + winningColor + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "end success";
	}

}