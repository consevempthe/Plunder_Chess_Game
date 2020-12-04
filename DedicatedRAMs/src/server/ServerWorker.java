package server;

import exceptions.IllegalRequestException;

import java.io.*;
import java.net.Socket;

public class ServerWorker extends Thread {

	private final Socket clientSocket;
	private final Server server;
	private OutputStream outputStream;
	private String nickname;
	
	public ServerWorker(Server server, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.server = server;
	}
	
	private void handleClientSocket() throws IOException {
    	InputStream inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
    	BufferedReader clientInputReader = new BufferedReader(new InputStreamReader(inputStream));
    	String line;
    	while((line = clientInputReader.readLine()) != null) {
    		if("quit".equalsIgnoreCase(line)) {
    			break;
    		}
    		handleRequest(line);
    	}
    	System.out.println("Closing Client");
    	this.nickname = null; //Delete serverWorker instead.
    	clientSocket.close();
    }

	private void handleRequest(String request) throws IOException {
		String type = request.split(" ")[0];
		System.out.println("Request(" + request + ").");
		Request r;
		try {
		switch(type) {
			case "register": r = new RegistrationRequest(request);
				break;
			case "login": r = new LoginRequest(request, this);
				break;
			case "invite": r = new InviteRequest(request, server);
				break;
			case "game": r = new GameRequest(request, this, server);
				break;
			case "move": r = new MoveRequest(request, server);
				break;
			case "deleteuser": r = new DeleteUserRequest(request, this, server);
				break;
			case "games": r = new GamesRequest(request);
				break;
			default:
				throw new IllegalRequestException();
		}
		}catch(IllegalRequestException e) {
			send(request + "\n");
			return;
		}
		send(r.buildResponse() + "\n");
	}

	public void send(String string) throws IOException {
		outputStream.write(string.getBytes());
	}
	
	@Override
	public void run() {
		try {
			handleClientSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

}
