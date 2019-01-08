package common;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class MensajeInicializaci�n implements Serializable {
		private int listPort;
        String ip="";
		String resource="";
	 
	 public MensajeInicializaci�n() throws SocketException {
			
	}
	
	@Override
	 public String toString() {
		InetAddress i = null;
		 try {
			i = InetAddress.getLocalHost();
			ip=i.getHostAddress();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;
		 
		 
	 }

	 public String getIp() {
		return this.toString();
		 
	 }

	public int getListPort() {
		return listPort;
	}

	public void setListPort(int listPort) {
		this.listPort = listPort;
	}
	
	 
	 
}
