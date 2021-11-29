package pooProject;

import java.io.*;
import java.net.*;


public class client {
	public static void main(String[] args){
		try {
			String host = "127.0.0.1"; 
			int port = 5000; 
			Socket clientSocket = new Socket(host, port);
			
			// Ligne à écrire
			PrintWriter pr = new PrintWriter(clientSocket.getOutputStream());
			pr.println("Is it working?");
			pr.flush();
			
			// lecture des buffers d'entree
			InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
			BufferedReader bf = new BufferedReader(in);
			
			String str = bf.readLine();
			System.out.println("Server : " + str);
			
			// fermeture des ressources
			clientSocket.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
