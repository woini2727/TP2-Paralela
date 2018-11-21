package clientes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;


import com.fasterxml.jackson.databind.ObjectMapper;

import common.MensajeInicialización;
import common.MsjDirRecurso;
import common.Request;


public class ClienteServidorExtremo {
	int port=5000;
	int portServ=6000;
	Socket sockCli;
	String nFile="dataCliente1.txt";
	ServidorExtremo st ;
	MsjDirRecurso msjMaster;
	
	public ClienteServidorExtremo() throws IOException {
		
	}
	public void iniciarServidor() throws IOException {

		this.st =new ServidorExtremo();
		Thread t = new Thread(st);
		t.start();
		
	}
			
	public static void main(String args[]) throws UnknownHostException, IOException, InterruptedException, ClassNotFoundException {
	
		ClienteServidorExtremo c1= new ClienteServidorExtremo();
		OutputStream os;
		ObjectOutputStream oos;
		MensajeInicialización msj;
		String opcion;
		File folder;
		
		//Se inicia el modo Servidor
		c1.iniciarServidor();
		while(true) {
			try {
				c1.sockCli= new Socket("localhost",c1.port);
				//creo un ouputstream
				os = c1.sockCli.getOutputStream();
				//para leer el os
				oos = new ObjectOutputStream(os);
				msj=new MensajeInicialización();
				msj.setListPort(c1.portServ);
				oos.writeObject(msj);
				break;
			}catch(IOException e) {
				System.out.println("No Hay conexión con el servidor");
				System.out.println("Reintentando conexión...");
				TimeUnit.SECONDS.sleep(2);
			}
		}
		
		
		//close
		/*oos.close();
		os.close();
		c1.sockCli.close();*/
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
				//ClienteServidorExtremo c2= new ClienteServidorExtremo();
				try {
					
					c1.sockCli= new Socket("192.168.0.106",c1.port); //me conecto al master
					os=c1.sockCli.getOutputStream();
					Request req = new Request(in);
					oos = new ObjectOutputStream(os);
					oos.writeObject(req);
				}catch(IOException e) {
					System.out.println("No Hay conexión con el servidor");
					System.out.println("Reintentando conexión...");
					TimeUnit.SECONDS.sleep(2);
				}
				
				//recibo la lista del Master con las direcciones de los que tienen mis recursos
				InputStream is=c1.sockCli.getInputStream();
				ObjectInputStream ois=new ObjectInputStream(is);
				MsjDirRecurso msjDelMaster2=(MsjDirRecurso)ois.readObject();
				if(msjDelMaster2.getDirecciones().isEmpty()) {
					System.out.println("");
				}else {
					//ACA DEBERIAMOS CONECTARNOS DIRECTAMENTE CON ALGUN NODO EXTERNO PARA DESCARGAR EL RECURSO					
					System.out.println("Not empty");
					System.out.println(msjDelMaster2.getDirecciones().get(0));
					
				}
				
				
				
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
		
		c1.sockCli.close();
		//System.out.println("cliente cerrado");
	}
	
			
	
}
