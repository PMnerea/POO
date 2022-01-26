package tcpConnexion;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import conversation.convoController;
import manager.convoManager;

public class TCPserversocket extends Thread {
	// Va recevoir des messages depuis un socket distant

	public int port;
	ServerSocket server;
	Socket client;
	convoManager cm;
	
	public TCPserversocket(int port, convoManager cm) {
		this.port = port;
		this.cm = cm;
		}
	
	public void run() {
		try {
			this.server = new ServerSocket(this.port);
			System.out.println("[TCPserver] Listening on port: " + this.port);
			this.client = new Socket();
			this.client = this.server.accept();
			System.out.println("[TCPserver] Client connected");
			
			BufferedReader input  = new BufferedReader(new InputStreamReader(this.client.getInputStream()));			
			String message = input.readLine();
			System.out.println("[TCPserversocket] Setting distantPort to: " + message);
			this.cm.setPort(Integer.valueOf(message));
			
			while (!(message==null)) {
				message = input.readLine();
				this.newMessage(message);
				// System.out.println(message);
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void newMessage(String message) {
		this.cm.newMessage(message);
	}
	
	public void close() { // Pas sur si besoin
		System.out.println("[TCPserver] Closing server");
		try {
			// this.client.close();
			this.server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		String ip = "";
		try(final DatagramSocket socket = new DatagramSocket()){
			  try {
				socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  ip = socket.getLocalAddress().getHostAddress();
			  System.out.println("IP:  " + ip);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		TCPserversocket sersock = new TCPserversocket(5557,null);
		sersock.run();
	}
}
