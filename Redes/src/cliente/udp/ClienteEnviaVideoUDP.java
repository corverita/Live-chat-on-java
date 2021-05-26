package cliente.udp;

import com.github.sarxos.webcam.Webcam;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

//declaramos la clase udp envia
public class ClienteEnviaVideoUDP extends Thread{
    protected BufferedReader in;
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=256;
    protected final int PUERTO_SERVER;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket paquete;
	private Webcam webcam;
	private BufferedImage bufferedImg;
	private ImageIcon drawImg;
    protected final String SERVER;
    
    public ClienteEnviaVideoUDP(String servidor, int puertoServidor) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        SERVER=servidor;
        PUERTO_SERVER=puertoServidor;
		address=InetAddress.getByName(SERVER);
    }

	public void run(){
		try{
			webcam=Webcam.getDefault();
			webcam.setViewSize(new Dimension(640,480));
			webcam.open();
		}catch(Exception e){
			System.out.println("Problema al momento de abrir la camara");
			e.printStackTrace();
		}
		while(true){
			try{
				bufferedImg = webcam.getImage();
				drawImg= new ImageIcon(bufferedImg);
				byte[] bytes=toByteArray(bufferedImg,"png");
				String bytesBase64 = Base64.encode(bytes);

				paquete = new DatagramPacket(bytesBase64.getBytes(StandardCharsets.UTF_8),bytesBase64.length(),address,PUERTO_SERVER);
				socket.send(paquete);
				
			}catch(Exception e){
				System.out.println("Problema al enviar la imagen");
				e.printStackTrace();
			}
		}
	}
    
	public byte[] toByteArray(BufferedImage bi, String format)
        throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }
    
}