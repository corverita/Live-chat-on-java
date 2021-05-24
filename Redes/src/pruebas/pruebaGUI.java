package pruebas;

import gui.Gui1;
import gui.Gui2;

import java.net.DatagramSocket;

public class pruebaGUI {
    public static void main(String args[]){
        try{
            DatagramSocket socket=new DatagramSocket();

            Gui1 gui=new Gui1(socket,"192.168.100.8",50001);
            gui.setTitle("Cliente 1");
            gui.setVisible(true);

            Gui2 gui2=new Gui2(socket,"192.168.100.8",50000);
            gui2.setVisible(true);
            gui2.setTitle("Cliente 2");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
