package servidor.udp;

import javax.swing.*;
import java.net.*;

public class ServidorEscuchaUDP extends Thread{
    protected DatagramSocket socket;
    protected final int PUERTO_SERVER;
    protected int puertoCliente=0;
    
    protected InetAddress addressCliente;
    protected final int MAX_BUFFER=256;
    protected DatagramPacket paquete;
    protected byte[] mensaje_bytes;
    protected JTextArea areaMensajes, areaMensajesEnviados;
    
    public ServidorEscuchaUDP(int puertoS, JTextArea areaDeMensajes, JTextArea areaDeMensajesEnviados) throws Exception{
        PUERTO_SERVER=puertoS;
        socket = new DatagramSocket(puertoS);
        areaMensajes=areaDeMensajes;
        areaMensajesEnviados=areaDeMensajesEnviados;
    }
    public void run() {
        try {
            
            String mensaje ="";
                       
            //Iniciamos el bucle
            while(true) {
                mensaje_bytes=new byte[MAX_BUFFER];
                paquete = new DatagramPacket(mensaje_bytes,MAX_BUFFER);
                socket.receive(paquete);

                // Lo formateamos
                mensaje_bytes=new byte[paquete.getLength()];
                mensaje_bytes=paquete.getData();
                mensaje = new String(mensaje_bytes,0,paquete.getLength()).trim();

                // Lo mostramos por pantalla
                String mensajesRecibidos=areaMensajes.getText();
                mensajesRecibidos+=paquete.getAddress()+":"+paquete.getPort()+"dice: "+mensaje+"\n";
                areaMensajes.setText(mensajesRecibidos);

                String mensajesEnviados=areaMensajesEnviados.getText();
                mensajesEnviados+="\n";
                areaMensajesEnviados.setText(mensajesEnviados);

                //Obtenemos IP Y PUERTO
                puertoCliente = paquete.getPort();
                addressCliente = paquete.getAddress();
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
