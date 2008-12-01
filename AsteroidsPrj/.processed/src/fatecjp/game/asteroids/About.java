package fatecjp.game.asteroids;

import javax.microedition.lcdui.*;

public class About {

    private static final String copyright =
	"ASTEROIDS(2008)"
	+"\nAdriano Kramer / Daniel Viana"
	+"\nSoftware v1.0.0\n";

    public About() {};

    public static void showAbout(Display display) {

	Alert alert = new Alert("About MIDP");
	alert.setTimeout(Alert.FOREVER);

	if (display.numColors() > 2) {
	    String icon = (display.isColor()) ?
		"/icons/JavaPowered-8.png" : "/icons/JavaPowered-2.png";

	    try {
	        Image image = Image.createImage(icon);
		alert.setImage(image);
	    } catch (java.io.IOException x) {
	    }
	}
	alert.setString(copyright);

	display.setCurrent(alert);
    }

}
