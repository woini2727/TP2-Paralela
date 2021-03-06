package clientes2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import common.Request;
import common.TipoRequest;
import master.RequestThread;

public class RequestListener implements Runnable{
	private TipoRequest tRequest;
	private ServerSocket sock;
	InputStream is;
	ObjectInputStream ois;
	Request reqCliente;
	public  RequestListener() throws IOException {
		sock=new ServerSocket(6001);
	}

	
	@Override
	public void run() {
		
		while (true) {
			
			try {
				Socket sockt = this.sock.accept();
				is = sockt.getInputStream();
				ois = new ObjectInputStream(is);
				reqCliente=(Request)ois.readObject();
				
				switch (reqCliente.gettRequest()) {
				
				case CONSULTA: {
				
					ThreadBusquedaRecurso tba =new ThreadBusquedaRecurso(sockt,reqCliente);		
					Thread t = new Thread(tba);
					t.start();
					break;
				}
				case PEDIDO_TRANFERENCIA:{
					
					ThreadEnviarArchivo tea =new ThreadEnviarArchivo(reqCliente);		
					Thread t = new Thread(tea);
					t.start();
					break;
				}
				case TRANSFERENCIA:{
					System.out.println("recibe archivoooooo");
					ThreadRecibirArchivo tra=new ThreadRecibirArchivo(sockt);
					Thread t= new Thread (tra);
					t.start();
					break;
				}
				default:
					break;
				}
			
				
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	public ServerSocket getSock() {
		return sock;
	}
	public void closeSock() throws IOException {
		this.sock.close();
	}
	
}
