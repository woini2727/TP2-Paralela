package common;

import java.util.ArrayList;
import java.util.List;

public class MsjDirRecurso {
	private String recurso;
	private ArrayList<String> direcciones;
	
	public MsjDirRecurso() {
		
	}
	public void setDirecciones(String direccion) {
		this.direcciones.add(direccion);
	}
	public ArrayList<String> getDirecciones(){
		return this.direcciones;
		
	}
	
}
