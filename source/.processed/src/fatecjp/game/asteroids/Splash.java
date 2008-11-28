package fatecjp.game.asteroids;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

public class Splash{

	   //private static final String txt_start = "<<Iniciar>>";
	   public MIDlet md;   
	
	   public Splash() 
	   {}

		    public static void showSplash(Display display){
		    
			Alert alert = new Alert("Asteroids 2008");
			//alert.setTimeout(Alert.FOREVER);
			alert.setTimeout(1); //Fechar

			if (display.numColors() > 2) {
			    String icon = "/imagens/splash240x280px.png";

			    try {
			        Image image = Image.createImage(icon);
			        alert.setImage(image);
			    } catch (java.io.IOException x) {}
			}
			//alert.setString(txt_start);

			display.setCurrent(alert);
		
	}
}
