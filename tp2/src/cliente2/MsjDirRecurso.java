package cliente2;

import java.util.List;

public class MsjDirRecurso {
	private String recurso;
	private List direcciones;
	
	public MsjDirRecurso(String recurso,List direcciones) {
		this.recurso=recurso;
		this.direcciones=direcciones;
	}
	
	public String toString(){
		return recurso+direcciones;
	}
}
