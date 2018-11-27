package master.principal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import common.MensajeInicialización;
import common.Request;
import common.masters.MensajeInicializacionServer;
import common.masters.RequestServidor;

public class Master {
	private static final int LISTEN_PORT = 5000;
	static HashMap<Integer, String> hmapNodosExtremos;
	static HashMap<Integer, String> listaServidores;
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket sv= new ServerSocket(LISTEN_PORT);
		System.out.println("Server Principal is running");
		hmapNodosExtremos = new HashMap<Integer, String>();
		listaServidores = new HashMap<Integer, String>();
		 	
			while(true){
				
				Socket sock2 = sv.accept();
				//guardo la ip del nodo extremo que se conecta//
				InputStream is = sock2.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object ob=ois.readObject();
				
				///////////////UNICO//
				if(ob instanceof MensajeInicializacionServer) {
					MensajeInicializacionServer msj = (MensajeInicializacionServer)ob;
					System.out.println(msj.getIp().toString()+" se acaba de conectar");
					synchronized (listaServidores) {
						listaServidores.put(msj.getListPort(), msj.getIp().toString());
					}
					is.close();
					ois.close();
					sock2.close();
				}
				if(ob instanceof MensajeInicialización) {
					MensajeInicialización msj = (MensajeInicialización)ob;
					System.out.println(msj.getIp().toString()+" se acaba de conectar");
					synchronized (hmapNodosExtremos) {
						hmapNodosExtremos.put(msj.getListPort(), msj.getIp().toString());
					}
					is.close();
					ois.close();
					sock2.close();
				
				}else if(ob instanceof Request) {
					Request reqCliente=(Request)ob;
					System.out.println("Request del Cliente: "+reqCliente.toString());
					RequestThread st2 =new RequestThread(sock2,reqCliente,hmapNodosExtremos,reqCliente.getNport());		
					Thread t2 = new Thread(st2);
					t2.start();
				
				
				//Si es una request del servidor es igual a la de los clientes pero me tienen que pasar el cliente que pidio 	
				}else if(ob instanceof RequestServidor) {
					RequestServidor reqServer=(RequestServidor)ob;
					System.out.println("Request del Servidor: "+reqServer.toString());
					ServerRequestThread st2 =new ServerRequestThread(sock2,reqServer,listaServidores,reqServer.getNport());		
					Thread t2 = new Thread(st2);
					t2.start();
					//libero Threads para preguntarles a todos los Servidores y que cada uno le responda (UNICO PORQUE SOY EL MASTER PRINCIPAL)
					
				}	
				
				
				

								
				
		}
		
		
		
	}

}
