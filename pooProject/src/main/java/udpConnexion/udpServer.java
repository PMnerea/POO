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
	
	public void run() {
		try {
			// le client est sur le port 4999
			DatagramSocket server = new DatagramSocket(4999);
			
			// while(app pas fermée) 
			byte[] buf = new byte[256];
			
			while (this.updConnected) {			
				// creation du packet datagramme
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				
				// reception de packets
				server.receive(packet);
				String reponse = new String(packet.getData());
				
				String[] res = reponse.split(" ");
				String pseudo = res[0];
				InetAddress add = InetAddress.getByName(res[1]);
				
				ArrayList<String> allUsers = Manager.getAllUsersConnected();
				if (!allUsers.contains(pseudo)) {
					addUserDB(pseudo, add);
				}
			}
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
