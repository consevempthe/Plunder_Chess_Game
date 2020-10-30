package server;

import java.io.IOException;

/**
 * The InviteRequest class is a class that is created upon an invite request from the client. The request should follow this protocol: invite (add/remove) [nicknameRx] [nicknameTx] [gameID]. 
 * InviteRequest fills based on the request arguments, places or removes the invite from the database, and looks to send it to the receiving Client if they are online.
 * @author DedicatedRAMs NF
 * Implements Request interface, which requires buildResponse() method.
 */
public class InviteRequest implements Request {

	private boolean isAdd = false;
	private String nicknameRx;
	private String nicknameTx;
	private String gameID;
	private final Server server;
	
	public InviteRequest(String request, Server server) throws IllegalRequestException {
		String [] requestSplit = request.split(" ");
		if(requestSplit.length != 5 || !requestSplit[0].equals("invite"))
			throw new IllegalRequestException();
		if(requestSplit[1].equalsIgnoreCase("add")) 
			isAdd = true;
		nicknameRx = requestSplit[2];
		nicknameTx = requestSplit[3];
		gameID = requestSplit[4];
		this.server = server;
	}
	
	/**
	 * buildResponse() is Overridden from the Request interface. Depending on whether the request is adding or removing an invite, the method will make changes to
	 * the database in the invitations table. If adding, a successful response is "invite sent", if removing, a successful response is "invite removed".
	 * "invite failed" only occurs when the database is unaccessible.
	 */
	@Override
	public String buildResponse() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		boolean success;
		if(isAdd)
			success = performInvitationQuery(accessor, "insert into invitations (nicknameRx, nicknameTx, gameID) values ('"+ nicknameRx + "', '" + nicknameTx + "', '" + gameID + "');");
		else
			success = performInvitationQuery(accessor, "delete from invitations where nicknameRx='" + nicknameRx + "' and nicknameTx='" + nicknameTx + "' and gameID='" + gameID + "';");
		boolean oppoSent = trySendingNow();
		if(success && isAdd && oppoSent)
			return "invite sent "  + nicknameTx + " " + nicknameRx + " " + gameID;
		else if(success && oppoSent)
			return "invite removed "  + nicknameTx + " " + nicknameRx + " " + gameID;
		return "invite failed";
	}

	/**
	 * performInvitationQuery() is the helper method for building the response. It attempts to insert or remove information to the invitation table based on the query it is sent.
	 * @param accessor - DatabaseAccessor to access the database.
	 * @param query - String to be queried in the database.
	 * @return boolean - true if successful, false if fails to complete query, which should only be when query is incorrect or database inaccessible.
	 */
	private boolean performInvitationQuery(DatabaseAccessor accessor, String query) {
		boolean isCorrect = false;
		try {
			isCorrect = accessor.changeDatabase(query);
		} catch (ClassNotFoundException e) {
			return isCorrect;
		}
		return isCorrect;
	}

	/**
	 * trySendingNow() attempts to send the invitation directly to another Client via a ServerWorker. First, it attempts to find the worker based on its nickname.
	 * Then, if it finds one, it will send the invitation on to the receiver's Client.
	 */
	private boolean trySendingNow() {
		ServerWorker worker = server.findWorker(nicknameTx);
		if(worker == null)
			return false;
		try {
			if(isAdd)
				worker.send("invite add " + nicknameRx + " " + nicknameTx + " " + gameID + "\n");
			else
				worker.send("invite remove " + nicknameRx + " " + nicknameTx + " " + gameID + "\n");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
