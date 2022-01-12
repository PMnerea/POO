package Manager;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import dbController.LocalDB;
import udpConnexion.udpClient;
import udpConnexion.udpServer;
import user.User;

public class Manager {
	
	public static User localUser;
	
	private static LocalDB localDB = new LocalDB();
	
	static udpServer serverUDP = new udpServer();
	static udpClient clientUDP;
	
	// ========================== INIT USER ==================================
	
	public static User initUser(String p) {
		try {
			return User.initLocalUser(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	// ========================== GESTION LOCAL DB ============================
	
	public static ArrayList<String> getAllUsersConnected(){
		return localDB.getAllPseudos();
	}
	
	public static void addUserToDB (String pseudo, InetAddress add) {
		localDB.addUser(pseudo, add);
	}
	
	// ========================== GESTION SERVERS ============================
	public static void runServers() {
		System.out.println("[Manager] running servers");
		new Thread(serverUDP).start();
	}
	
	public static void runUDPclient() {
		System.out.println("[Manager] send information in broadcast");
		clientUDP.start();
	}
	
	public static void main(String[] args) {
		localUser = initUser("qsqs");
		
		clientUDP = new udpClient();
		System.out.println(localUser.pseudo);
		System.out.println(localUser.add);
		
		runServers();
		runUDPclient();
	}
}
