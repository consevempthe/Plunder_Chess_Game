package server;

import java.util.ArrayList;

import exceptions.IllegalRequestException;

public class LoadRequest implements Request {

	private String gameId;	
	
	public LoadRequest(String request) throws IllegalRequestException {
		String [] requestSplit = request.split(" ");
		if(requestSplit.length != 2 || !requestSplit[0].equals("load"))
			throw new IllegalRequestException();
		 gameId = requestSplit[1];
	}
	
	
	@Override
	public String buildResponse() {
		DatabaseAccessor accessor = new DatabaseAccessor();
		ArrayList<String> queryResults;
		try {
			queryResults = accessor.queryFromDatabase("select * from game" + gameId +  ";");
		} catch (ClassNotFoundException e) {
			return "load failed";
		}
		String j = String.join(" ", queryResults);
		System.out.println(j);
		return "load success " + gameId + " " + j;
	}

}
