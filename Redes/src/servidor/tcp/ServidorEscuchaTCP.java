package servidor.tcp;

import java.net.*;
//importar la libreria java.net
 
import java.io.*;
//importar la libreria java.io
// declaramos la clase servidortcp
 
public class ServidorEscuchaTCP extends Thread {
    // declaramos un objeto ServerSocket para realizar la comunicación
    protected ServerSocket socket;
    protected Socket socket_cli;
    protected DataOutputStream output;
    protected DataInputStream dis;
    protected BufferedInputStream bis;
    protected BufferedOutputStream bos;
    protected byte[] datos;
    protected int in;
    protected final int PUERTO_SERVER;

    
    public ServidorEscuchaTCP(int puertoS)throws Exception{
        PUERTO_SERVER=puertoS;
        // Instanciamos un ServerSocket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación

        socket = new ServerSocket(PUERTO_SERVER);
    }

    public void run(){
        try{
            while(true){
                socket_cli=socket.accept();

                InputStream is = socket_cli.getInputStream();
                dis= new DataInputStream(socket_cli.getInputStream());

                String nombreArchivo=dis.readUTF();
                System.out.println(nombreArchivo);
                nombreArchivo=nombreArchivo.substring(nombreArchivo.indexOf('\\')+1,nombreArchivo.length());

                long tamano=dis.readLong();
                datos=new byte[Integer.parseInt(tamano+"")];

                bos=new BufferedOutputStream(new FileOutputStream(nombreArchivo));
                while((in = is.read(datos)) != -1){
                    bos.write(datos,0,in);
                }
                bos.close();
                System.out.println("Terminado de recibir: "+in);
                System.out.println("Terminé de recibir");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
