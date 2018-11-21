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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import common.MensajeInicialización;
import common.MsjDirRecurso;
import common.Request;
import common.Response;

public class RequestThread implements Runnable {
	private Socket sock;
	private Socket sReq;
	private HashMap<Integer, String> listaNodosExtremosRegistrados;
	private HashMap<Integer,String>listaNodosExtremos;
	private InetAddress ipLocal;
	private int portLocal;
	private MsjDirRecurso msjCli;
	private Request reqCliente;
	
	public RequestThread(Socket sock,Request reqCliente,HashMap<Integer,String>listaNodos,int portLocal){
	
		//id=this.getId();
		this.listaNodosExtremosRegistrados=listaNodos;
		this.sock=sock;
		this.portLocal=portLocal;
		this.reqCliente=reqCliente;
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
					    this.msjCli=new MsjDirRecurso();
					    while(iterator.hasNext()) {
					         Entry<Integer, String> mentry = iterator.next();
					      //   if(!((mentry.getValue()).equals(this.ipLocal.toString()))&&(mentry.getKey().equals(this.portLocal))) {
					        	 	sReq=new Socket(mentry.getValue(),Integer.parseInt(mentry.getKey().toString()));
									OutputStream os=sReq.getOutputStream();
									ObjectOutputStream oos= new ObjectOutputStream(os);
									oos.writeObject(reqCliente);
									
									InputStream is=this.sReq.getInputStream();
								    ObjectInputStream ois = new ObjectInputStream(is);
									Response response=(Response)ois.readObject();
									System.out.println("resp de un cliente: "+response.toString());
									 
									if(response.isEncontrado()==true) {
										//Si lo encuentro guardo la ip en una lista y se la doy al cliente
										
										this.msjCli.setDireccion(mentry.getKey(),mentry.getValue());
										
									}else {
										this.msjCli.setDireccion(0,"nada");
									}
					        // }
					         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
					         System.out.println(mentry.getValue());
					         
					    }
					}
							
					
					//FOR PARA RECIBIR DEPENDIENDO CUANTO MANDE
					//recibimos respuesta del cliente si encontro o no el archivo
					this.listaNodosExtremos=new HashMap<Integer, String>();	    
				    Set<Entry<Integer, String>> set2 = this.listaNodosExtremos.entrySet();
				    Iterator<Entry<Integer, String>> iterator2 = set2.iterator();
				    
				    while(iterator2.hasNext()) {
				    	Entry<Integer, String> mentry = iterator2.next();
					    //InputStream is=this.sReq.getInputStream();
					    //ObjectInputStream ois = new ObjectInputStream(is);
						//Response response=(Response)ois.readObject();
						//System.out.println("resp de un cliente: "+response.toString());
						//this.msjCli =new MsjDirRecurso();
						
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
