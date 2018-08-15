package clientes;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class MensajeServidor implements Serializable {

	static String ip="";
	ArrayList<String> listaIps;
	 
	 public MensajeServidor() throws SocketException {
		//para  la ip
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		NetworkInterface iface = interfaces.nextElement();
		Enumeration<InetAddress> addresses = iface.getInetAddresses();
		InetAddress addr = addresses.nextElement();
		String  ipp = addr.getHostAddress();
		ip=ipp;
		////
		listaIps=new ArrayList<String>();
		
	}
	
	@Override
	 public String toString() {
		 return ip;
		 
	 }

	 public String getIp() {
		return this.toString();
		 
	 }
	 public void addIp(String ip) {
		 this.listaIps.add(ip);
	 }
	 public ArrayList<String> getListaIps(){
		return this.listaIps;
		 
	 }
	 
}
