package cliente.tcp;
import utilidad.Temporizador;

import javax.swing.*;
import java.net.*;
// importar la libreria java.net
import java.io.*;
import java.util.Date;
import java.util.Timer;
// importar la libreria java.io
 
// declararamos la clase clientetcp
public class ClienteEnviaTCP extends Thread{
    // declaramos un objeto socket para realizar la comunicación
    protected Socket socket;
    protected final int PUERTO_SERVER;
    protected final String SERVER;
    protected DataOutputStream out;
    protected DataInputStream input;
    protected BufferedInputStream bis;
    protected BufferedOutputStream bos;
    protected DataOutputStream dos;
    private byte[] bytes;
    private int in;
    private File archivo;
    private JLabel labelBandwidth;
    private JLabel labelTiempoRestante;
    
    public ClienteEnviaTCP(String servidor, int puertoS, File archivo, JLabel labelBandwidth, JLabel labelTiempoRestante)throws Exception{
        PUERTO_SERVER=puertoS;
        SERVER=servidor;
        this.archivo=archivo;
        this.labelBandwidth=labelBandwidth;
        this.labelTiempoRestante=labelTiempoRestante;
    }

    public void run(){
        try{
            socket = new Socket(SERVER,PUERTO_SERVER);
            bis= new BufferedInputStream(new FileInputStream(archivo));
            dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(archivo.getName());
            dos.flush();

            dos.writeLong(archivo.length());
            dos.flush();

            int segundoAnterior=0;
            int vecesEnviado=0;

            Temporizador temp=new Temporizador();
            Timer timer=new Timer();
            timer.scheduleAtFixedRate(temp,0,1000);

            bytes=new byte[1024*4];
            while ((in = bis.read(bytes)) != -1) {
                dos.write(bytes, 0, in);
                dos.flush();
                vecesEnviado++;
                if(segundoAnterior+1==temp.contadorSegundos){
                    segundoAnterior=temp.contadorSegundos;

                    int bytesSegundo=bytes.length*vecesEnviado;
                    vecesEnviado=0;

                    double bandwidth=((bytesSegundo/1048576.0)*8.0);
                    long mBArchivo=((archivo.length()*8)/(1024*1024));

                    double transmissionTime=mBArchivo/bandwidth;

                    labelBandwidth.setText(bandwidth+"Mbps");
                    labelTiempoRestante.setText(transmissionTime-segundoAnterior+"s");
                }
            }
            System.out.println("Enviado: "+archivo.getName());
            bis.close();
            dos.close();

            labelBandwidth.setText("Enviado");
            labelTiempoRestante.setText("Enviado");

            socket.close();
            timer.cancel();
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
