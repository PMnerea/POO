package udpConnexion;

import java.io.*; 
import java.net.*; 

public class udpServer {
	public static void main(String[] args) throws IOException {
		// le client est sur le port 4999
		DatagramSocket server = new DatagramSocket(4999);
		
		// Mettre cette partie dans une boucle qui commence au démarrage de l'application et se ferme si on éteint l'application
		// while(app pas fermée) 
		byte[] buf = new byte[256];
		
		// creation du packet datagramme
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		
		// reception de packets
		server.receive(packet);
		String reponse = new String(packet.getData());
		System.out.println("Reponse Data : " + reponse);
		// fermeture de la boucle
		
		server.close();
	}
}