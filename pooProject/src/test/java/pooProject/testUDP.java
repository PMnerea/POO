package pooProject;

import java.io.IOException;
import udpConnexion.*;

public class testUDP {
	public static void main(String[] args) throws IOException {
		udpServer.main(null);
		System.out.println("il a fait qqc");
		udpClient.main(null);
		System.out.println("il a fait qqc");
	}
}
