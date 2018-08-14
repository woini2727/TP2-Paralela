package ej1;

import java.io.IOException;
import java.net.Socket;

public class ServidorThread implements Runnable {
	private Socket sock;	 

	public ServidorThread(Socket sock)throws IOException {
	
		//id=this.getId();
		this.sock=sock;}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
