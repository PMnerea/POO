package tcpConnexion;

import java.io.*;
import java.net.*;
import user.User;

public class TCPserver {
	public static void acceptClavardage(InetAddress distantAddress, User localUser) {
		try {
			ServerSocket server = new ServerSocket(5000);
			Socket client = server.accept();
			
			// avertir de la connexion (à supprimer plus tard)
			System.out.println("client connected");
			
			InputStreamReader in = new InputStreamReader(client.getInputStream());
			BufferedReader br = new BufferedReader(in);
			
			String str = br.readLine();
			System.out.println(str);
			
			client.close();		
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void conversationClavardage(InetAddress distantAddress, User localUser) {
		try {
			// declaration des sockets
			ServerSocket server = new ServerSocket(5001);
			Socket client = server.accept();
			
			System.out.println("Client connected");
			
			InputStreamReader in = new InputStreamReader(client.getInputStream());
			BufferedReader br = new BufferedReader(in);

			String str = "";
			
			while(!str.equals("Over")) {
				str = br.readLine();
				System.out.println("Client : " + str);
			}
			
			client.close();	
			server.close();
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
