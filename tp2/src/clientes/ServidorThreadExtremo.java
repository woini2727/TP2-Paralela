package clientes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import common.Request;

public class ServidorThreadExtremo implements Runnable {
	Socket sock;
	File folder;
	String nFile="dataCliente1.txt";
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
			
			File files= new File("src/clientes/files/"+this.nFile);
			folder = new File("src/clientes/files/");
			File[] listOfFiles = folder.listFiles();
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			        if(file.getName().equals((reqCliente).toString())) {
			        	System.out.println("Encontrado!!");
			        }else {
			        	System.out.println("NO ESTA!");
			        }
			        	
			    }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

}
