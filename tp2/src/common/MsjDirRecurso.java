package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MsjDirRecurso implements Serializable{
	private String recurso;
	private ArrayList<String> direcciones;
	
	public MsjDirRecurso() {
		this.direcciones=new ArrayList<String>();
		this.direcciones.add("aa");
	}
	public void setDireccion(String direccion) {
		this.direcciones.add(direccion);
	}
	public ArrayList<String> getDirecciones(){
		return this.direcciones;
		
	}
	
}
