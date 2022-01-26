package bddDistante;

import java.sql.*;
import message.Message;
import java.util.ArrayList;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class BddDistante {
	
	Connection connection = null; 
	Statement statement; 
	String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/tp_servlet_016?";
	String user = "tp_servlet_016";
	String password = "ahy5HoRe";
	
	public BddDistante() {
		System.out.println("[DistantDB] Calling BddDistante constructor");
		
		// Load the driver class file
		try {
			System.out.println("[DistantDB] Loading the drive class file");
			Class.forName("com.mysql.cj.jdbc.Driver");	
		} catch (ClassNotFoundException e) {
			System.out.println("Error while loading the driver class file " + e);
		}
		
		try {
			// Start connection
			System.out.println("[DistantDB] Database connection...");
			this.connection = DriverManager.getConnection(this.url, this.user, this.password);
			System.out.print("[DistantDB] DatabaseConnected");
			
			// Create Statement object
			System.out.println("[DistantDB] Statement object creation...");
			this.statement = this.connection.createStatement();
			System.out.println("[DistantDB] Statement object created");
			
			// Execute the statement
			String query = "CREATE TABLE IF NOT EXISTS `hist`" + 
					"(`idMessage` INT not NULL, " + 
						"`Sender` VARCHAR(45) not NULL, " + 
						"`Receiver` VARCHAR(45) not NULL, " + 
						"`Message` VARCHAR(500) not NULL, " + 
						"`DateMess` DATETIME not NULL, " + 
						"PRIMARY KEY (`idMessage`));";
			
			System.out.println("[DistantDB] Creating table...");
			this.statement.executeUpdate(query);
			System.out.println("[DistantDB] Table created...");
			// this.connection.close();
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	// YYYY-MM-DD hh:min:sec
	public void storeFirstMessage(String message, String senderIP, String receiverIP) {
		
		LocalDateTime time = LocalDateTime.now();
	    DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedDate = time.format(myFormat);
	    
		String query = "INSERT INTO hist (idMessage, Sender, Receiver, Message, DateMess )" + 
			    "VALUES (0, '" + senderIP + 
			    "', '" + receiverIP + 
			    "', '" + message + 
			    "', '" + formattedDate + "');";			
		try {
			this.statement.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void storeMessage(String message, String senderIP, String receiverIP) {
		int idMess = -1;
		String getIDmess = "SELECT idMessage FROM hist ORDER BY idMessage DESC LIMIT 1;";
		
		try {
			ResultSet idMessRes = this.statement.executeQuery(getIDmess);
			
			if (idMessRes.next()) { 
				// Get next idMessage
				idMess = idMessRes.getInt(1) + 1;
				
				// Get and format date/time
				LocalDateTime time = LocalDateTime.now();
			    DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			    String formattedDate = time.format(myFormat);
			    
			    // Insert message into database
			    String query = "INSERT INTO hist (idMessage, Sender, Receiver, Message, DateMess )" + 
			    "VALUES (" + idMess + 
			    ", '" + senderIP + 
			    "', '" + receiverIP + 
			    "', '" + message + 
			    "', '" + formattedDate + "');";			
				this.statement.executeUpdate(query);
				System.out.println("[DistantDB] Message Stored...");
				System.out.println("Sender: " + senderIP);
				System.out.println("Receiver: " + receiverIP);
				System.out.println("Message: " + message);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (idMess == -1) {
			System.out.println("[DistantDB] Trouble storing message in DB...");
		}
	}
	
	public ArrayList<Message> getHistory(String localUserIP, String distantUserIP) {
		
		ArrayList<Message> history = new ArrayList<Message>();
		
		String query = "SELECT Sender, Receiver, Message, DateMess FROM hist WHERE (Sender='" + 
				localUserIP + "' AND Receiver='" + distantUserIP + 
				"') OR (Sender='" + distantUserIP + "' AND Receiver='" + localUserIP + "');";
		
		try {
			ResultSet historyRes = this.statement.executeQuery(query);
			
			while (historyRes.next()) {
				Message messAdd = new Message();
				messAdd.setMessage(historyRes.getString(3));
				messAdd.setSender(historyRes.getString(1));
				messAdd.setReceiver(historyRes.getString(2));
				messAdd.setDateTime(historyRes.getString(4));
				history.add(messAdd);
				messAdd.printMessage();
				
			}
			
			if (history.size() == 0) {
				System.out.println("No history ");
			}
			
			for (Message message : history) {
				System.out.println(message.getDateTime() + "   " + message.getSender() + " :   " + message.getMessage());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception");
			
		}
		return history;
	}
	
	public void showDb() {
		String queryyy = "SELECT idMessage, Message, Sender, Receiver, DateMess FROM hist;";
		
		try {
			ResultSet result = this.statement.executeQuery(queryyy);
			System.out.println("DB: (idmess, mess, sender, receiver, dateTime)");
			
			while (result.next()) {
				System.out.println(result.getString(1) + "  " + result.getString(2) + "  " + result.getString(3) + "  " + result.getString(4) + "  " + result.getString(5));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	public void deleteDb() {
		String queryyy = "DROP TABLE hist;";
		
		try {
			this.statement.executeUpdate(queryyy);
			System.out.println("DB deleted");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	
	public static void main(String[] args) {
	
		/*
		// Tout ca est juste pour tester 
		BddDistante dbDist = new BddDistante();
		dbDist.storeMessage(" ICIII", "192.167", "192.168");
		ArrayList<Message> messages = dbDist.getHistory("192.167", "192.168");
		
		for ( Message message : messages) {
			message.printMessage();
		}
		*/
		BddDistante dbDist = new BddDistante();
		// dbDist.deleteDb();
		// dbDist.storeFirstMessage("Hey", "10.1.5.126", "10.1.5.81");
		dbDist.showDb();
		ArrayList<Message> hist = dbDist.getHistory("10.1.5.147", "10.1.5.146");
		/*
		try {
			dbDist.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		/* 
		BddDistante dbDist = new BddDistante();
		dbDist.showDb();
		dbDist.deleteDb();
		dbDist.showDb();
		*/ 
		
	}
	
	

}
