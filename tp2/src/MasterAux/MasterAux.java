package MasterAux;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import common.MensajeInicializaci�n;
import ej1.ServidorThread;

//importe servidor thread y servidorDeMasters
public class MasterAux {
	static ArrayList<String> listaNodosExtremos; 
	static Socket sockServerAux;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		sockServerAux= new Socket("localhost",7000);
		OutputStream os;
		ObjectOutputStream oos;
		MensajeInicializaci�n msj;
		os = sockServerAux.getOutputStream();
		//para leer el os
		oos = new ObjectOutputStream(os);
		msj=new MensajeInicializaci�n();
		oos.writeObject(msj);
		while(true) {}
		
		
		
		
		
		
	}

}
