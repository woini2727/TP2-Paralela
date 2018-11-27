package common.masters;

import java.io.Serializable;

public class RequestServidor implements Serializable{
	private String nameResource="";
	private int nport;
	private TipoRequest tRequest;
	private String dir;
	private int port;
	
	public RequestServidor(String nameResource) {
		this.nameResource=nameResource;
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
	
}
