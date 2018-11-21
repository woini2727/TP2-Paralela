package ej1;

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

public class Master {
	static HashMap<Integer, String> hmapNodosExtremos;
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket sv= new ServerSocket(5000);
		System.out.println("Server Principal is running");
		//listaNodosExtremos=new ArrayList<String>();
		hmapNodosExtremos = new HashMap<Integer, String>();
		
		//abro un servidor que escucha los otros Masters
		
		/*ServidorDeMasters SMaster=new ServidorDeMasters();
		ServMasters sm=new ServMasters(listaNodosExtremos);
		Thread tsm=new Thread(sm);
		tsm.start();
		
			Socket sock = sv.accept();
			ServidorThread st =new ServidorThread(sock,listaNodosExtremos);		
			Thread t = new Thread(st);
			t.start();*/
			
			while(true){
				
				Socket sock2 = sv.accept();
				//guardo la ip del nodo extremo que se conecta//
				InputStream is = sock2.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object ob=ois.readObject();
				int port = 0;
				if(ob instanceof MensajeInicialización) {
					MensajeInicialización msj = (MensajeInicialización)ob;
					System.out.println(msj.getIp().toString()+" se acaba de conectar");
					synchronized (hmapNodosExtremos) {
						hmapNodosExtremos.put(msj.getListPort(), msj.getIp().toString());
					}
					port=msj.getListPort();
				}else if(ob instanceof Request) {
					Request reqCliente=(Request)ob;
					System.out.println("Request del Cliente: "+reqCliente.toString());
					RequestThread st2 =new RequestThread(sock2,reqCliente,hmapNodosExtremos,port);		
					Thread t2 = new Thread(st2);
					t2.start();
				}		
				

				//mando a los otros masters la conexion (completar)
				/*for(Iterator<String> i= listaNodosExtremos.iterator(); i.hasNext();) {
					String item=i.next();
					System.out.println(item);
				}*/
								
				
		}
		
		
		
	}

}
