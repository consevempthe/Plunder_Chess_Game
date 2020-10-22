package server;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


/**
 * RemoteSSHConnector class allows the program to port forward to remote ports without using the command line. 
 * It allows the Server to be accessed from any device.
 * @author DedicatedRAMs Team
 *
 */
public class RemoteSSHConnector {
	
	private static final String username = "najyfaou";
	private static final String password = "831931241";
	private int localPort;
	private int remotePort;
	private String remoteHost;
	private String host;
	private JSch jsch = new JSch();
	private Session session = null;
	/**
	 * RemoteSSHConnector constructor takes the port and host information and saves it to their respective attributes.
	 * Ex: ssh -L [localPort]:remoteHost:[remotePort] username@host -p password
	 * @param localPort - localPort number in forwarding.
	 * @param remotePort - remotePort number in forwarding.
	 * @param remoteHost - remoteHost String in forwarding.
	 * @param host - host String in forwarding.
	 */
	public RemoteSSHConnector(int localPort, int remotePort, String remoteHost, String host) {
		this.localPort = localPort;
		this.remotePort = remotePort;
		this.remoteHost = remoteHost;
		this.host = host;
	}
	
	/**
	 * connect() attempts to connect an port forwarding session. 
	 * @return boolean true if the connection was secured. False if connection failed or connection already exists.
	 */
	public boolean connect() {
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		try {
			session = jsch.getSession(username, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");
			session.setPortForwardingL(localPort, remoteHost, remotePort);
			System.out.println("Port Fowarded!");
		} catch (JSchException e) {
			System.out.println("Port Connected Already!");
			return false;
		}
		return true;
	}
	
	/**
	 * disconnect() disconnects the session. It requires that a session has been connected.
	 */
	public void disconnect() {
		session.disconnect();
	}
	
}
