package user;

import java.io.IOException;
import java.net.*;

import reseau.Reseau;

public class User {
	public String pseudo;
	public InetAddress add;
	
	// Constructeur d'un objet User
	public User(String pseudo, InetAddress add) {
		this.pseudo = pseudo; 
		this.add = add; 
	}
	
	// initialiser un utilisateur en lui envoyant un string qu'on entrera au clavier grace a l'interface
	// a l'interieur on recupere l'adresse ip de l'ordinateur
	public static User initLocalUser(String pseudo) throws IOException {
		InterfaceAddress ia = Reseau.findAddresses();
		InetAddress ip = ia.getAddress();
		
		User u = new User(pseudo, ip);
		
		return u; 
	}
}
