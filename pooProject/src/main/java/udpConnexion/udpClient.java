package udpConnexion;

import java.io.IOException;
import java.net.*;
import reseau.Reseau;
import user.User;

public class udpClient {
	// Envoi monocast : quand on reçois la premiere question on repond qui on est
	public static void userDiscoverResponse(InetAddress distantAddress, User localUser) throws IOException {
		// declaration du client et de son adresse IP
		DatagramSocket client = new DatagramSocket();
		
		// Créer le message composé du pseudo et de l'adresse ip locale
		String str = localUser.pseudo + " " + (localUser.add).getHostAddress();
		
		// declaration des buffer pour avoir la longueur du datagramme
		byte[] buf = str.getBytes();
		// on prend le port 4999 pcq le 5000 on va l'utiliser pour tcp
		DatagramPacket p = new DatagramPacket(buf, buf.length, distantAddress, 4999);
		
		// envoi du packet udp cree juste avant
		client.send(p);
				
		client.close();
	}
	
	// Envoi broadcast : chaque x secondes on envoie nos infos pour dire qu'on est co
	// Quand on est utilisateur distant, si cet utilisateur n'est pas encore dans la liste on l'ajoute et renvoie info en monocast
	public static void sendInfo(User localUser) throws IOException {
		// declaration du client et de son adresse IP
		DatagramSocket client = new DatagramSocket();
		InterfaceAddress ia = Reseau.findAddresses();
		InetAddress add = ia.getBroadcast();
		
						
		// Créer le message composé du pseudo et de l'adresse ip locale
		String str = localUser.pseudo + " " + (localUser.add).getHostAddress();
						
		// declaration des buffer pour avoir la longueur du datagramme
		byte[] buf = str.getBytes();
		// on prend le port 4999 pcq le 5000 on va l'utiliser pour tcp
		DatagramPacket p = new DatagramPacket(buf, buf.length, add, 4999);
						
		// envoi du packet udp cree juste avant
		client.send(p);
								
		client.close();
	}
	
	public static void main(String[] args) throws IOException {
		String testName = "toto";
		User testUser = User.initLocalUser(testName);
		sendInfo(testUser);
	}
}
