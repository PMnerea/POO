package reseau;

import java.net.*;
import java.util.*;

public class Reseau {
	public static InterfaceAddress findAddresses() {
		Enumeration<NetworkInterface> interfaces;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		}
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            try {
				if (networkInterface.isLoopback()) continue;
			} catch (SocketException e) {
				e.printStackTrace();
				continue;
			}
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
            	InetAddress broadcast = interfaceAddress.getBroadcast();
            	if (broadcast == null) {
            		continue; 
            	}
            	else {
            		return interfaceAddress;
            	}
            }
        }
		
		return null; 
	}
}
