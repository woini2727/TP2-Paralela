package ej1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import clientes.MensajeInicialización ;

public class ServidorThread implements Runnable {
	private Socket sock;	 

	public ServidorThread(Socket sock)throws IOException {
	
		//id=this.getId();
		this.sock=sock;}

	@Override
	public void run() {
			System.out.println("ServerThread is running");
			try {
				InputStream is = this.sock.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				MensajeInicialización msj = (MensajeInicialización)ois.readObject();
				System.out.println(msj.getIp().toString());
				is.close();
				//RECIBO EL/LOS RECURSOS
				//Los busco
				//ARMO EL JSON Y ENVIO
				this.sock.close();
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void find() {
		
	}
	
}
