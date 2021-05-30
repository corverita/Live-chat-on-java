package servidor.udp;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServidorEscuchaAudioUDP extends Thread{
    private DatagramSocket socket;
    private boolean activo;
    private DataLine.Info info;
    private AudioFormat format;
    private SourceDataLine sourceDataLine;
    private DatagramPacket paquete;
    private byte[] datos;
    public ServidorEscuchaAudioUDP(int puerto) throws SocketException {
        socket=new DatagramSocket(puerto);
        activo=true;
        format = new AudioFormat(48000.0f, 16, 2, true, false);
        info = new DataLine.Info(SourceDataLine.class, format);
        datos=new byte[64000];
    }

    @Override
    public void run(){
        try{
            sourceDataLine= (SourceDataLine) AudioSystem.getLine(info);
            sourceDataLine.open(format);
            sourceDataLine.start();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error al abrir obtener los altavoces");
        }
        try{
            paquete=new DatagramPacket(datos,datos.length);
            ByteArrayInputStream bais=new ByteArrayInputStream(datos);
            while(activo){
                socket.receive(paquete);
                String mensaje = new String(paquete.getData(),0,paquete.getLength()).trim();
                if(mensaje.equalsIgnoreCase("-1")){
                    sourceDataLine.drain();
                }else {
                    toSpeaker(paquete.getData());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Problema al recibir el paquete o al reproducir el audio en los altavoces");
        }
    }

    public void toSpeaker(byte soundbytes[]) {
        try
        {
            sourceDataLine.write(soundbytes, 0, soundbytes.length);
        } catch (Exception e) {
            System.out.println("No se puede reproducir en los altavoces.");
            e.printStackTrace();
        }
    }
}
