package server;

/**
 * The InviteRequest class is a class that is created upon an invite request from the client. The request should follow this protocol: invite (add/remove) [nicknameRx] [nicknameTx] [gameID]. 
 * InviteRequest fills based on the request arguments, places or removes the invite from the database, and looks to send it to the receiving Client if they are online.
 * @author DedicatedRAMs NF
 * Implements Request interface, which requires buildResponse() method.
 */
public class InviteRequest implements Request {

	private boolean isAdd;
	private String nicknameRx;
	private String nicknameTx;
	private String gameID;
	
	public InviteRequest(String request) {
		
	}

	@Override
	public String buildResponse() {
		
		return null;
	}

}
