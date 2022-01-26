package manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.TimeUnit;

import request.Request;

public class RequestManager extends Thread {
	static int port = 55555;
	int nextPort;
	
	public int getNextPort() {
		return this.nextPort;
	}
	
	public void setNextPort(int nextPort) {
		this.nextPort = nextPort;
	}
	
	public void augmentNextPort() {
		if (this.nextPort == 5556) {
			this.nextPort = 5558;
		} else {
			this.nextPort++;
		}
	}
	
	public RequestManager() {
		this.nextPort = 55556;
	}
	
	public Request analyseRequest(String input) {
		// System.out.println("AnalyseRequest : " + input);
		Request request = new Request();
		String[] parts = input.split(" ");
		// System.out.println("IP: " + parts[0] + "  port : " + parts[1]);
		if (parts.length == 2) {
			request.setIPaddress(parts[0]);
			request.setPort(Integer.valueOf(parts[1]));
		} else {
			System.out.println("[RequestManager] Received a bad request ( IP : " + request.IPaddress + "  Port : " + request.port + " )");
		}
		return request;
	}
	
	public void treatRequest(Request request) {
		if (!(request.port==0) && !(request.IPaddress == "")) {
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Manager.startConversation(this.nextPort, request.port, request.IPaddress);
			// convoManager cm = new convoManager(nextPort, request.port, request.IPaddress); 
			// cm må tilpasses any port/IPaddresse 
			// distant port og ip av obv reasons 
				// distant port = 0 hvis man initialize convo
				// må vent på portnr i første melding fra distant i så fall
				// trur det kan gjøres med if i samme cm
			// local fordi første melding må vær local port som dem kan svare på
			this.augmentNextPort();
			System.out.println("[RequestManager] Next port : " + this.nextPort);
			
		} else {
			System.out.println("[RequestManager] Could not accept conversation");
		}
				
	}
	
	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("[RequestManager] Listening...");
			String message;
			Request request;
			
			while (true) { // while app open
				System.out.println("[RequestManager] Waiting for client to connect...");
				Socket client = new Socket();
				client = server.accept();
				System.out.println("[RequestManager] Client connected");
				
				BufferedReader input  = new BufferedReader(new InputStreamReader(client.getInputStream()));			
				message = input.readLine();
				System.out.println("[RequestManager] Message received : " + message);
				request = this.analyseRequest(message);
				System.out.println("[RequestManager] Treating request...");
				this.treatRequest(request);
				System.out.println("[RequestManager] Request treated...");
				client.close();
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
