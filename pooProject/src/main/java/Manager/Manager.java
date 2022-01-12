package Manager;

import java.net.InetAddress;
import dbController.LocalDB;

public class Manager {
	
	private static LocalDB localDB = new LocalDB();
	
	// ========================== GESTION LOCAL DB =========================
	
	public static void addUserToDB (String pseudo, InetAddress add) {
		localDB.addUser(pseudo, add);
	}
	
	public static void main(String[] args) {
		
	}
}
