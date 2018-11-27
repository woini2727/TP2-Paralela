package clientes2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import common.Request;
import common.Response;

public class ThreadBusquedaRecurso implements Runnable {
	private static final String PATHLOCAL = "src/clientes2/files/";
	Socket sock;
	File folder;
	String nFile="dataCliente2.txt";
	Request reqCliente;
	public ThreadBusquedaRecurso(Socket sock,Request reqCliente) {
		this.sock=sock;
		this.reqCliente=reqCliente;
	}
	@Override
	public void run() {
		
		
		try {
			//recibo la request por parte del MASTER
			
			//System.out.println(reqCliente.toString());
			
			//busco en la lista de archivos del cliente
			boolean encontrado=false;
			File files= new File(PATHLOCAL+this.nFile);
			folder = new File(PATHLOCAL);
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			        if(file.getName().equals((reqCliente).toString())) {
			        	//System.out.println("Encontrado!!");
			        	encontrado=true;
			        }else {
			        	//System.out.println("NO ESTA!");
			        }
			        	
			    }
			}
			Response respuesta=new Response();
			if (encontrado==true) {
				respuesta.setEncontrado(true);
			}else {
				respuesta.setEncontrado(false);
			}
			
			OutputStream os=this.sock.getOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(os);
			oos.writeObject(respuesta);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		}

}
