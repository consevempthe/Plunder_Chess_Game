package client;

import java.io.IOException;
import java.util.Scanner;


public class ClientMain {
	public static void main(String args[]) throws IOException, InterruptedException 
    { 
        Client client = new Client("localhost", 8818); 
        if(!client.connect())
        	System.err.println("Connection Failed.");
        else
        	System.out.println("Connection Succeeded.");
        Scanner in = new Scanner(System.in);
        while(true) {
        String request = in.nextLine() + "\n";
        client.request(request);
        String msg = client.response();
        System.out.println(msg);
        	if(request.equals("quit\n"))
        			break;
        }
    } 
}