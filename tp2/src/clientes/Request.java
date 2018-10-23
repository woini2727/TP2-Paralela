package clientes;

import java.io.Serializable;

public class Request implements Serializable{
	String nameResource="";
	
	public Request(String nameResource) {
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
	
}
