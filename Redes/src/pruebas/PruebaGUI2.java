package pruebas;

import gui.Gui2;

public class PruebaGUI2 {
    public static void main(String args[]){
        try{
            Gui2 gui2=new Gui2("192.168.100.8",50001,50002, 50005,50006,50003, 50007, 50008);
            gui2.iniciaServidorTCP(50004);
            gui2.setTitle("Cliente 2");
            gui2.setVisible(true);

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
