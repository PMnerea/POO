package conversation;

import gui.*;
import tcpConnexion.*;
import user.User;

import java.net.*;

public class convoController {
	User distantUser;
	TCPclient client;
	TCPserver server;
	chat gui;
	
	// CONSTRUCTEUR
	public convoController(User distantUser, TCPclient client, TCPserver server) { 
		this.distantUser = distantUser;
		System.out.println("distantu");
		this.client = client;
		System.out.println("client");
		this.server = server;
		System.out.println("server");
		this.gui = new chat(distantUser, this);
		System.out.println("gui");
	}
	
	public void sendMessage(String message) {
		// Quand l'utilisateur pousse la bouton, le controller va envoyer le msg
		// Et la stocker dans la bdd
		// FInn ut ka du gjor med heure osv
		this.server.sendMessage(message);
	}

	public void receiveMessage(String message) {
		// Quand on recoit un message, on va le afficher sur le gui
		this.gui.newMessage(message);
	}

	public static void main(String[] args) {
		String testName1 = "user1";
		String testName2 = "user2";
		User user1;
		User user2;
		InetAddress add1, add2;
		System.out.println("her iallefall");
		try {
			user1 = User.initLocalUser(testName1);
			user2 = User.initLocalUser(testName2);
			TCPserver server1 = new TCPserver(); // 5043 distant user 1
			server1.run(user2, 5043);
			TCPserver server2 = new TCPserver(); // 5043 distant user 2
			server1.run(user1, 5042);
			TCPclient client1 = new TCPclient(); // 5042 local user 1
			client1.run(user2, 5042);
			TCPclient client2 = new TCPclient(); // 5043 local user 2
			client2.run(user1, 5043);
			
			add1 = InetAddress.getLocalHost();
			add2 = InetAddress.getLocalHost();
			user1 = User.initLocalUser(testName1);
			user2 = User.initLocalUser(testName2);
			convoController ctrl1 = new convoController(user2, client1, server1);
			convoController ctrl2 = new convoController(user1, client2, server2);
			System.out.println("HIT");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
