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
	
	public ServerWorker(Server server, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.server = server;
	}
	
	public OutputStream getOutputStream() {
		return outputStream;
	}
	
	private void handleClientSocket() throws IOException, InterruptedException {
    	InputStream inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
    	BufferedReader clientInputReader = new BufferedReader(new InputStreamReader(inputStream));
    	String line;
    	while((line = clientInputReader.readLine()) != null) {
    		if("quit".equalsIgnoreCase(line)) {
    			break;
    		}
    		handleRequest(line + "\n");
    	}
    	System.out.println("Closing Client");
    	clientSocket.close();
    }

	private void handleRequest(String line) throws IOException {
		System.out.println("Request(" + line + ").");
		send(line);
	}

	private void send(String string) throws IOException {
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
		}
	}
}

/*String[]cmd = line.split(" ");
    		if("quit".equalsIgnoreCase(line)) {
    			break;
    		}
    		else if(cmd[0].equals("reg")) {
    			String msg = line + "\n";
    			outputStream.write(msg.getBytes());
    		}
    		else {
    			List<ServerWorker> workerList = server.getWorkerList();
    			for(ServerWorker worker: workerList) {
    				worker.send("Online");
    			}
    		}*/