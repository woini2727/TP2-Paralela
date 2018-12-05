package common.masters;

import java.io.Serializable;

public class RequestServidor implements Serializable{
	private String nameResource="";
	//private TipoRequestServer tRequest;
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

	/*public TipoRequestServer gettRequest() {
		return tRequest;
	}

	public void settRequest(TipoRequestServer tRequest) {
		this.tRequest = tRequest;
	}*/


	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
