package Manager;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import dbController.LocalDB;
import udpConnexion.*;
import user.User;

public class Manager {
	
	public static User localUser;
	
	private static LocalDB localDB = new LocalDB();
	
	static udpServer serverUDP = new udpServer();
	static udpClient clientUDP;
	static UDPclientDeco clientUDPdeco;
	
	// ========================== INIT APP ==================================
	
	public static void initApp(String pseudo) {
		localUser = initUser(pseudo);
		
		clientUDP = new udpClient();
		clientUDPdeco = new UDPclientDeco();
		System.out.println(localUser.pseudo);
		System.out.println(localUser.add);
		
		runServers();
		runUDPclient();
		//runUDPdeco();
	}
	
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
	
	public static void deleteUserInDB (String pseudo) {
		localDB.deleteUser(pseudo);
	}
	
	public static void updatePseudo(String p, String np) {
		localDB.updatePseudo(p, np);
	}
	
	// ========================== GESTION SERVERS ============================
	
	public static void runServers() {
		System.out.println("[Manager] running servers...");
		new Thread(serverUDP).start();
	}
	
	public static void stopServers() {
		System.out.println("[Manager] closing servers...");
		clientUDP.stopClient();
		serverUDP.stopServer(); 
		System.out.println("[Manager] servers closed");
	}
	
	public static void runUDPclient() {
		System.out.println("[Manager] send information in broadcast");
		clientUDP.start();
	}
	
	public static void runUDPdeco() {
		System.out.println("[Manager] Sending message of deconnexion");
		clientUDPdeco.start();
	}
	
	// ========================= GESTION GUI ================================
	
	public static String currentPseudo() {
		return localUser.pseudo;
	}
	
	public static InetAddress currentIP() {
		return localUser.add;
	}
	
	// ========================= LANCEMENT APPLICATION =======================
	
	public static void main(String[] args) {
		
	}
}
