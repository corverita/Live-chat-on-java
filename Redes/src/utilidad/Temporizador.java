package utilidad;

import java.util.TimerTask;

public class Temporizador extends TimerTask {
    public int contadorSegundos;

    public Temporizador(){
        contadorSegundos=-1;
    }

    public void run(){
        contadorSegundos+=1;
    }
}
