package server;

import java.io.IOException;

public class MoveRequest implements Request{
	private String to;
	private String from;
	private String opponentName;
	private String request;
	private final Server server;

	//format: move a2 a3 gameID opponent
	public MoveRequest(String request, Server svr) throws IllegalRequestException {
		this.server = svr;
		String[] requestSplit = request.split(" ");
		if(requestSplit.length != 5 || !requestSplit[0].equals("move"))
			throw new IllegalRequestException();
		this.to = requestSplit[1];
		this.from = requestSplit[2];
		this.opponentName = requestSplit[4];
		this.request = request;
	}

	public String buildResponse() {
		ServerWorker worker = server.findWorker(opponentName);
		try {
			worker.send(this.request + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return request; 
	}
}
