package pruebas;

import gui.Gui1;
import gui.Gui2;

import java.net.DatagramSocket;

public class pruebaGUI {
    public static void main(String args[]){
        try{
            Gui1 gui=new Gui1("192.168.100.8",50002,50001, 50006,50005,50004, 50008, 50007);
            gui.iniciaServidorTCP(50003);
            gui.setTitle("Cliente 1");
            gui.setVisible(true);

            Gui2 gui2=new Gui2("192.168.100.8",50001,50002, 50005,50006,50003, 50007, 50008);
            gui2.iniciaServidorTCP(50004);
            gui2.setTitle("Cliente 2");
            gui2.setVisible(true);

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
