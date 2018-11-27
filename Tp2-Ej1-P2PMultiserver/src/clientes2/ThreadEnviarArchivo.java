package clientes2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import common.CorregirRuta;
import common.Request;
import common.TipoRequest;

public class ThreadEnviarArchivo implements Runnable{
	
	private static final String PATHFILE = "src/clientes2/files/";
	Request reqCliente;
	Socket socket;
	public ThreadEnviarArchivo(Request reqCliente) {
		this.reqCliente=reqCliente;
		
	}
	@Override
	public void run() {
		 try{
			 System.out.println(reqCliente.getDir());
			 System.out.println(reqCliente.getPort());
			 socket = new Socket(reqCliente.getDir(),reqCliente.getPort());
			 //Creo un nuevo Socket
			 //Mandamos un mensaje indicando que empieza la transmisión del archivo
	     
	    	  OutputStream os=socket.getOutputStream();
		      ObjectOutputStream oos= new ObjectOutputStream(os);
		      reqCliente.settRequest(TipoRequest.TRANSFERENCIA);
		      oos.writeObject(reqCliente);  
	      }catch(IOException e) {
	    	  e.printStackTrace(); 
	      }
	      //Empieza la transferencia
	      try
	       {
	         
	         
	    	 //socket.setSoTimeout( 2000 );
	         //socket.setKeepAlive( true );
	      
	         // Creamos el archivo que vamos a enviar
	         File archivo = new File( PATHFILE+reqCliente.getNameResource()  );
	      
	         // Obtenemos el tamaño del archivo
	         int tamañoArchivo = ( int )archivo.length();
	      
	         // Creamos el flujo de salida, este tipo de flujo nos permite 
	         // hacer la escritura de diferentes tipos de datos tales como
	         // Strings, boolean, caracteres y la familia de enteros, etc.
	         DataOutputStream dos = new DataOutputStream( socket.getOutputStream() );
	      
	         System.out.println( "Enviando Archivo: "+archivo.getName() );
	      
	         // Enviamos el nombre del archivo 
	         dos.writeUTF( archivo.getName() );
	      
	         // Enviamos el tamaño del archivo
	         dos.writeInt( tamañoArchivo );
	      
	         // Creamos flujo de entrada para realizar la lectura del archivo en bytes
	         System.out.println(reqCliente.getNameResource());
	         FileInputStream fis = new FileInputStream(PATHFILE+reqCliente.getNameResource()  );
	         BufferedInputStream bis = new BufferedInputStream( fis );
	      
	         // Creamos el flujo de salida para enviar los datos del archivo en bytes
	         BufferedOutputStream bos = new BufferedOutputStream( socket.getOutputStream()          );
	      
	         // Creamos un array de tipo byte con el tamaño del archivo 
	         byte[] buffer = new byte[ tamañoArchivo ];
	      
	         // Leemos el archivo y lo introducimos en el array de bytes 
	         bis.read( buffer ); 
	      
	         // Realizamos el envio de los bytes que conforman el archivo
	         for( int i = 0; i < buffer.length; i++ )
	         {
	             bos.write( buffer[ i ] ); 
	         } 
	      
	           JOptionPane.showMessageDialog(null,"Archivo Enviado: "+archivo.getName() );
	         // Cerramos socket y flujos
	         bis.close();
	         bos.close();
	         socket.close(); 
	       }
	       catch( Exception e )
	       {
	         System.out.println( e.toString() );
	           System.out.println("Archvo enviado");
	       }

		
	}
		  
				
			
}
			
		    
	


