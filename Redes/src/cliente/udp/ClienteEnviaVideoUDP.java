package cliente.udp;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ClienteEnviaVideoUDP extends Thread{
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

	public ClienteEnviaVideoUDP(String servidor, int puertoServidor, JLabel webcamUsuario) throws UnknownHostException {
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
			webcam.setViewSize(new Dimension(320,240));
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

				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				ImageIO.write(bufferedImg,"jpg",baos);
				byte[] imageByte=baos.toByteArray();

				paquete = new DatagramPacket(imageByte,imageByte.length,address,PUERTO_SERVER);
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
}