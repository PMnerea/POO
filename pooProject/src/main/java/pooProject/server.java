package pooProject;

import java.io.*;
import java.net.*;

public class server {
	public static void main(String[] args){
		try {
			int port = 5000; 
			// on définit un nouveau serveur
			ServerSocket serverSocket = new ServerSocket(port);
			// le socket client et l'acceptation de la connexion du server
			Socket s = serverSocket.accept();
			
			// afficher confirmation de connexion
			System.out.println("Client connected");
			
			// lire le buffer d'entree
			InputStreamReader in = new InputStreamReader(s.getInputStream());
			BufferedReader bf = new BufferedReader(in);
			
			// lecture du buffer et affichage de la lecture
			String str = bf.readLine();
			System.out.println("Client : " + str);
			
			// ecriture de ligne en sortie 
			PrintWriter pr = new PrintWriter(s.getOutputStream());
			pr.println("yes");
			pr.flush();
			
			// fermeture des flux
			s.close();
			serverSocket.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
