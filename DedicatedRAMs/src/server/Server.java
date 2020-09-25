package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
	
	private final int serverPort;
	private ArrayList<ServerWorker> workerList = new ArrayList<>();
	
	
	public Server(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public List<ServerWorker> getWorkerList() {
		return workerList;
	}
	
	public int getServerPort() {
		return serverPort;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(getServerPort());
			while(true) {
				System.out.println("Ready to accept connection...");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Accepted Connection From: " + clientSocket);
				ServerWorker serverWorker = new ServerWorker(this, clientSocket);
				workerList.add(serverWorker);
				serverWorker.start();	
			}
		}catch(IOException e) {
			e.printStackTrace();
		}

	}
}