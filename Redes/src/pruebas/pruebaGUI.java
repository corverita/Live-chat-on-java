package pruebas;

import gui.Gui;

import java.net.DatagramSocket;

public class pruebaGUI {
    public static void main(String args[]){
        try{
            DatagramSocket socket=new DatagramSocket();

            Gui gui=new Gui(socket,"192.168.100.8",50000);
            gui.setVisible(true);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
