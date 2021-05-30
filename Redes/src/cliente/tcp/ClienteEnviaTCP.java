package cliente.tcp;
import utilidad.Temporizador;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Timer;

public class ClienteEnviaTCP extends Thread{
    protected Socket socket;
    protected final int PUERTO_SERVER;
    protected final String SERVER;
    protected BufferedInputStream bis;
    protected DataOutputStream dos;
    private byte[] bytes;
    private int in;
    private File archivo;
    private JLabel labelBandwidth;
    private JLabel labelTiempoRestante;
    private JLabel labelLatencia;
    private JLabel labelTTranscurrido;
    private JTextArea mensajesEnviados;
    
    public ClienteEnviaTCP(String servidor, int puertoS, File archivo, JLabel labelBandwidth, JLabel labelTiempoRestante, JTextArea mensajesEnviados, JLabel labelLatencia, JLabel labelTTranscurrido) throws IOException {
        PUERTO_SERVER=puertoS;
        SERVER=servidor;
        this.archivo=archivo;
        this.labelBandwidth=labelBandwidth;
        this.labelTiempoRestante=labelTiempoRestante;
        this.mensajesEnviados=mensajesEnviados;
        this.labelLatencia=labelLatencia;
        this.labelTTranscurrido=labelTTranscurrido;
        socket = new Socket(SERVER,PUERTO_SERVER);
    }

    public void run(){
        try{
            bis= new BufferedInputStream(new FileInputStream(archivo));
            dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(archivo.getName());
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

                    labelLatencia.setText(transmissionTime+"s");
                    labelTTranscurrido.setText(segundoAnterior+"s");
                    labelBandwidth.setText(bandwidth+"Mbps");
                    labelTiempoRestante.setText(transmissionTime-segundoAnterior+"s");
                }
            }
            labelLatencia.setText("Enviado");
            labelTTranscurrido.setText("Enviado");
            labelBandwidth.setText("Enviado");
            labelTiempoRestante.setText("Enviado");

            String mensajesEnviadosString=mensajesEnviados.getText();
            mensajesEnviadosString+="Enviado: "+archivo.getName()+"\n";
            mensajesEnviados.setText(mensajesEnviadosString);

            bis.close();
            dos.close();

            socket.close();
            timer.cancel();
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
