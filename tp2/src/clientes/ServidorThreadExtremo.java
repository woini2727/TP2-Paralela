package clientes;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import common.Request;

public class ServidorThreadExtremo implements Runnable {
	Socket sock;
	public ServidorThreadExtremo(Socket sock) {
		this.sock=sock;
	}
	@Override
	public void run() {
		System.out.println("reviso los recursos locales");
		InputStream is;
		try {
			is = this.sock.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			Request reqCliente=(Request)ois.readObject();
			System.out.println(reqCliente.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

}
