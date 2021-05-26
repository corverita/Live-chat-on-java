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
                    webcam.setViewSize(new Dimension(640, 480));
                    webcam.open();
                    frame = new JFrame("[WEBCAM SERVER] - Host:" + InetAddress.getLocalHost().getHostAddress() + " - Port:");
                    frame.setSize(640, 480);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    label = new JLabel();
                    label.setSize(640, 480);
                    label.setVisible(true);

                    frame.add(label);
                    frame.setVisible(true);
                } catch (Exception e) {
                    System.out.println("Problema al momento de abrir la camara");
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        byte[] bytes = new byte[MAX_BUFFER];

                        paquete = new DatagramPacket(bytes, MAX_BUFFER);
                        socket.receive(paquete);

                        String paqueteRecibido=new String(paquete.getData(),0,paquete.getLength()).trim();
                        byte[] bytesDecode= Base64.decode(paqueteRecibido);

                        BufferedImage bi=toBufferedImage(bytesDecode);
                        drawImg= new ImageIcon(bi);
                        label.setIcon(drawImg);
                    } catch (Exception e) {
                        System.out.println("Problema al recibir la imagen");
                        e.printStackTrace();
                    }
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
