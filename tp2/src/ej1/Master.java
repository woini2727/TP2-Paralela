package ej1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import common.MensajeInicialización;

public class Master {
	static ArrayList<String> listaNodosExtremos; 
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket sv= new ServerSocket(5000);
		System.out.println("Server Principal is running");
		listaNodosExtremos=new ArrayList<String>();
		//abro un servidor que escucha los otros Masters
		//ServidorDeMasters SMaster=new ServidorDeMasters();
		/*ServMasters sm=new ServMasters(listaNodosExtremos);
		Thread tsm=new Thread(sm);
		tsm.start();*/
		
			Socket sock = sv.accept();
			ServidorThread st =new ServidorThread(sock,listaNodosExtremos);		
			Thread t = new Thread(st);
			
			//guardo la ip del nodo extremo que se conecta
			InputStream is = sock.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			MensajeInicialización msj = (MensajeInicialización)ois.readObject();			
			System.out.println(msj.getIp().toString()+" se acaba de conectar");
			listaNodosExtremos.add(msj.getIp().toString());
			//mando a los otros masters la conexion
			for(Iterator<String> i= listaNodosExtremos.iterator(); i.hasNext();) {
				String item=i.next();
				System.out.println(item);
			}
			
			t.start();
			
			while(true){
				Socket sock2 = sv.accept();
				ServidorThread st2 =new ServidorThread(sock2,listaNodosExtremos);		
				Thread t2 = new Thread(st2);
				t2.start();
		}
		
		
		
	}

}
