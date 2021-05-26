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
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

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
	private JLabel videoUsuario;
	private boolean activo;
	protected final String SERVER;

	public ClienteEnviaVideoUDP(String servidor, int puertoServidor, JLabel webcamUsuario) throws UnknownHostException, SocketException {
		SERVER=servidor;
		PUERTO_SERVER=puertoServidor;
		address=InetAddress.getByName(SERVER);
		videoUsuario=webcamUsuario;
		activo=true;
	}

	public void run(){
		try{
			socket=new DatagramSocket();
			webcam=Webcam.getDefault();
			webcam.setViewSize(new Dimension(176,144));
			webcam.open();
		}catch(Exception e){
			System.out.println("Problema al momento de abrir la camara");
			e.printStackTrace();
		}
		while(activo){
			try{
				bufferedImg = webcam.getImage();
				drawImg= new ImageIcon(bufferedImg);
				videoUsuario.setIcon(drawImg);
				byte[] bytes=toByteArray(bufferedImg,"png");

				byte[] comprimido=compress(bytes);
				comprimido=compress(comprimido);
				comprimido=compress(comprimido);

				paquete = new DatagramPacket(comprimido,comprimido.length,address,PUERTO_SERVER);
				socket.send(paquete);

			}catch(Exception e){
				System.out.println("Problema al enviar la imagen");
				e.printStackTrace();
			}
		}
	}

	public void detener() throws IOException {
		if(activo) {
			activo = false;
			webcam.close();
			String valorSalida = "-1";
			byte[] bytes = valorSalida.getBytes(StandardCharsets.UTF_8);
			DatagramPacket paqueteSalida = new DatagramPacket(bytes, bytes.length, address, PUERTO_SERVER);
			socket.send(paqueteSalida);
			videoUsuario.setIcon(null);
			socket.close();
			stop();
		}
	}

	public static byte[] compress(byte[] in) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DeflaterOutputStream defl = new DeflaterOutputStream(out);
			defl.write(in);
			defl.flush();
			defl.close();
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(150);
			return null;
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