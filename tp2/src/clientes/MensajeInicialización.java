package clientes;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class MensajeInicialización implements Serializable {

	static String ip="";

	 
	 public MensajeInicialización() throws SocketException {
		//para  la ip
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		NetworkInterface iface = interfaces.nextElement();
		Enumeration<InetAddress> addresses = iface.getInetAddresses();
		InetAddress addr = addresses.nextElement();
		String  ipp = addr.getHostAddress();
		ip=ipp;
		////
	
		
	}
	
	@Override
	 public String toString() {
		 return ip;
		 
	 }

	 public String getIp() {
		return this.toString();
		 
	 }
	
	 
	 
}
