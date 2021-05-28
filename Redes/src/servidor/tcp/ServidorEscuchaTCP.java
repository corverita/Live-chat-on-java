package servidor.tcp;

import javax.swing.*;
import java.net.*;
 
import java.io.*;
 
public class ServidorEscuchaTCP extends Thread {
    protected ServerSocket socket;
    protected Socket socket_cli;
    protected DataInputStream dis;
    protected BufferedOutputStream bos;
    protected byte[] datos;
    protected int in;
    protected final int PUERTO_SERVER;
    protected JTextArea areaMensajesRecibidos;
    protected JTextArea areaMensajesEnviados;

    
    public ServidorEscuchaTCP(int puertoS, JTextArea areaMensajesRecibidos, JTextArea areaMensajesEnviados)throws Exception{
        PUERTO_SERVER=puertoS;
        socket = new ServerSocket(PUERTO_SERVER);
        this.areaMensajesRecibidos=areaMensajesRecibidos;
        this.areaMensajesEnviados=areaMensajesEnviados;
    }

    public void run(){
        try{
            while(true){
                socket_cli=socket.accept();

                InputStream is = socket_cli.getInputStream();
                dis= new DataInputStream(socket_cli.getInputStream());

                String nombreArchivo=dis.readUTF();

                datos=new byte[1024*8];

                bos=new BufferedOutputStream(new FileOutputStream(nombreArchivo));
                while((in = is.read(datos)) != -1){
                    bos.write(datos,0,in);
                }
                bos.close();
                areaMensajesRecibidos.append("Recibí el archivo: "+ nombreArchivo+"\n");
                areaMensajesEnviados.append("\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
