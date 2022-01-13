package udpConnexion;

import java.io.*; 
import java.net.*;
import java.util.ArrayList;
import Manager.Manager; 

public class udpServer extends Thread {
	
	boolean updConnected;
	
	public udpServer() {
		this.updConnected = true;
	}
	
	public void addUserDB(String pseudo, InetAddress add) {
		Manager.addUserToDB(pseudo, add);
	}
	
	public void deleteUserDB (String pseudo) {
		Manager.deleteUserInDB(pseudo);
	}
	
	public void stopServer() {
		this.updConnected = false;
	}
	
	public void run() {
		try {
			// le client est sur le port 4999
			DatagramSocket server = new DatagramSocket(4999);
			
			// Declaration des variables auxiliaires
			String reponse;
			
			String[] res;
			String pseudo;
			InetAddress add;
			String connexion;
			
			while (this.updConnected) {		
				// while(app pas fermée) 
				byte[] buf = new byte[256];
				
				// creation du packet datagramme
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				
				// reception de packets
				server.receive(packet);
				reponse = new String(packet.getData());
				
				res = reponse.split(" ");
				pseudo = res[0];
				add = InetAddress.getByName(res[1]);
				connexion = res[2];
				
				ArrayList<String> allUsers = Manager.getAllUsersConnected();
				
				System.out.println(allUsers);
				
				if (!allUsers.contains(pseudo)) {
					addUserDB(pseudo, add);
				}
				
				if (connexion.equals("deconnection") && allUsers.contains(pseudo)) {
					deleteUserDB(pseudo);
					Manager.stopServers();
					System.out.println("Deconnecteeeeeeeeeeeeeeee");
				}
				
				pseudo = "";
				connexion = "";
				
				System.out.println(this.updConnected);
			}
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
