package masterSecundario;

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
import common.masters.Nodo;
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
	private ArrayList<Nodo> listaNodos;
	private ArrayList<Nodo>listaNodosTotales;
	
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
					listaNodosTotales = new ArrayList<Nodo>();											
					
					//Tomo la IP del socket (para no enviar la request al mismo que me la pide)
					this.ipLocal=(InetAddress) this.sock.getInetAddress();         					
					System.out.println("ip lOCAL::"+ipLocal.getHostAddress());
					
					
					//por cada direccion en la lista mandamos a cada NodoExtremo//
					synchronized (listaNodosExtremosRegistrados) {
						Set<Entry<Integer, String>> set = this.listaNodosExtremosRegistrados.entrySet();
					    Iterator<Entry<Integer, String>> iterator = set.iterator();
					    
					    while(iterator.hasNext()) {
					         Entry<Integer, String> mentry = iterator.next();
					         
					         if(!(mentry.getValue().equals(this.ipLocal.getHostAddress())) || !(mentry.getKey()==this.portLocal)) {
					        	 	sReq=new Socket(mentry.getValue(),Integer.parseInt(mentry.getKey().toString()));
					        	 	System.out.println(mentry.getValue()+Integer.parseInt(mentry.getKey().toString()));
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
									
									if(response.isEncontrado()) {
										//Si lo encuentro guardo el nodo en una lista
										//Creo un nuevo Nodo
										Nodo nodo =new Nodo(mentry.getValue(),mentry.getKey());
										//Agrego los Nodos al arreglo
										this.listaNodosTotales.add(nodo);
										System.out.println("Nodo que tiene la request: "+"IP: "+nodo.getIp()+" Port: "+nodo.getPort());
										//this.msjCli.setDireccion(mentry.getKey(),mentry.getValue());
										
									}
					         }
					    }
					}				
					//Respuesta al Master pirncipal que me consulta
					OutputStream os=this.sock.getOutputStream();
					ObjectOutputStream oos=new ObjectOutputStream(os);
					oos.writeObject(this.listaNodosTotales);
					
					/*os.close();
					oos.close();
					this.sock.close();*/
										
				} catch(IOException e) {
					System.out.println("Se corto la conexion con el cliente srme");
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
