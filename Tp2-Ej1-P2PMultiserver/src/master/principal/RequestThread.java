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
import java.util.List;
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


public class RequestThread implements Runnable {
	private Socket sock;
	private Socket sReq;
	private HashMap<Integer, String> listaNodosExtremosRegistrados;
	private HashMap<Integer,String>listaServidores;
	private ArrayList<Nodo>listaNodosTotales;
	private InetAddress ipLocal;
	private int portLocal;
	private MsjDirRecurso msjCli;
	private Request reqCliente;
	private RequestServidor reqServer;
	
	public RequestThread(Socket sock,Request reqCliente,HashMap<Integer,String>listaNodos,HashMap<Integer,String>listaServidores,int portLocal){
	
		//id=this.getId();
		this.listaNodosExtremosRegistrados=listaNodos;
		this.listaServidores=listaServidores;
		this.sock=sock;
		this.portLocal=portLocal;
		this.reqCliente=reqCliente;	
	}
		
	@Override
	public void run() {
			
				try {											
									
					////pedimos el recurso a los nodos extremos 
					System.out.println("ip lOCAL::"+ipLocal.getHostAddress());
					
					synchronized (listaNodosExtremosRegistrados) {
						Set<Entry<Integer, String>> set = this.listaNodosExtremosRegistrados.entrySet();
					    Iterator<Entry<Integer, String>> iterator = set.iterator();
					    
					    //mensaje al cliente que nos solicito el recurso
					    this.msjCli=new MsjDirRecurso();
					    
					    //Itero sobre la lista de clientes
					    while(iterator.hasNext()) {
					         Entry<Integer, String> mentry = iterator.next();
					         //Crear nueva capa de threads
					         if(!(mentry.getValue().equals(this.ipLocal.getHostAddress())) || !(mentry.getKey()==this.portLocal)) {
					        	 	sReq=new Socket(mentry.getValue(),Integer.parseInt(mentry.getKey().toString()));
									OutputStream os=sReq.getOutputStream();
									ObjectOutputStream oos= new ObjectOutputStream(os);
									this.reqCliente.settRequest(TipoRequest.CONSULTA);
									oos.writeObject(this.reqCliente);
									
									InputStream is=this.sReq.getInputStream();
								    ObjectInputStream ois = new ObjectInputStream(is);
									Response response=(Response)ois.readObject();
									System.out.println("resp de un cliente: "+response.toString());
									
									
									
									if(response.isEncontrado()) {
										
										//Si lo encuentro guardo la ip en una lista
										this.msjCli.setDireccion(mentry.getKey(),mentry.getValue());
										
									}
					         }
					         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
					         System.out.println(mentry.getValue());
					         
					    }
					}
			
					//Si no encuentro nada en los clientes conectados a mi intento preguntando a los servidores replicas
					if (msjCli.getDirecciones().isEmpty()) {
						listaNodosTotales=new ArrayList<Nodo>();
						//Itero sobre la lista de Servidores
						Set<Entry<Integer, String>> setServ = this.listaServidores.entrySet();
					    Iterator<Entry<Integer, String>> iteratorServ = setServ.iterator();
					    while(iteratorServ.hasNext()) {
					    	Entry<Integer, String> mentry = iteratorServ.next();
					    	
					    	///Abro una conexion contra el Master de cada Servidor Replica
					    	sReq=new Socket(mentry.getValue(),Integer.parseInt(mentry.getKey().toString()));
					    	OutputStream os=sReq.getOutputStream();
							ObjectOutputStream oos= new ObjectOutputStream(os);
							
							//Creo una request al servidor con lo que pidio el cliente
							reqServer= new RequestServidor(reqCliente.getNameResource());
							oos.writeObject(this.reqServer);
							
							//Espero una lista de nodos que tienen el recurso 
							InputStream is=this.sReq.getInputStream();
						    ObjectInputStream ois = new ObjectInputStream(is);
						   
						    //Itero sobre la lista de nodos (OPTIMIZAR ESTO!!)
						    ArrayList<Nodo> listaNodos=(ArrayList<Nodo>)ois.readObject();
						    int size = listaNodos.size();
						    for (Nodo i : listaNodos) { 
						    	
						    	//Agrego a la lista de Nodos totales
						    	listaNodosTotales.add(i);
						    }
					    }
						
						}
						//Envío la respuesta al cliente
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
	}
	
	
}
