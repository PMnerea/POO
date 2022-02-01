package manager;

import gui.*;
import services.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import connections.*;

public class ConvoManager extends Thread {
	
	TCPserversocket serversocket;
	TCPsocket socket;
	String localhost;
	ChatGUI gui;
	int localPort, distantPort;
	String distantIP;
	String distantPseudo;
	ArrayList<Message> history = new ArrayList<Message>();
	
	public ConvoManager(int localPort, int distantPort, String distantIP, String distantPseudo) {
		this.localPort = localPort;
		this.distantPort = distantPort;
		this.distantIP = distantIP;
		this.distantPseudo = distantPseudo;
		this.history = Manager.getHistory(distantIP);
		System.out.println("[ConvoManager] Creating serversocket...");
		this.serversocket = new TCPserversocket(this.localPort, this);	
		
		this.gui = new ChatGUI(this, this.distantPseudo, this.history, this.distantIP);
	}
	
	// Alert the gui that user disconnected
	public void distantUserDisconnected(){
		this.gui.distantUserDisconnected();
	}
	
	// Alert the gui that user refused the request
	public void connectionRefused(){
		this.gui.connectionRefused();
	}
	
	public void run() {
		System.out.println("[ConvoManager] Running");
		// Start listening for messages
		this.serversocket.start();
		this.gui.notYetConnected();
		
		// Wait until the distant user has connected and sent their port number
		while (this.distantPort == 0) {
			System.out.println("dport: " + this.distantPort);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		};
		
		// When connected show history messages in gui
		this.gui.getHistory();
		
		// Create socket and connect to distant user
		System.out.println("[ConvoManager] Creating socket... ");
		this.socket = new TCPsocket(this.distantIP,this.distantPort, this.localPort);
		System.out.println("[ConvoManager] Socket Created");
		this.socket.start();
	}
	
	// Alert gui of new message received
	public void newMessage(String message) {
		gui.newMessage(message);
	}
	
	// Send message to distant user from socket, and store it in the database
	public void sendMessage(String message) {
		this.socket.sendMessage(message);
		if ((!message.equals("CONVO_REFUSED")) && (!message.equals("CONVO_DISCONNECTED"))) {
			Manager.storeSentMessage(message, this.distantIP);
		}
		
	}
	
	// Store the port number received from distant user
	public void setPort(int receivedPort){
		if (this.distantPort == 0) {
			System.out.println("[convoManager] Setting distantPort to: " + receivedPort);
			this.distantPort = receivedPort;
		}
	}
	
	// Close gui, socket and serversocket
	public void closeConversation() {
		System.out.println("[ConvoManager] Closing conversation...");
		this.gui.closeConversation();
		this.socket.closeConversation();
		this.serversocket.closeConversation();
		
	}
	
	public static void main(String[] args) {	
	}
}