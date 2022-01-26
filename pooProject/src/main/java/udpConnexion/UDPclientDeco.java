package udpConnexion;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.SocketException;

import manager.Manager;
import reseau.Reseau;
import user.User;

public class UDPclientDeco extends Thread {
	public boolean updConnected;
	// Declaration utilisateur local
	User localUser;
	// Declaration pour le setup des interfaces
	DatagramSocket client;
	InterfaceAddress ia;
	InetAddress add;
	String str;
	DatagramPacket p;
	byte[] buf;
	
	public UDPclientDeco() {
		this.updConnected = true;
		this.localUser = Manager.localUser;
		try {
			this.client = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.ia = Reseau.findAddresses();
		this.add = ia.getBroadcast();
	}
	
	public void run() {
		try {			
			// Créer le message composé du pseudo et de l'adresse ip locale
			this.str = this.localUser.pseudo + " " + (this.localUser.add).getHostAddress() + " deconnection ";
								
			// declaration des buffer pour avoir la longueur du datagramme
			this.buf = str.getBytes();
				
			// on prend le port 4999 pcq le 5000 on va l'utiliser pour tcp
			this.p = new DatagramPacket(buf, buf.length, add, 4999);
								
			// envoi du packet udp cree juste avant
			client.send(p);
			
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
