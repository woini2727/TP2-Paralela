package ej1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import clientes.MensajeInicialización ;
import clientes.Request;

public class ServidorThread implements Runnable {
	private Socket sock;	 

	public ServidorThread(Socket sock){
	
		//id=this.getId();
		this.sock=sock;}

	@Override
	public void run() {
			System.out.println("ServerThread is running");
			/*try {
				InputStream is = this.sock.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				MensajeInicialización msj = (MensajeInicialización)ois.readObject();
				System.out.println(msj.getIp().toString());
				is.close();
				ois.close();
				
				this.sock.close();
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		//recibo y respondo mensajes que vienen del menu
			//while(true) {
				try {	
					
					InputStream is = this.sock.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);
					Request reqCliente=(Request)ois.readObject();
					System.out.println(reqCliente.toString());
					
					//pedimos el recurso
					
					
					
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
