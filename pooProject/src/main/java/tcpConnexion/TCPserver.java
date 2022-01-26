package tcpConnexion;

// tt le temps ouvert 1 pour chaque convo
import java.io.*;
import java.net.*;
import user.User;

public class TCPserver extends Thread {
	public static void acceptClavardage(InetAddress distantAddress, User localUser) {
		try {
			ServerSocket server = new ServerSocket(5042);
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
	
	// Plus tard essayer avec plusieurs ports 
	public static void conversationClavardage(InetAddress distantAddress, User localUser) {
		try {
			System.out.println("[TCPserver] Listening...");
			
			// declaration des sockets
			ServerSocket server = new ServerSocket(5001);
			Socket client = server.accept();
			
			System.out.println("Client connected");
			
			DataInputStream din = new DataInputStream(client.getInputStream());

			String str = "";
			
			while(!str.equals("Over")) {
				str = din.readUTF();
				System.out.println("Client : " + str);
			}
			
			client.close();	
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TCPserver() {
	}
	
	public void run(User distantUser, int distantPort) {

		try {
			conversationClavardage(distantUser.add, distantUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		/*
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
		*/
	}

	
}
