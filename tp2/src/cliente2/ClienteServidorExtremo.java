package cliente2;

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

public class ClienteServidorExtremo {
	 int port=5000;
	Socket sockCli;
	String nFile="dataCliente1.txt";
	
	public ClienteServidorExtremo() {
		
	}
	public void iniciarServidor() throws IOException {
		ServerSocket sv= new ServerSocket(6001);
		System.out.println("Server is running");
		//Socket sock = sv.accept();
		ServidorThreadExtremo st =new ServidorThreadExtremo(sv);
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
		//c1.sockCli= new Socket("localhost",6000);
		//creo un ouputstream
		os = c1.sockCli.getOutputStream();
		//para leer el os
		oos = new ObjectOutputStream(os);
		msj=new MensajeInicialización();
		oos.writeObject(msj);
		//close
		oos.close();
		os.close();
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
				Scanner sc2= new Scanner(System.in);
				opcion =sc2.next();
				System.out.println("Buscando...");
				//socket al master con los archivos que quiero buscar
				
				//aca recibo la respuesta del Master que me dice quien tiene el/los archivos
				//por cada archivo reviso ping a la ip y descargo a la ip con menos rtt
				
				
				break;
			}else if(opcion.equals("2")) {
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
