package client;

import java.net.*;
import java.io.*; 


public class Client 
{ 
	private final String serverName;
	private final int serverPort;  
	private Socket socket; 
	private InputStream serverIn;
	private BufferedReader bufferedIn;
	private OutputStream serverOut;
	private User user = new User(null, null, null);

    public Client(String address, int port) 
    { 
    	this.serverName = address;
    	this.serverPort = port;
    }
    
    public User getUser() {
    	return user;
    }

    public void request(String request) throws IOException {
    	serverOut.write(request.getBytes());
    }

    public String response() throws IOException {
		return bufferedIn.readLine();
    }
    
	public boolean connect() {
		try {
			this.socket = new Socket(getServerName(), getServerPort());
			this.serverIn = socket.getInputStream();
			this.serverOut = socket.getOutputStream();
			this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
			startResponseReader();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void startResponseReader() {
		Thread t = new Thread() {
			@Override
			public void run() {
				readResponsesLoop();
			}
		};
		t.start();
	}

	private void readResponsesLoop() {
		String response;
		try {
			while( (response = bufferedIn.readLine()) != null) {
				handleResponse(response);
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void handleResponse(String response) {
		Response r = null;
		String responseType = response.split(" ")[0];
		switch(responseType) {
			case "login": r = new LoginResponse(response, user);
				break;
			case "register": r = new RegistrationResponse(response, user);
				break;
			case "invite": r = new InviteResponse(response, user);
				break;
			//Other responses
		}
		r.handleResponse();
	}


	public String getServerName() {
		return serverName;
	}
	
	public int getServerPort() {
		return serverPort;
	}
	
} 