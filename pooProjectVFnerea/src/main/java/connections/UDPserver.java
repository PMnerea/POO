package connections;

import java.io.*; 
import java.net.*;
import java.util.ArrayList;
import manager.Manager; 

public class UDPserver extends Thread {
	
	boolean updConnected;
	
	public UDPserver() {
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
				System.out.println("[UDPserver]" + res[0] + "  " + res[1] + "  " + res[2]);
				System.out.println("[UDPserver]" + pseudo + "  " + add + "  " + connexion);
				
				ArrayList<String> allUsers = Manager.getAllUsersConnected();
				
				System.out.println(allUsers);
				
				// Si l'utilisateur est déjà dans la bdd alors on ne l'ajoute pas 
				if (!allUsers.contains(pseudo)) {
					addUserDB(pseudo, add);
				}
				
				if (connexion.equals("deconnection") && allUsers.contains(pseudo)) {
					deleteUserDB(pseudo);
					Manager.stopServers();
				}
				
				// On vide pour la prochaine reception
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

