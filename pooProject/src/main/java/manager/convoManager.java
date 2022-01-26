package manager;

import java.net.DatagramSocket;
import gui.*;
import message.*;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import tcpConnexion.*;
import user.User;

// TODO: Kan slette mange av timeoutan (egt alle) - trengs kun for testing siden man sende te samme maskin
public class convoManager extends Thread {
	
	TCPserversocket serversocket;
	TCPsocket socket;
	String localhost;
	ChatGUI gui;
	int localPort, distantPort;
	String distantIP;
	String distantPseudo;
	ArrayList<Message> history = new ArrayList<Message>();
	
	public convoManager(int localPort, int distantPort, String distantIP, String distantPseudo) {
		this.localPort = localPort;
		this.distantPort = distantPort;
		this.distantIP = distantIP;
		this.distantPseudo = distantPseudo;
		this.history = Manager.getHistory(distantIP);
		// Message mess = new Message("2022-01-15 15:15:15", "10.1.5.145", "10.1.5.147", "haaaaaaaaiiiiii");
		// this.history.add(mess);
		
		System.out.println(" ");
		System.out.println("[ConvoManager] Starting conversation with data : ");
		System.out.println("[ConvoManager] LocalPort : " + localPort);
		System.out.println("[ConvoManager] distantPort : " + distantPort);
		System.out.println("[ConvoManager] distantIP : " + distantIP);
		System.out.println("[ConvoManager] distantPseudo : " + distantPseudo);
		System.out.println("  ");		
		
		System.out.println("[ConvoManager] Database : ");
		
		for (Message message : this.history) {
			message.printMessage();
		}
		
		System.out.println("[ConvoManager] Creating serversocket...");
		this.serversocket = new TCPserversocket(this.localPort, this);	
		
		this.gui = new ChatGUI(this, this.distantPseudo, this.history, this.distantIP);
	}
	
	public void run() {
		System.out.println("[ConvoManager] Running");
		this.serversocket.start();
		while (this.distantPort == 0) {
			System.out.println("dport: " + this.distantPort);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		};
		
		System.out.println("[ConvoManager] Creating socket... (with port: " + this.distantPort + " )");
		this.socket = new TCPsocket(this.distantIP,this.distantPort, this.localPort);
		System.out.println("[ConvoManager] Socket Created");
		
		/*
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		
		this.socket.start();
	}
	
	public void newMessage(String message) {
		gui.newMessage(message);
	}
	
	public void sendMessage(String message) {
		this.socket.sendMessage(message);
		Manager.storeSentMessage(message, this.distantIP);
	}
	
	public void setPort(int receivedPort){
		if (this.distantPort == 0) {
			System.out.println("[convoManager] Setting distantPort to: " + receivedPort);
			this.distantPort = receivedPort;
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("local port, dist port, ip");
		Scanner s = new Scanner(System.in);
		int local = s.nextInt();
		int distant = s.nextInt();
		String ipad = s.next();
		System.out.println(" localport : " + local + "  distant port : " + distant + "  ip : " + ipad);
		
		
		convoManager cm = new convoManager(local,distant,ipad, "tull"); // TODO: test med 0 som distant
		cm.start();
		
	}
}
