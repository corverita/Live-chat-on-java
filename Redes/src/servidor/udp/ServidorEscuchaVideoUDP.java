package servidor.udp;

import com.github.sarxos.webcam.Webcam;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.InflaterOutputStream;

import static com.github.sarxos.webcam.util.ImageUtils.toByteArray;

//declaramos la clase udp envia
public class ServidorEscuchaVideoUDP extends Thread {
    protected BufferedReader in;
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER = 256;
    protected DatagramSocket socket;
    protected DatagramPacket paquete;
    private Webcam webcam;
    private BufferedImage bufferedImg;
    private ImageIcon drawImg;
    private JFrame frame;
    private JLabel label;
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

                byte[] descomprimir=decompress(paquete.getData());
                descomprimir=decompress(descomprimir);

                InputStream is=new ByteArrayInputStream(descomprimir);
                System.out.println(descomprimir.length);
                BufferedImage bi=ImageIO.read(is);
                System.out.println(bi.getHeight()+ " "+bi.getWidth());
                drawImg= new ImageIcon(bi);
                System.out.println(drawImg);
                videoServidor.setIcon(drawImg);
            } catch (Exception e) {
                System.out.println("Problema al recibir la imagen");
                e.printStackTrace();
            }
        }
    }

    public static byte[] decompress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InflaterOutputStream infl = new InflaterOutputStream(out);
            infl.write(in);
            infl.flush();
            infl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(150);
            return null;
        }
    }

    public BufferedImage toBufferedImage ( byte[] bytes) throws IOException {

        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi;

    }

}
