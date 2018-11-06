package common;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {
	private boolean encontrado;
	public Response() {
		
	}
	public boolean isEncontrado() {
		return encontrado;
	}
	public void setEncontrado(boolean encontrado) {
		this.encontrado = encontrado;
	}
	@Override
	public String toString(){
		String respuesta="false";
		if (this.encontrado) {
			respuesta="true";
		}
		return respuesta;
	}

	
	
}
