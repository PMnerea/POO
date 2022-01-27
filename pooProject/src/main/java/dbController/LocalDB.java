package dbController;

import java.sql.Statement;
import java.util.ArrayList;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
 
public class LocalDB {

	// Attributes 
		Connection connection = null ; 
		Statement statement ;
		
		// ======================= CONNEXION =========================
		
		// Constructor 
		public LocalDB() {
			System.out.println("[LocalDB] Calling LocalDB constructor");
			
			// Load the driver class file 
			try {
				System.out.println("[LocalDB] Loading the driver class file");
				Class.forName("org.sqlite.JDBC") ; 
			} 
			catch (ClassNotFoundException e) {
				System.out.println("Error while loading the driver class file" + e) ; 
			}
			
			try {
				// Make a database connection
				System.out.println("[LocalDB] Database connection...");
				this.connection = DriverManager.getConnection("jdbc:sqlite:test.db");
				System.out.println("[LocalDB] Database connected");
				
				// Create a statement object
				System.out.println("[LocalDB] Statement objects creation...");
				this.statement = this.connection.createStatement() ; 

				System.out.println("[LocalDB] Statement objects created");

				
				// Execute the statement 			
				String query = " CREATE TABLE IF NOT EXISTS `users`( " +
								 "`idUser` INT NOT NULL," +
								 "`pseudo` VARCHAR(45) NOT NULL," +
								 "`ipAddress` VARCHAR(45) NOT NULL," +
								 "PRIMARY KEY (`idUser`));";
				
				System.out.println("[LocalDB] Creating the table...");
				this.statement.executeUpdate(query) ;
				System.out.println("[LocalDB] Table created");
				
				System.out.println(" ================================= ");
			} 
			
			catch (SQLException e) {
				System.out.println(e);
			}
		}
		
		
		// ============================== POST ==============================
		
		// Add a user by his pseudo and his ipAddress to the database
		public void addUser(String pseudo, InetAddress ipAdd) {
			System.out.println("[LocalDB] Adding a user in the table...");
			
			String aux = "SELECT idUser FROM users ORDER BY idUser DESC LIMIT 1;";
			
			try {
				// Recuperer l'id
				ResultSet rs = this.statement.executeQuery(aux);
				if (rs.next()) {
					int id = rs.getInt(1) + 1;
					String query = "INSERT INTO users (idUser, pseudo, ipAddress) VALUES (" + id + ", '" + pseudo + "', '" + ipAdd + "') ;" ; 
					this.statement.executeUpdate(query) ;
				}
				else {
					String query = "INSERT INTO users (idUser, pseudo, ipAddress) VALUES (" + 0 + ", '" + pseudo + "', '" + ipAdd + "') ;" ; 
					this.statement.executeUpdate(query) ;
				}
				System.out.println("[LocalDB] User added");
				System.out.println(" ================================= ");
			} 
			catch (SQLException e) {
				System.out.println(e);
			}
		}
		
		// ============================== DELETE ==============================
		
		// Delete one user by his pseudo
		public void deleteUser(String pseudo) {
			System.out.println("[LocalDB] Deleting user...");
			
			String query = "DELETE FROM users WHERE pseudo = '" + pseudo + "';";
			
			try {
				this.statement.executeUpdate(query);
				System.out.println("[LocalDB] User deleted");
				System.out.println(" ================================= ");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Delete all user
		public void deleteAllUser() {
			System.out.println("[LocalDB] Deleting all users...");
			
			String query = "DELETE FROM users;";
			
			try {
				this.statement.executeUpdate(query);
				System.out.println("[LocalDB] All users deleted");
				System.out.println(" ================================= ");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// ============================ GET ================================
		
		// Get user by his IP address
		public String getUserByIp(InetAddress ipAdd) {
			System.out.println("[LocalDB] Getting user by his IP address");
			
			String IP = ipAdd.toString();
			
			String query = "SELECT pseudo FROM users WHERE ipAddress = '" + IP + "' ;"; 
			
			try {
				ResultSet rs = this.statement.executeQuery(query);
				if (rs.next()) {
					String pseudo = rs.getString(1);
					System.out.println("[LocalDB] User returned");
					System.out.println(" ================================= ");
					return pseudo;
				}
				else {
					System.out.println("[LocalDB] No user has been found...");
					System.out.println(" ================================= ");
					return null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}

		// Get user by his pseudo
		public InetAddress getUserByPseudo(String pseudo) {
			System.out.println("[LocalDB] Getting user by his pseudo");
			
			String query = "SELECT ipAddress FROM users WHERE pseudo = '" + pseudo + "' ;"; 
			
			try {
				ResultSet rs = this.statement.executeQuery(query);
				if (rs.next()) {
					String addName = rs.getString(1);
					String[] res = addName.split("/");
					try {
						InetAddress ipAdd = InetAddress.getByName(res[0]);
						System.out.println("[LocalDB] IP address returned");
						System.out.println(" ================================= ");
						return ipAdd;
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				}
				else {
					System.out.println("[LocalDB] No IP address has been found...");
					System.out.println(" ================================= ");
					return null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}
		
		// Get all pseudo users
		public ArrayList<String> getAllPseudos() {
			System.out.println("[LocalDB] Getting all users connected");
			
			String query = "SELECT pseudo FROM users";
			
			ArrayList<String> pseudos = new ArrayList<String>() ;
			
			try {
				ResultSet rs = this.statement.executeQuery(query);
				while (rs.next()) {
					pseudos.add(rs.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("[LocalDB] All pseudos returned");
			System.out.println(" ================================= ");
			return pseudos;
		}
		
		// ============================ UPDATE ================================
		
		// Update pseudo given a pseudo
		public void updatePseudo(String pseudo, String newPseudo) {
			System.out.println("[LocalDB] Updating pseudo...");
			
			String query = "UPDATE users SET pseudo = '" + newPseudo + "' WHERE pseudo = '" + pseudo + "';";
			
			try {
				this.statement.executeUpdate(query);
				System.out.println("[LocalDB] Pseudo updated");
				System.out.println(" ================================= ");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Update ip given an id
		public void updateIp(int id, InetAddress newAdd) {
			System.out.println("[LocalDB] Updating IP address...");
			
			String query = "UPDATE users SET ipAddress = '" + newAdd + "' WHERE idUser = " + id + ";";
			
			try {
				this.statement.executeUpdate(query);
				System.out.println("[LocalDB] IP address updated");
				System.out.println(" ================================= ");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// ============================ tests ================================
		
		public static void main(String[] args) {
			InetAddress add;
			try {
				add = InetAddress.getLocalHost();
				LocalDB localDB = new LocalDB() ;
				localDB.addUser("juju", add);
				String resPseudo = localDB.getUserByIp(add);
				InetAddress resIp = localDB.getUserByPseudo("juju");
				System.out.println(resPseudo);
				System.out.println(resIp);
				localDB.updatePseudo("toto", "koko");
				localDB.updateIp(8, add);
				//localDB.deleteUser("dede");
				ArrayList<String> pseudos = localDB.getAllPseudos();
				System.out.println(pseudos);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
