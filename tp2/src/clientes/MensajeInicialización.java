package clientes;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class MensajeInicialización implements Serializable {

        String ip="";
		String resource="";
	 
	 public MensajeInicialización() throws SocketException {
		//para  la ip
		/*Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		NetworkInterface iface = interfaces.nextElement();
		Enumeration<InetAddress> addresses = iface.getInetAddresses();
		InetAddress addr = addresses.nextElement();
		String  ipp = addr.getHostAddress();
		ip=ipp;*/
		////
		this.resource=resource;
		///
		
	
		
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
	
	 
	 
}
