package clientes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorThreadExtremo implements Runnable{
	private ServerSocket sock;
	
	public  ServidorThreadExtremo(ServerSocket sock) {
		this.sock=sock;
	}
	@Override
	public void run() {
		
		while (true) {
			try {
				Socket sock = this.sock.accept();
				System.out.println("Recibi una conexi�n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
