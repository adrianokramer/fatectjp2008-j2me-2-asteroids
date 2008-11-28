package fatecjp.game.asteroids;

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Display;

public class Asteroids extends MIDlet{

	public Display display;	
	AsteroidsCanvas asteroidsCanvas;

	public Asteroids()
	{
		display = Display.getDisplay(this);
		asteroidsCanvas = new AsteroidsCanvas(this);	
	}

	protected void startApp(){
      		display.setCurrent(asteroidsCanvas);
   	}
	
   	protected void pauseApp(){}
   	protected void destroyApp(boolean unconditional){}

   	public void exitMIDlet(){
      		destroyApp(false);
      		notifyDestroyed();
      
   	}
}
