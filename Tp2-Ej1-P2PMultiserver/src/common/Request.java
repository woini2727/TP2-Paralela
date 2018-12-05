package common;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Request implements Serializable{
	private String nameResource="";
	private int nport;
	private TipoRequest tRequest;
	private String dir;
	private int port;
	private String ip;
	
	public Request(String nameResource) {
		this.nameResource=nameResource;
		InetAddress i = null;
		 try {
			i = InetAddress.getLocalHost();
			ip=i.getHostAddress();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNameResource() {
		return nameResource;
	}

	public void setNameResource(String nameResource) {
		this.nameResource = nameResource;
	}
	@Override
	public String toString() {
		return nameResource;
		
	}

	public int getNport() {
		return nport;
	}

	public void setNport(int nport) {
		this.nport = nport;
	}

	public TipoRequest gettRequest() {
		return tRequest;
	}

	public void settRequest(TipoRequest tRequest) {
		this.tRequest = tRequest;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		 return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
