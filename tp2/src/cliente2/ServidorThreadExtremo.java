package cliente2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import common.Request;
import common.Response;

public class ServidorThreadExtremo implements Runnable {
	Socket sock;
	File folder;
	String nFile="dataCliente2.txt";
	public ServidorThreadExtremo(Socket sock) {
		this.sock=sock;
	}
	@Override
	public void run() {
		System.out.println("reviso los recursos locales");
		InputStream is;
		try {
			//recibo la request por parte del MASTER
			is = this.sock.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			Request reqCliente=(Request)ois.readObject();
			System.out.println(reqCliente.toString());
			
			//busco en la lista de archivos del cliente
			boolean encontrado=false;
			File files= new File("src/cliente2/files/"+this.nFile);
			folder = new File("src/cliente2/files/");
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			        if(file.getName().equals((reqCliente).toString())) {
			        	System.out.println("Encontrado!!");
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

}
