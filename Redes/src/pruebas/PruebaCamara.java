package pruebas;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PruebaCamara {
    public static void main(String args[]) throws IOException {
        System.out.println(Webcam.getWebcams());
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        BufferedImage image = webcam.getImage();
        ImageIO.write(image, "JPG", new File("test.jpg"));
    }
}
