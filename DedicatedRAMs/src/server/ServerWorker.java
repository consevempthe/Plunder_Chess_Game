package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
	
	public OutputStream getOutputStream() {
		return outputStream;
	}
	
	private void handleClientSocket() throws IOException, InterruptedException, IllegalRequestException {
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
    	clientSocket.close();
    }

	private void handleRequest(String request) throws IOException {
		String type = request.split(" ")[0];
		System.out.println("Request(" + request + ").");
		Request r = null;
		try {
		switch(type) {
			case "register": r = new RegistrationRequest(request);
				break;
			case "login": r = new LoginRequest(request, this);
				break;
			case "invite": r = new InviteRequest(request, server);
				break;
			case "move": r = new MoveRequest(request, server);
				break;
			default:
				throw new IllegalRequestException();
		}
		}catch(IllegalRequestException e) {
			send(request + "\n");
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
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IllegalRequestException e) {
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
