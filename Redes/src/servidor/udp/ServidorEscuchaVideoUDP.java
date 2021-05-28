package servidor.udp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;

//declaramos la clase udp envia
public class ServidorEscuchaVideoUDP extends Thread {
    protected DatagramSocket socket;
    protected DatagramPacket paquete;
    private ImageIcon drawImg;
    private JLabel videoServidor;

    public ServidorEscuchaVideoUDP(int puertoServidor, JLabel webcamServidor) throws SocketException, UnknownHostException {
        socket = new DatagramSocket(puertoServidor);
        videoServidor=webcamServidor;
    }

    public void run() {
        while (true) {
            try {
                byte[] bytes = new byte[64000];

                paquete = new DatagramPacket(bytes,64000);
                socket.receive(paquete);
                String mensaje = new String(paquete.getData(),0,paquete.getLength()).trim();
                if(mensaje.equalsIgnoreCase("-1")){
                    videoServidor.setIcon(null);
                }else{
                    byte[] paqueteBytes=paquete.getData();
                    ByteArrayInputStream bain=new ByteArrayInputStream(paqueteBytes);
                    BufferedImage bIma=ImageIO.read(bain);
                    drawImg= new ImageIcon(bIma);
                    videoServidor.setIcon(drawImg);
                }
            } catch (Exception e) {
                System.out.println("Problema al recibir la imagen");
                e.printStackTrace();
            }
        }
    }
}
