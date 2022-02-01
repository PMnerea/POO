package connections;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import manager.ConvoManager;

public class TCPserversocket extends Thread {
	public int port;
	ServerSocket server;
	Socket client;
	ConvoManager cm;
	public boolean stillConnected;
	
	public TCPserversocket(int port, ConvoManager cm) {
		this.port = port;
		this.cm = cm;
		this.stillConnected = true;
		}
	
	public void run() {
		try {
			// Creating serversocket and waiting for distant user to connect
			System.out.println("[TCPserver] Waiting for client...");
			this.server = new ServerSocket(this.port);
			this.client = new Socket();
			this.client = this.server.accept();
			System.out.println("[TCPserver] Client connected");
			
			// The first message is the port number of the distant user
			BufferedReader input  = new BufferedReader(new InputStreamReader(this.client.getInputStream()));			
			String message = input.readLine();
			this.cm.setPort(Integer.valueOf(message));
			
			// Reading messages until distant user sends CONVO_DISCONNECTED or CONVO_REFUSED, if so, alert ConvoManager that conversation is ended
			while (!(message == null)) {
				if ((message.toString().equals("CONVO_DISCONNECTED"))) {
					this.cm.distantUserDisconnected();
					break;
				} else if ((message.toString().equals("CONVO_REFUSED"))) {
					this.cm.connectionRefused();
					break;
				} else { 	
				message = input.readLine();
				this.newMessage(message);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Alert ConvoManager of new message
	public void newMessage(String message) {
		this.cm.newMessage(message);
	}
	
	public void closeConversation() { 
		System.out.println("[TCPserversocket] Closing server");
		try {
			this.server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	}
}
