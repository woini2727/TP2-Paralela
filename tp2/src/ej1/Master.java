package ej1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {

	public static void main(String[] args) throws IOException {
		ServerSocket sv= new ServerSocket(5000);
		System.out.println("Server is running");
		while(true){
			Socket sock = sv.accept();
			ServidorThread st =new ServidorThread(sock);
			
			Thread t = new Thread(st);
			
			t.start();
			

		}
		
	}

}
