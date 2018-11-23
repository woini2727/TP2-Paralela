package clientes2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class ThreadRecibirArchivo implements Runnable {
	private Socket socket;
	public ThreadRecibirArchivo(Socket sock) {
		this.socket=sock;
	}
	@Override
	public void run() {
		try {
		// Creamos flujo de entrada para leer los datos que envia el cliente 
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        // Obtenemos el nombre del archivo
        String nombreArchivo = dis.readUTF().toString();
        // Obtenemos el tamaño del archivo
        int tam = dis.readInt();
        System.out.println("Recibiendo Archivo "+nombreArchivo);
        // Creamos flujo de salida, este flujo nos sirve para 
        // indicar donde guardaremos el archivo
        FileOutputStream fos = new FileOutputStream("C:\\ArchivosRecibidos\\"+nombreArchivo);
        BufferedOutputStream out = new BufferedOutputStream(fos);
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
        // Creamos el array de bytes para leer los datos del archivo
        byte[] buffer = new byte[tam];
        // Obtenemos el archivo mediante la lectura de bytes enviados
        for(int i=0;i<buffer.length;i++)
        {
            buffer[i] = (byte)in.read();
        }
        //Escribimos el archivo
        out.write(buffer);
        //Cerramos los flujos
        out.flush();
        in.close();
        out.close();
        socket.close();
        System.out.println("Archivo recivido "+nombreArchivo);
    } catch (Exception e) {
        System.out.println("Recibir "+e.toString());
    }
	
	}
}
