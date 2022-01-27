package tcpConnexion;

import java.io.*;
import java.net.*;
import java.util.*;

import user.User;

public class TCPclient {
	// demande clavardage
	// port 5000 utilise pour la demande de clavardage
	public static void demandeClavardage(InetAddress distantAddress, User localUser) {
		try {
			Socket client = new Socket(localUser.add, 5000);
			
			PrintWriter out = new PrintWriter(client.getOutputStream());
			out.println(localUser.pseudo + " " + (localUser.add).getHostAddress());
			out.flush();
			
			client.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// envoi messages
	// port 5001 utilise pour la conversation
	public static void conversationClavardage(InetAddress distantAddress, User localUser) {
		try {
			// declaration du socket client
			Socket client = new Socket(localUser.add, 5001);	
			
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
	
	public static void main(String[] args) {
		String testName = "toto";
		User testUser;
		InetAddress add;
		try {
			add = InetAddress.getLocalHost();
			testUser = User.initLocalUser(testName);
			conversationClavardage(add, testUser);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
