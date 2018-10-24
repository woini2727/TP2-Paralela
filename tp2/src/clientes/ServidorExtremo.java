package clientes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ej1.ServidorThread;

public class ServidorExtremo implements Runnable{
	private ServerSocket sock;
	
	public  ServidorExtremo() throws IOException {
		sock=new ServerSocket(6000);
	}
	@Override
	public void run() {
		
		while (true) {
			try {
				Socket sockt = this.sock.accept();
				ServidorThreadExtremo st =new ServidorThreadExtremo(sockt);		
				Thread t = new Thread(st);
				
				System.out.println("Recibi una conexión");
				t.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
