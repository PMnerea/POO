package connections;

import java.io.*;
import java.net.*;

public class TCPsocket extends Thread{
	
	public PrintWriter output;
	public String message;
	Socket client;
	public int port, localPort;
	public String address;
	
	public void sendMessage(String message) {
		this.output.println(message);
		this.output.flush();
	}
	
	public void closeConversation() { 
		System.out.println("[TCPsocket] Closing socket");	
		try {
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TCPsocket(String address, int port, int localPort) {
		this.address = address;
		this.port = port;
		this.localPort = localPort;
		this.message="";
	}
	
	public void run() {
		
		// Connecting to distant serversocket
		System.out.println("[TCPsocket] Connecting to server...");
		try {
			this.client = new Socket(this.address, this.port);
			System.out.println("[TCPsocket] Connected");
			this.output = new PrintWriter(this.client.getOutputStream());
			
			// Sending local port to distant user
			this.sendMessage(String.valueOf(this.localPort));
			System.out.println("[TCPsocket] Sending myPort : " + String.valueOf(this.localPort));
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
	}
	
}
