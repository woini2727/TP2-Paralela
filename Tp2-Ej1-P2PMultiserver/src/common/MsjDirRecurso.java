package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MsjDirRecurso implements Serializable{
	private String recurso;
	private HashMap<Integer,String> direcciones;
	
	public MsjDirRecurso() {
		this.direcciones=new HashMap<Integer,String>();
		
	}
	public void setDireccion( int puerto,String direccion) {
		this.direcciones.put(puerto,direccion);
	}
	public  HashMap<Integer,String> getDirecciones(){
		return this.direcciones;
		
	}
	public String getContenido() {
		String result="";
		for (Entry<Integer, String> entry : direcciones.entrySet()) {
		    Integer key = entry.getKey();
		    Object value = entry.getValue();
		    result=key+"-"+value;
		}
		return result;
	}
	
}
