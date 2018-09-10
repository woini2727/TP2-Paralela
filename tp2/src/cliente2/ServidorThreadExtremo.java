package cliente2;

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
				System.out.println("Cliente 2 Thread");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
