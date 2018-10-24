package clientes;

import java.net.Socket;

public class ServidorThreadExtremo implements Runnable {
	Socket sock;
	public ServidorThreadExtremo(Socket sock) {
		this.sock=sock;
	}
	@Override
	public void run() {
		System.out.println("reviso los recursos locales");
	}

}
