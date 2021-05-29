package pruebas;

import gui.Gui1;
import gui.Gui2;

import java.net.DatagramSocket;

public class pruebaGUI {
    public static void main(String args[]){
        try{
            DatagramSocket socket1=new DatagramSocket();

            Gui1 gui=new Gui1(socket1,"192.168.100.10",50002,50001, 50006,50005,50004, 50008, 50007);
            gui.iniciaServidorTCP(50003);
            gui.setTitle("Cliente 1");
            gui.setVisible(true);

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
