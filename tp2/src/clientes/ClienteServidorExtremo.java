package clientes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;
import org.apache.commons.io.IOUtils; 
import com.fasterxml.jackson.databind.ObjectMapper;


public class ClienteServidorExtremo {
	 int port=5000;
	Socket sockCli;
	String nFile="dataCliente1.txt";
	ServerSocket sv; 
	
	public ClienteServidorExtremo() throws IOException {
		this.sv=new ServerSocket(6000);
	}
	public void iniciarServidor() throws IOException {
		
		System.out.println("Server is running");
		//Socket sock = sv.accept();
		ServidorThreadExtremo st =new ServidorThreadExtremo(this.sv);
		Thread t = new Thread(st);
		t.start();
		
	}
			
	public static void main(String args[]) throws UnknownHostException, IOException {
	
		ClienteServidorExtremo c1= new ClienteServidorExtremo();
		OutputStream os;
		ObjectOutputStream oos;
		MensajeInicialización msj;
		String opcion;
		File folder;
		
		//Se inicia el modo Servidor
		c1.iniciarServidor();
		
		c1.sockCli= new Socket("localhost",c1.port);
		//creo un ouputstream
		os = c1.sockCli.getOutputStream();
		//para leer el os
		oos = new ObjectOutputStream(os);
		msj=new MensajeInicialización();
		oos.writeObject(msj);
		//close
		oos.close();
		os.close();
		c1.sockCli.close();
		//menu
		
		
		while(true) {
			System.out.println("Menu:");
			System.out.println("1-Buscar y Descargar Archivo");
			System.out.println("2-Actualizar mis archivos");
			System.out.println("3-Listar mis archivos");
			System.out.println("4-salir");
			Scanner sc= new Scanner(System.in);
			opcion =sc.next();	
			
			if (opcion.equals("1")) {
				System.out.println("");
				System.out.println("Ingrese el nomrbe del/los archivo/s que desea descargar (ej: archivo1.txt-archivo2.txt)");
				Scanner input=new Scanner(System.in);
				String in=input.next();
				System.out.println("Buscando "+in+"...");
				
				//request al Master
				c1.sockCli= new Socket("localhost",c1.port); //me conecto al master
				os=c1.sockCli.getOutputStream();
				
				//creo el JSON que voy a mandar (instalo maven dependency)
				Foo foo = new Foo(1,"first");
			    ObjectMapper mapper = new ObjectMapper();
			 
			    String jsonStr = mapper.writeValueAsString(foo);
			    Foo result = mapper.readValue(jsonStr, Foo.class);
				
				//aca recibo la respuesta del Master que me dice quien tiene el/los archivos
				//por cada archivo reviso ping a la ip y descargo a la ip con menos rtt
				
				
				
				break;
			}else if(opcion.equals("2")) { 					//Actualiza mi directorio si mientras estoy sirviendo se agrega algo
				folder = new File("src/clientes/files/");
				File files= new File("src/clientes/files/"+c1.nFile);
				File[] listOfFiles = folder.listFiles();
				//BufferedWriter writer = null;
				//writer = new BufferedWriter(new FileWriter(files));
				FileWriter fileWriter = new FileWriter(files);
			    PrintWriter printWriter = new PrintWriter(fileWriter);
			    
				for (File file : listOfFiles) {
				    if (file.isFile()) {
				    
				    	printWriter.println(file.getName());				        
				       
				    }
				}
				printWriter.close();
				System.out.println("Archivos actualizados!!");
			}else if(opcion.equals("3")) {
				folder = new File("src/clientes/files/");
				File[] listOfFiles = folder.listFiles();
				System.out.println("");
				System.out.println("Mis archivos:");
				System.out.println("");
				for (File file : listOfFiles) {
				    if (file.isFile()) {
				        System.out.println(file.getName());
				    }
				}
				System.out.println("");
			
			}else if(opcion.equals("4")) {
				break;
			}
			
		}
		
	}
	
			
	
}
