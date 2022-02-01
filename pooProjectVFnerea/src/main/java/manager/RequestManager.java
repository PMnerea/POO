package manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.TimeUnit;
import services.*;
import connections.TCPsocket;
import gui.AcceptRequest;

public class RequestManager extends Thread {
	static int port = 55555;
	int response = 0;
	int nextPort;
	public boolean stillRunning;
	
	public int getNextPort() {
		return this.nextPort;
	}
	
	public void setNextPort(int nextPort) {
		this.nextPort = nextPort;
	}
	
	public void augmentNextPort() {
			this.nextPort++;
	}
	
	public RequestManager() {
		this.nextPort = 55556;
		this.stillRunning = true;
	}
	
	public Request analyseRequest(String input) {
		
		// Reading distant user's IP and port from the arriving request
		Request request = new Request();
		String[] parts = input.split(" ");
		if (parts.length == 2) {
			request.setIPaddress(parts[0]);
			request.setPort(Integer.valueOf(parts[1]));
		} else {
			System.out.println("[RequestManager] Received a bad request ( IP : " + request.IPaddress + "  Port : " + request.port + " )");
		}
		return request;
	}
	
	public void treatRequest(Request request) {
		
		// Getting pseudo from the local database
		if (!(request.port==0) && !(request.IPaddress == "")) {
			String distantPseudo = null;
			while (distantPseudo == null) {
				try {
					distantPseudo = Manager.getUserByIPFromDB(InetAddress.getByName(request.IPaddress));
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// Asking user if they accept the conversation request
			AcceptRequest acceptRequest = new AcceptRequest(this,distantPseudo);
			while(this.response == 0) {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			acceptRequest.closeWindow();
			
			// Starting conversation or sending CONVO_REFUSED to distant user
			if (this.response == 1) {
				Manager.startConversation(this.nextPort, request.port, request.IPaddress);
				this.augmentNextPort();
			} else {
				TCPsocket socket = new TCPsocket(request.IPaddress, request.port, 10000);
				socket.run();
				socket.sendMessage("CONVO_REFUSED");
				socket.closeConversation();
			}
			this.setResponse(0);
		} else {
			System.out.println("[RequestManager] Could not accept conversation");
		}
				
	}
	
	public void setResponse(int response) {
		this.response = response;
	}
	
	public void run() {
		try {
			// Creating serversocket listening for conversation requests, always on port 55555
			ServerSocket server = new ServerSocket(port);
			System.out.println("[RequestManager] Listening...");
			String message;
			Request request;
			
			// While app is still running we're waiting for a client to connect
			while (this.stillRunning) {
				System.out.println("[RequestManager] Waiting for client to connect...");
				Socket client = new Socket();
				client = server.accept();
				System.out.println("[RequestManager] Client connected");
				
				// Read input from client
				BufferedReader input  = new BufferedReader(new InputStreamReader(client.getInputStream()));			
				message = input.readLine();
				
				// Analysing and treating request from client, should be an IP address and a port number
				request = this.analyseRequest(message);
				System.out.println("[RequestManager] Treating request...");
				this.treatRequest(request);
				System.out.println("[RequestManager] Request treated...");
				
				// Disconnect from client and continue listening for connections
				client.close();
			}
			server.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}