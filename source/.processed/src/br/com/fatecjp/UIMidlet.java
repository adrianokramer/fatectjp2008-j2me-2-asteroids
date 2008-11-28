package br.com.fatecjp;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import br.com.fatecjp.core.AsteroidsCanvas;
import br.com.fatecjp.telas.Menu;
import br.com.fatecjp.telas.SplashScreen;

public class UIMidlet extends MIDlet {
	
	private AsteroidsCanvas controller;
	
	private boolean started;
	
	private Image splashLogo;
	private boolean isSplash = true;

	public UIMidlet() {
		controller = AsteroidsCanvas.createInstance(this);
	}

	public void destroyApp(boolean flag) {

	}

	protected void pauseApp() {

	}

	protected void startApp() throws MIDletStateChangeException {
		if (!started) {
			started = true;
			//controller.setCurrent(Login.getInstance());
			
			Display display = Display.getDisplay(this);
			
			if(isSplash) {
				isSplash = false;
				try {
					splashLogo = Image.createImage("/images/splash.png");
					new SplashScreen(display, Menu.getInstance(), splashLogo, 4000);
				} catch(Exception ex) {
					controller.setCurrent(Menu.getInstance());
				}
			} else {
				controller.setCurrent(Menu.getInstance());
			}			
			
		} else {	
			// fluxo pós paused
			// pega a ultima tela...
		}
	}

}
