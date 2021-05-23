//package cliente.udp;
//
//import java.net.*;
//
////declaramos la clase udp
//public class ClienteUDP{
//    protected final int PUERTO_SERVER;
//    protected final String SERVER;
//
//    public ClienteUDP(String servidor, int puertoS){
//        PUERTO_SERVER=puertoS;
//        SERVER=servidor;
//    }
//
//    public void inicia()throws Exception{
//        DatagramSocket socket=new DatagramSocket();
//
//        cliente.udp.ClienteEscuchaUDP clienteEnvUDP=new cliente.udp.ClienteEscuchaUDP(socket);
//        cliente.udp.ClienteEnviaUDP clienteEscUDP=new cliente.udp.ClienteEnviaUDP(socket, SERVER, PUERTO_SERVER);
//
//        clienteEnvUDP.start();
//        clienteEscUDP.start();
//    }
//}
