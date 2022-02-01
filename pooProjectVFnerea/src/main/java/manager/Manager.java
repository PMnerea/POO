package manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import dbController.RemoteDB;
import dbController.LocalDB;
import gui.HomePage;
import gui.Login;
import services.*;
import connections.*;
import java.net.*;

public class Manager {
	
	public static User localUser;
	
	private static LocalDB localDB = new LocalDB();
	
	static UDPserver serverUDP = new UDPserver();
	static UDPclient clientUDP;
	static UDPclientDeco clientUDPdeco;
	
	private static RequestManager requestManager = new RequestManager();
	private static RemoteDB bddDistante = new RemoteDB();
	
	// ========================== INIT APP ==================================
	
	public static void initApp(String pseudo) {
		localUser = initUser(pseudo);
		
		clientUDP = new UDPclient();
		clientUDPdeco = new UDPclientDeco();
		System.out.println("[Manager] User pseudo : " + localUser.pseudo);
		System.out.println("[Manager] User ip : " + localUser.add);
		
		runUDPclient();
		runRequestManager();
	}
	
	public static void stopApp() {
		deleteAllUsersFromDB();
		
		stopServers();
		
		stopRequestManager();
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
		if (Login.loginConnected) {
			HomePage.closeHomePage();
			new HomePage();
		}
	}
	
	public static void deleteUserInDB (String pseudo) {
		localDB.deleteUser(pseudo);
		if (Login.loginConnected) {
			HomePage.closeHomePage();
			new HomePage();
		}
	}
	
	public static void updatePseudo(String p, String np) {
		localDB.updatePseudo(p, np);
		if (Login.loginConnected) {
			HomePage.closeHomePage();
			new HomePage();
		}
	}
	
	public static void deleteAllUsersFromDB() {
		localDB.deleteAllUser();
		if (Login.loginConnected) {
			HomePage.closeHomePage();
			new HomePage();
		}
	}
	
	public static String getUserByIPFromDB (InetAddress ip) {
		return localDB.getUserByIp(ip);
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
	
	// ========================== GESTION CONVERSATIONS ===========================
	public static void runRequestManager() {
		requestManager.start();
	}
			
	public static void stopRequestManager() {
		requestManager.stillRunning = false;
	}
			
	public static void requestConversation(String pseudo) { //TODO: à implementer dans usergui
		int localPort = requestManager.getNextPort();
		requestManager.augmentNextPort();
				
		String localIpAddress = localUser.add.getHostAddress(); // .toString(); //
		System.out.println("[Manager] Sending ip : " + localIpAddress);
		Request request = new Request(localIpAddress, localPort); //TODO sjekk om / eller ikke
		String requestMessage = request.createString();
				
		Socket client;
		String distantipAddress;
		InetAddress dist;
		try {
			distantipAddress = localDB.getUserByPseudo(pseudo).toString().substring(1);
			dist = localDB.getUserByPseudo(pseudo);
			ConvoManager cm = new ConvoManager(localPort, 0, distantipAddress, pseudo);
			cm.start();
					
			System.out.println("[Manager] New client with IP: " + distantipAddress + " and port : " + RequestManager.port);
			client = new Socket(dist, RequestManager.port);
					
			PrintWriter output = new PrintWriter(client.getOutputStream());
			output.println(requestMessage);
			output.flush();
					
			client.close();				
					
					
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
			
	public static void startConversation(int localPort, int distantPort, String distantIPaddress) {
		String pseudo = "";
		try {
			pseudo = localDB.getUserByIp(InetAddress.getByName(distantIPaddress));
			System.out.println("[Manager] Starting conversation with " + pseudo);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		ConvoManager cm = new ConvoManager(localPort, distantPort, distantIPaddress, pseudo);
		cm.start();
	}
			
		// ========================== GESTION BDD DISTANTE ===========================

	public static ArrayList<Message> getHistory(String distantUserIP) {
		ArrayList<Message> history = bddDistante.getHistory(localUser.add.getHostAddress(), distantUserIP);		
		return history;
	}
		
	public static void storeSentMessage(String message, String receiverIP) {
		String senderIP = localUser.add.toString().substring(1);
		bddDistante.storeMessage(message, senderIP, receiverIP);
	}
		
	public static void showDB() {
		bddDistante.showDb();
	}
	
	// ========================= GESTION GUI ================================
	
	public static String currentPseudo() {
		return localUser.pseudo;
	}
	
	public static void updtPseudo(String newPseudo) {
		localUser.pseudo = newPseudo;
	}
	
	public static InetAddress currentIP() {
		return localUser.add;
	}
	
	// ========================= LANCEMENT APPLICATION =======================
	
	public static void main(String[] args) {
		
	}
}
