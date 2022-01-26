package manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import message.Message;

import bddDistante.BddDistante;
import dbController.LocalDB;
import request.Request;
import udpConnexion.*;
import user.User;

public class Manager {
	
	public static User localUser;
	
	private static LocalDB localDB = new LocalDB();
	
	static udpServer serverUDP = new udpServer();
	static udpClient clientUDP;
	static UDPclientDeco clientUDPdeco;
	
	private static RequestManager requestManager = new RequestManager();
	private static BddDistante bddDistante = new BddDistante();
	
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
	
	// ========================== GESTION SERVERS ============================
	public static void runServers() {
		System.out.println("[Manager] running servers");
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
		
		public static void requestConversation(String distantipAddress) { //TODO: à implementer dans usergui
			int localPort = requestManager.getNextPort();
			requestManager.augmentNextPort();
			
			String localIpAddress = localUser.add.getHostAddress();
			Request request = new Request(localIpAddress, localPort); //TODO sjekk om / eller ikke
			String requestMessage = request.createString();
			
			Socket client;
			String pseudo;
			try {
				pseudo = localDB.getUserByIp(InetAddress.getByName(distantipAddress));
				convoManager cm = new convoManager(localPort, 0, distantipAddress, pseudo);
				cm.start();
				
				client = new Socket(distantipAddress, RequestManager.port);
				
				PrintWriter output = new PrintWriter(client.getOutputStream());
				output.println(requestMessage);
				output.flush();
				
				client.close();				
				
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public static void startConversation(int localPort, int distantPort, String distantIPaddress) {
			convoManager cm = new convoManager(localPort, distantPort, distantIPaddress, "testName");
			cm.start();
		}
		
	// ========================== GESTION BDD DISTANTE ===========================

	public static ArrayList<Message> getHistory(String distantUserIP) {
		ArrayList<Message> history = bddDistante.getHistory(localUser.add.getHostAddress(), distantUserIP);
		
		System.out.println("[Manager] Database : ");
		System.out.println("[Manager] localIP : " + localUser.add.toString());
		System.out.println("[Manager] distantIP : " + distantUserIP);
		for (Message message : history) {
			message.printMessage();
		}
		
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
		localUser = initUser("qsqs");
		
		clientUDP = new udpClient();
		System.out.println(localUser.pseudo);
		System.out.println(localUser.add);
		
		System.out.println("[Manager] Actual Database : ");
		showDB();
		
		// runServers();
		// runUDPclient();
		runRequestManager();
		Scanner s = new Scanner(System.in);
		
		while (true) {
			String ip = s.next();
			// "10.1.5.145

			// "10.1.5.146

			// "10.1.5.147

			requestConversation(ip);
		}
	}
	
}
