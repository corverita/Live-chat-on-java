package cliente.udp;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.net.*;

public class ClienteEnviaAudioUDP extends Thread{
    private DatagramSocket socket;
    private InetAddress address;
    private TargetDataLine micro;
    private String ipServer;
    private AudioFormat format;
    private DataLine.Info info;
    private byte[] data;
    private boolean activo;
    private int puertoServer;
    private int dsize;
    private DatagramPacket paquete;


    public ClienteEnviaAudioUDP(String ipServer, int puertoServer) throws SocketException, UnknownHostException {
        socket=new DatagramSocket();
        this.ipServer=ipServer;
        this.puertoServer=puertoServer;
        activo=true;
        address=InetAddress.getByName(ipServer);
    }

    @Override
    public void run() {
        try{
            socket=new DatagramSocket();
            format = new AudioFormat(48000.0f, 16, 2, true, false);
            micro = AudioSystem.getTargetDataLine(format);
            info = new DataLine.Info(TargetDataLine.class, format);

            micro=(TargetDataLine) AudioSystem.getLine(info);
            micro.open(format);
            micro.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        while(activo){
            try{
                data=new byte[64000];
                dsize = micro.read(data,0,data.length);
                paquete = new DatagramPacket(data,data.length,address,puertoServer);
                socket.send(paquete);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void detener(){
        if(activo){
            activo=false;
            micro.close();
            socket.close();
            stop();
        }
    }
}
