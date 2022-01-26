package tcpConnexion;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TCPsocket extends Thread{
	// Va envoyer des messages à un socketserver distant
	
	public PrintWriter output;
	public String message;
	// BufferedReader input;
	Socket client;
	public int port, localPort;
	// public InetAddress address;
	public String address;
	
	public void sendMessage(String message) {
		this.output.println(message);
		// System.out.println("har printa msg");
		this.output.flush();
	}
	
	public void close() { // Pas sur si besoin
		System.out.println("[TCPsocket] Closing socket");
		try {
			this.client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TCPsocket(String address, int port, int localPort) {
		this.address = address;
		this.port = port;
		this.localPort = localPort;
		this.message="";
	}
	
	public void run() {
		System.out.println("[TCPsocket] Connecting to server...");
		System.out.println("[TCPsocket] IP: " + this.address);
		System.out.println("[TCPsocket] port: " + this.port);

		try {
			this.client = new Socket(this.address, this.port);
			System.out.println("[TCPsocket] Connected");
			this.output = new PrintWriter(this.client.getOutputStream());
			this.sendMessage(String.valueOf(this.localPort));
			System.out.println("[TCPsocket] Sending myPort : " + String.valueOf(this.localPort));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		TCPsocket sock = new TCPsocket("10.1.5.80", 5557, 0);
		sock.run();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sock.sendMessage("HEYYY");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sock.sendMessage("how are you!");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sock.sendMessage("Over");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
