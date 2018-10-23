package ej1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import clientes.MensajeInicialización;

public class ServidorDeMasters {

	
	public ServidorDeMasters() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}
	public static void main(String args[]) throws ClassNotFoundException {

		try {
		
			ServerSocket sv=new ServerSocket(7000);
			System.out.println("Servodr de Masters ON");
			/*while(true) {
				Socket sock = sv.accept();
				System.out.println("se me conecto un Master");
			}*/
			while(true){
				Socket sock = sv.accept();
				ServidorThreadM st =new ServidorThreadM(sock);		
				Thread t = new Thread(st);
				
					InputStream is = sock.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);
					MensajeInicialización msj = (MensajeInicialización)ois.readObject();			
					System.out.println(msj.getIp().toString()+" se acaba de conectar");
				
				t.start();
	}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
