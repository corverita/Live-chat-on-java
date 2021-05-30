package pruebas;

import gui.Gui1;

public class PruebaGUI {
    public static void main(String args[]){
        try{
            Gui1 gui=new Gui1("192.168.100.8",50002,50001, 50006,50005,50004, 50008, 50007);
            gui.iniciaServidorTCP(50003);
            gui.setTitle("Cliente 1");
            gui.setVisible(true);

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
