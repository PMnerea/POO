package Manager;

import java.net.InetAddress;
import dbController.LocalDB;
import udpConnexion.udpServer;

public class Manager {
	
	private static LocalDB localDB = new LocalDB();
	
	static udpServer serverUDP = new udpServer();
	
	// ========================== GESTION LOCAL DB ============================
	
	public static void addUserToDB (String pseudo, InetAddress add) {
		localDB.addUser(pseudo, add);
	}
	
	// ========================== GESTION SERVERS ============================
	public static void runServers() {
		System.out.println("[Manager] running servers");
		new Thread(serverUDP).start();
	}
	
	public static void main(String[] args) {
		runServers();
	}
}
