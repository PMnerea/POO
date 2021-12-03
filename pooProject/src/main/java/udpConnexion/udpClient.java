package udpConnexion;

import java.io.IOException;
import java.net.*;
import user.User;

public class udpClient {
	// Envoi monocast : coucou j'ai vu que tu etais la je te dis qui je suis (quand on reçois la premiere question on repond qui on est) 
	public static void userDiscoverResponse(InetAddress distantAddress, User localUser) throws IOException {
		// declaration du client et de son adresse IP
		DatagramSocket client = new DatagramSocket();
		
		// Créer le message composé du pseudo et de l'adresse ip locale
		String str = localUser.pseudo + (localUser.add).toString();
		
		// declaration des buffer pour avoir la longueur du datagramme
		byte[] buf = str.getBytes();
		// on prend le port 4999 pcq le 5000 on va l'utiliser pour tcp
		DatagramPacket p = new DatagramPacket(buf, buf.length, distantAddress, 4999);
		
		// envoi du packet udp cree juste avant
		client.send(p);
				
		client.close();
	}
	
	// Envoi broadcast : coucou qui est la? (quand on se connecte la premiere fois on demande qui est connecte)
	public static void userDiscover(InetAddress localAddress) throws IOException {
		
	}
	
	// Envoi broadcast : coucou je suis la, je suis toto (chaque x secondes on envoie nos infos pour dire qu'on est co)
	public static void sendInfo(InetAddress localAddress) throws IOException {
		
	}
	
	public static void main(String[] args) throws IOException {
		// declaration du client et de son adresse IP
		DatagramSocket client = new DatagramSocket();
		InetAddress add = InetAddress.getByName("localhost");
		
		String str = "Hello world";
		
		// declaration des buffer pour avoir la longueur du datagramme
		byte[] buf = str.getBytes();
		// on prend le port 4999 pcq le 5000 on va l'utiliser pour tcp
		DatagramPacket p = new DatagramPacket(buf, buf.length, add, 4999);
		
		// envoi du packet udp cree juste avant
		client.send(p);
		
		client.close();
	}
}
