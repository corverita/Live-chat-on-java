package servidor.udp;

public class PruebaServidorUDP{
    public static void main(String args[]) throws Exception{
        servidor.udp.ServidorUDP servidorUDP=new servidor.udp.ServidorUDP(50000);
        
        servidorUDP.inicia(); 
    }
}
