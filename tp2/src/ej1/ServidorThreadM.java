package ej1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import clientes.MensajeInicialización;

public class ServidorThreadM implements Runnable{
	Socket sock;
	public ServidorThreadM(Socket sock){
		
		//id=this.getId();
		this.sock=sock;
		}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("hubo una conexion");
		//guardo la ip del master que se conecta
	
		
	}

}
