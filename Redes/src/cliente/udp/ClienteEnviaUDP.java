package cliente.udp;

import java.net.*;

public class ClienteEnviaUDP{
    protected final int PUERTO_SERVER;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket paquete;
    protected final String SERVER;
    
    public ClienteEnviaUDP(String servidor, int puertoServidor) throws SocketException {
        socket = new DatagramSocket();
        SERVER=servidor;
        PUERTO_SERVER=puertoServidor;
    }

    public void enviarMensaje(String mensaje){
        byte[] mensaje_bytes;

        try {
            address=InetAddress.getByName(SERVER);
            mensaje_bytes = mensaje.getBytes();
            paquete = new DatagramPacket(mensaje_bytes,mensaje.length(),address,PUERTO_SERVER);
            socket.send(paquete);
        }
        catch (Exception e) {
            System.err.println("Exception "+e.getMessage());
            System.exit(1);
        }
    }
}
