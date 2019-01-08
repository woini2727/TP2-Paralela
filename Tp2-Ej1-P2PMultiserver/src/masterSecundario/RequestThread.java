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
	private ArrayList<Nodo>listaNodosTotales;
	private String ipLocal;
	private int portLocal;
	private MsjDirRecurso msjCli;
	private Request reqCliente;
	private RequestServidor reqServer;
	
	public RequestThread(Socket sock,Request reqCliente,HashMap<Integer,String>listaNodos,int portLocal){
	
		this.ipLocal=reqCliente.getIp();
		this.listaNodosExtremosRegistrados=listaNodos;

		this.sock=sock;
		this.portLocal=portLocal;
		this.reqCliente=reqCliente;	
	}
		
	@Override
	public void run() {
			
				try {											
									
					////pedimos el recurso a los nodos extremos 
					System.out.println("ip lOCAL::"+this.ipLocal);
					
					synchronized (listaNodosExtremosRegistrados) {
						Set<Entry<Integer, String>> set = this.listaNodosExtremosRegistrados.entrySet();
					    Iterator<Entry<Integer, String>> iterator = set.iterator();
					    
					    //mensaje al cliente que nos solicito el recurso
					    this.msjCli=new MsjDirRecurso();
					    
					    //Itero sobre la lista de clientes
					    while(iterator.hasNext()) {
					         Entry<Integer, String> mentry = iterator.next();
					         //Crear nueva capa de threads
					         if(!(mentry.getValue().equals(this.ipLocal)) || !(mentry.getKey()==this.portLocal)) {
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
										this.msjCli.setDireccion(mentry.getKey(),mentry.getValue());}
					         }     
					    }
					}
					
					//Si no encuentro nada en los clientes le pregunto al master
					if (msjCli.getDirecciones().isEmpty()) {
						listaNodosTotales=new ArrayList<Nodo>();
						//Itero sobre la lista de Servidores
					    	
					    	///Abro una conexion contra el Master principal (hardcodeado)
					    	sReq=new Socket("localhost",5000);
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
						    for (Nodo nodo : listaNodos) { 
						    	
						    	//Agrego a la lista de Nodos totales
						    	listaNodosTotales.add(nodo);
						    	this.msjCli.setDireccion(nodo.getPort(), nodo.getIp());
						    }
					    }

						//Envío la respuesta al Master principal
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
