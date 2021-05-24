package cliente.tcp;
import java.net.*;
// importar la libreria java.net
import java.io.*;
// importar la libreria java.io
 
// declararamos la clase clientetcp
public class ClienteEnviaTCP{
    // declaramos un objeto socket para realizar la comunicación
    protected Socket socket;
    protected final int PUERTO_SERVER;
    protected final String SERVER;
    protected DataOutputStream out;
    protected DataInputStream input;
    protected BufferedInputStream bis;
    protected BufferedOutputStream bos;
    protected DataOutputStream dos;
    byte[] bytes;
    int in;
    
    public ClienteEnviaTCP(String servidor, int puertoS)throws Exception{
        PUERTO_SERVER=puertoS;
        SERVER=servidor;
        
        // Instanciamos un socket con la dirección del destino y el
        // puerto que vamos a utilizar para la comunicación
        socket = new Socket(SERVER,PUERTO_SERVER);
        
        // Declaramos e instanciamos el objeto DataOutputStream
        // que nos valdrá para enviar datos al servidor destino
//        out =new DataOutputStream(socket.getOutputStream());
    }

    public void enviarArchivo(File archivo){
        try{
            bis= new BufferedInputStream(new FileInputStream(archivo));
            dos = new DataOutputStream(socket.getOutputStream());

//            bos = new BufferedOutputStream(socket.getOutputStream());
            dos.writeUTF(archivo.getName());
            dos.flush();

            dos.writeLong(archivo.length());
            dos.flush();

            bytes=new byte[1024*4];
            while ((in = bis.read(bytes)) != -1){
                dos.write(bytes,0,in);
                dos.flush();
            }
            System.out.println("Enviado: "+archivo.getName());
            bis.close();
            dos.close();
//            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
