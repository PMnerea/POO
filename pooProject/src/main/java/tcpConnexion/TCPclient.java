package tcpConnexion;

import java.io.*;
import java.net.*;
import java.util.*;

import user.User;
// client envoie à ouvrir quand qqn veut envoyer

public class TCPclient extends Thread{
	// demande clavardage
	// port 5000 utilise pour la demande de clavardage
	public static void demandeClavardage(InetAddress distantAddress, User distantUser) {
		try {
			Socket client = new Socket(distantUser.add, 5000);
			
			PrintWriter out = new PrintWriter(client.getOutputStream());
			out.println(distantUser.pseudo + " " + (distantUser.add).getHostAddress());
			out.flush();
			
			client.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// envoi messages
	// port 5001 utilise pour la conversation
	public static void conversationClavardage(InetAddress distantAddress, User localUser, int localPort) {
		try {
			// declaration du socket client
			Socket client = new Socket(localUser.add, localPort);	
			
			// out est le systeme permettant d'envoyer un segment
			PrintWriter out = new PrintWriter(client.getOutputStream());
			// le scanner permet de lire quelque chose entre par l'utilisateur
			Scanner s = new Scanner(System.in);
			// on construit notre message en lisant une ligne
			String msg = "";
			
			while (!msg.equals("Over")) {
				msg = s.nextLine();
				// on envoi le msg dans la sortie et on efface le buffer de sortie
				out.println(msg);
				out.flush();
			}
			
			client.close();		
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendMessage(InetAddress distantAddress, User localUser, String msg) {
		try {
			Socket client = new Socket(localUser.add, 5001);
			
			DataOutputStream dout = new DataOutputStream(client.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			dout.writeUTF(msg);
			
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TCPclient() {
		// User testUser;
		// InetAddress add;
		
	}
	
	public void run( User distantUser, int localPort) {
		try {
			// add = InetAddress.getLocalHost();
			// testUser = User.initLocalUser(testName);
			sendMessage(distantUser.add, distantUser, "ça marche?");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	}
}
