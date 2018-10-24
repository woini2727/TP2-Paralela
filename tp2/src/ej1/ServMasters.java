package ej1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import common.MensajeInicialización;

public class ServMasters implements Runnable{
	ArrayList<String>listaDeMasters;
	public ServMasters(ArrayList<String> lista) {
		this.listaDeMasters=lista;
		
	}
	@Override
	public void run() {
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
					this.listaDeMasters.add(msj.getIp().toString());
				t.start();
	}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
