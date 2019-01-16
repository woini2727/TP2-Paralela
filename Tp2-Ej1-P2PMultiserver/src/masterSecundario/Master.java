package masterSecundario;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import clientes.ClienteServidorExtremo;
import common.MensajeInicialización;
import common.Request;
import common.masters.MensajeInicializacionServer;
import common.masters.Nodo;
import common.masters.RequestServidor;

public class Master {
	private static final int LISTEN_PORT = 5001;
	private static final int MASTER_PORT = 5000;
	private static final String SERVER_IP = "localhost";
	static HashMap<Integer, String> hmapNodosExtremos;
	static HashMap<Integer, String> listaServidores;
	Socket sockMaster;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		OutputStream os;
		ObjectOutputStream oos;
		MensajeInicializacionServer msjServ;
		ServerSocket sv= new ServerSocket(LISTEN_PORT);
		System.out.println("Server Principal 2 is running");
		hmapNodosExtremos = new HashMap<Integer, String>();
		listaServidores = new HashMap<Integer, String>();
		
		////////////////////**Me conecto al Server Principal**///////////////////////////////////
		try {
			Master m1= new Master();
			m1.sockMaster= new Socket(SERVER_IP,MASTER_PORT); //me conecto al master
			m1.sockMaster.setKeepAlive(true);
			os=m1.sockMaster.getOutputStream();
			//Request req = new Request(in);
			oos = new ObjectOutputStream(os);
			msjServ=new MensajeInicializacionServer();
			msjServ.setListPort(m1.LISTEN_PORT);
			//req.setNport(m1.portServ);
			oos.writeObject(msjServ);
		}catch(IOException e) {
			System.out.println("No Hay conexión con el servidor");
		}
		
		////////////////////**********************************///////////////////////////////////
		
			while(true){
				
				Socket sock2 = sv.accept();
				InputStream is = sock2.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				Object ob=ois.readObject();

				//Registramos un nuevo cliente
				if(ob instanceof MensajeInicialización) {
					MensajeInicialización msj = (MensajeInicialización)ob;
					System.out.println(msj.getIp().toString()+" se acaba de conectar");
					synchronized (hmapNodosExtremos) {
						hmapNodosExtremos.put(msj.getListPort(), msj.getIp().toString());
					}
					is.close();
					ois.close();
					sock2.close();
				
				//Request Del cliente
				}else if(ob instanceof Request) {
					Request reqCliente=(Request)ob;
					System.out.println("Request del Cliente: "+reqCliente.toString());
					RequestThread st2 =new RequestThread(sock2,reqCliente,hmapNodosExtremos,reqCliente.getNport());		
					Thread t2 = new Thread(st2);
					t2.start();
				
				
				//Requeste de Servidor Principal 	
				}else if(ob instanceof RequestServidor) {
					RequestServidor reqServer=(RequestServidor)ob;
					System.out.println("Request del Servidor: "+reqServer.toString());
					ServerRequestThread st2 =new ServerRequestThread(sock2,reqServer,hmapNodosExtremos,reqServer.getPort());		
					Thread t2 = new Thread(st2);
					t2.start();
					//libero Threads para preguntarles a todos los Servidores y que cada uno le responda (UNICO PORQUE SOY EL MASTER PRINCIPAL)
					
				}
				
				
				

								
				
		}
		
		
		
	}

}
