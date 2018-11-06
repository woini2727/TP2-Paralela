package ej1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import common.MensajeInicialización;
import common.MsjDirRecurso;
import common.Request;
import common.Response;

public class ServidorThread implements Runnable {
	private Socket sock;
	private Socket sReq;
	ArrayList<String> listaNodosExtremos;
	InetAddress ipLocal;
	MsjDirRecurso msjCli;
	
	public ServidorThread(Socket sock,ArrayList<String>listaNodos){
	
		//id=this.getId();
		this.listaNodosExtremos=listaNodos;
		this.sock=sock;}

	@Override
	public void run() {
			
				try {	
					//REEVER//////////
					
					InputStream is = this.sock.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(is);
					Request reqCliente=(Request)ois.readObject();
					System.out.println(reqCliente.toString());
					
					//pedimos el recurso a los nodos extremos y a los otros masters
					//por cada direccion en la lista mandamos a cada NodoExtremo
					this.ipLocal=(InetAddress) this.sock.getInetAddress();
					System.out.println("ip lOCAL::"+ipLocal.getHostAddress());
					
					
					for(Iterator<String> i =this.listaNodosExtremos.iterator();i.hasNext();) {
						String dir=i.next();
						if(!dir.equalsIgnoreCase("ss")) {
							sReq=new Socket(dir,6000);
							OutputStream os=sReq.getOutputStream();
							ObjectOutputStream oos= new ObjectOutputStream(os);
							oos.writeObject(reqCliente);
						}
						
					}
					//FOR PARA RECIBIR DEPENDIENDO CUANTO MANDE
					//recibimos respuesta del cliente si encontro o no el archivo
					for(int i = 0;i<this.listaNodosExtremos.size();i++) {
						is=this.sReq.getInputStream();
						ois = new ObjectInputStream(is);
						Response response=(Response)ois.readObject();
						System.out.println("resp de un cliente: "+response.toString());
						this.msjCli =new MsjDirRecurso();
						if(response.isEncontrado()==true) {
							//Si lo encuentro guardo la ip en una lista y se la doy al cliente
							
							this.msjCli.setDireccion(this.listaNodosExtremos.get(i));
							
						}else {
							this.msjCli.setDireccion("nada");
						}
					}
					
					//Si no encuentro nada y le mando la lista vacía si no le mando las direcciones
					OutputStream os=this.sock.getOutputStream();
					ObjectOutputStream oos=new ObjectOutputStream(os);
					oos.writeObject(this.msjCli);
					
										
				} catch(IOException e) {
					System.out.println("Se corto la conexion con el cliente");
					try {
						this.sock.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			//}
	}
	public void find() {
		
	}
	
}
