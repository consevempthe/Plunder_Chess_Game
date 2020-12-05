package server;

import exceptions.IllegalRequestException;

import java.io.IOException;

public class MoveRequest implements Request{
	private String to;
	private String from;
	private String opponentName;
	private String request;
	private String plunderOption;
	private String gameId;
	private final Server server;

	//format: move a2 a3 gameID opponent plunder 0 or 1
	public MoveRequest(String request, Server server) throws IllegalRequestException {
		this.server = server;
		String[] requestSplit = request.split(" ");
		if(!requestSplit[0].equals("move"))
			throw new IllegalRequestException();
		this.from = requestSplit[1];
		this.to = requestSplit[2];
		this.gameId = requestSplit[3];
		this.opponentName = requestSplit[4];
		this.plunderOption = requestSplit[5];
		this.request = request;
	}

	public String buildResponse() {
		ServerWorker worker = server.findWorker(opponentName);
		DatabaseAccessor accessor = new DatabaseAccessor();
		String insertMoveQuery = "insert into game " + gameId +  " (moves) values ('move " + from + " " + to + " " + plunderOption + "');";
		try {
			System.out.println(insertMoveQuery);
			System.out.println(accessor.changeDatabase(insertMoveQuery));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			if(worker != null)
				worker.send(this.request + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "move success"; 
	}
}
