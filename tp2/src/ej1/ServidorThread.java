package ej1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import common.MensajeInicialización;
import common.Request;

public class ServidorThread implements Runnable {
	private Socket sock;	 
	ArrayList<String> listaNodosExtremos;
	
	public ServidorThread(Socket sock,ArrayList<String>listaNodos){
	
		//id=this.getId();
		this.listaNodosExtremos=listaNodos;
		this.sock=sock;}

	@Override
	public void run() {
			System.out.println("ServerThread is running");
			
				try {	
					
					InputStream is = this.sock.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);
					Request reqCliente=(Request)ois.readObject();
					System.out.println(reqCliente.toString());
					
					//pedimos el recurso a los nodos extremos y a los otros masters
					//por cada direccion en la lista mandamos a cada NodoExtremo
					Socket sReq;
					for(Iterator<String> i =this.listaNodosExtremos.iterator();i.hasNext();) {
						String item=i.next();
						sReq=new Socket("localhost",6000);
					}
					
					
					
				} catch(IOException e) {
					System.out.println("Se corto la conexion");
					try {
						this.sock.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			//}
	}
	public void find() {
		
	}
	
}
