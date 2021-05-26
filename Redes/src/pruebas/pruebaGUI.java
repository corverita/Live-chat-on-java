package pruebas;

import gui.Gui1;

import java.net.DatagramSocket;

public class pruebaGUI {
    public static void main(String args[]){
        try{
            DatagramSocket socket1=new DatagramSocket();

            DatagramSocket socket2=new DatagramSocket();

            Gui1 gui=new Gui1(socket1,"192.168.100.8",50002,50001, 50006,50005);
            gui.iniciaServidorTCP(50003);
            gui.setTitle("Cliente 1");
            gui.setVisible(true);

            Gui1 gui2=new Gui1(socket2,"192.168.100.8",50001,50002,50005,50006);
            gui2.iniciaServidorTCP(50004);
            gui2.setTitle("Cliente 2");
            gui2.setVisible(true);

            gui.iniciaClienteTCP("192.168.100.8",50004);
            gui2.iniciaClienteTCP("192.168.100.8",50003);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
