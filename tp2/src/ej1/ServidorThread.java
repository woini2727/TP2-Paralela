package ej1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import common.MensajeInicialización;
import common.Request;
import common.Response;

public class ServidorThread implements Runnable {
	private Socket sock;
	private Socket sReq;
	ArrayList<String> listaNodosExtremos;
	
	public ServidorThread(Socket sock,ArrayList<String>listaNodos){
	
		//id=this.getId();
		this.listaNodosExtremos=listaNodos;
		this.sock=sock;}

	@Override
	public void run() {
			//System.out.println("ServerThread is running");
			
				try {	
					
					InputStream is = this.sock.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);
					Request reqCliente=(Request)ois.readObject();
					System.out.println(reqCliente.toString());
					
					//pedimos el recurso a los nodos extremos y a los otros masters
					//por cada direccion en la lista mandamos a cada NodoExtremo
					
					for(Iterator<String> i =this.listaNodosExtremos.iterator();i.hasNext();) {
						String item=i.next();
						sReq=new Socket("localhost",6000);
						OutputStream os=sReq.getOutputStream();
						ObjectOutputStream oos= new ObjectOutputStream(os);
						oos.writeObject(reqCliente);
					}
					//recibimos respuesta del cliente
					is=this.sReq.getInputStream();
					ois = new ObjectInputStream(is);
					Response response=(Response)ois.readObject();
					System.out.println(response.toString());
					if(response.isEncontrado()==true) {
						System.out.println("Encontrado");
					}else {
						System.out.println("NO encontrado");
					}
					
					
										
				} catch(IOException e) {
					System.out.println("Se corto la conexion con el cliente");
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
