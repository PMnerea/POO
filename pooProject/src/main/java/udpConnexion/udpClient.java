package udpConnexion;

import java.io.IOException;
import java.net.*;

public class udpClient {
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
