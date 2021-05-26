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

    public ServidorEscuchaVideoUDP(int puertoServidor) throws SocketException, UnknownHostException {
        socket = new DatagramSocket(puertoServidor);
    }

    public void run() {


        try {
            webcam = Webcam.getDefault();
            webcam.setViewSize(new Dimension(176,144));
            webcam.open();
            frame = new JFrame("[WEBCAM SERVER] - Host:" + InetAddress.getLocalHost().getHostAddress() + " - Port:");
            frame.setSize(176,144);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            label = new JLabel();
            label.setSize(176,144);
            label.setVisible(true);

            frame.add(label);
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Problema al momento de abrir la camara");
            e.printStackTrace();
        }
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
                label.setIcon(drawImg);
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
