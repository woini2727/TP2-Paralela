package master.principal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import common.MensajeInicialización;
import common.MsjDirRecurso;
import common.Request;
import common.Response;
import common.TipoRequest;
import common.masters.RequestServidor;

public class ServerRequestThread implements Runnable {
	private Socket sock;
	private Socket sReq;
	private HashMap<Integer, String> listaNodosExtremosRegistrados;
	private InetAddress ipLocal;
	private int portLocal;
	private MsjDirRecurso msjCli;
	private RequestServidor reqServer;
	private Request reqCliente;
	
	public ServerRequestThread(Socket sock,RequestServidor reqServer,HashMap<Integer,String>listaNodos,int portLocal){
	
		//id=this.getId();
		this.listaNodosExtremosRegistrados=listaNodos;
		this.sock=sock;
		this.portLocal=portLocal;
		this.reqServer=reqServer;
	
	}
		
	@Override
	public void run() {
			
				try {											
									
					////pedimos el recurso a los nodos extremos y a los otros masters////
					
					//por cada direccion en la lista mandamos a cada NodoExtremo//
					
					//Tomo la IP del socket (para no enviar la request al mismo que me la pide)
					this.ipLocal=(InetAddress) this.sock.getInetAddress();         					
					System.out.println("ip lOCAL::"+ipLocal.getHostAddress());
					
					synchronized (listaNodosExtremosRegistrados) {
						Set<Entry<Integer, String>> set = this.listaNodosExtremosRegistrados.entrySet();
					    Iterator<Entry<Integer, String>> iterator = set.iterator();
					    
					    while(iterator.hasNext()) {
					         Entry<Integer, String> mentry = iterator.next();
					         
					         if(!(mentry.getValue().equals(this.ipLocal.getHostAddress())) || !(mentry.getKey()==this.portLocal)) {
					        	 	sReq=new Socket(mentry.getValue(),Integer.parseInt(mentry.getKey().toString()));
									OutputStream os=sReq.getOutputStream();
									ObjectOutputStream oos= new ObjectOutputStream(os);
									
									//Creo una request Cliente con el recuros que viene de la req del Servidor Principal
									this.reqCliente =new Request(this.reqServer.getNameResource());
									this.reqCliente.settRequest(TipoRequest.CONSULTA);
									oos.writeObject(this.reqCliente);
									
									InputStream is=this.sReq.getInputStream();
								    ObjectInputStream ois = new ObjectInputStream(is);
									Response response=(Response)ois.readObject();
									System.out.println("resp de un cliente: "+response.toString());
									
									//deberia cerrar la conexion
									
									if(response.isEncontrado()) {
										//Si lo encuentro guardo el nodo en una lista
										
										
										
									}
					         }
					         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
					         System.out.println(mentry.getValue());
					         
					    }
					}
							
					
					
					
					//Si no encuentro nada y le mando la lista vacía si no le mando las direcciones
					OutputStream os=this.sock.getOutputStream();
					ObjectOutputStream oos=new ObjectOutputStream(os);
					oos.writeObject(this.msjCli);
					
					os.close();
					oos.close();
					this.sock.close();
										
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
