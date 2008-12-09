package br.com.fatecjp;

import java.io.IOException;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import br.com.fatecjp.core.AsteroidsController;
import br.com.fatecjp.telas.MainMenu;
import br.com.fatecjp.telas.SplashScreen;

public class AsteroidsUIMidlet extends MIDlet {

	private AsteroidsController controller;

	private boolean started;
	private boolean isSplash = true;
	Display display;

	public AsteroidsUIMidlet() {
		controller = AsteroidsController.createInstance(this);
	}

	public void destroyApp(boolean flag) {
		display.setCurrent(null);
		this.notifyDestroyed(); 
	}

	protected void pauseApp() {
		this.notifyPaused();
	}

	protected void startApp() throws MIDletStateChangeException {
		if (!started) {
			started = true;
			display = Display.getDisplay(this);			

			if(isSplash) {
				isSplash = false;

				try {
					controller.setCurrent(SplashScreen.getInstance(display, MainMenu.getInstance(), 3000));
				} catch (IOException e) {
					e.printStackTrace();
					controller.setCurrent(MainMenu.getInstance());
				}

			} else {
				try {
					controller.setCurrent(MainMenu.getInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	

		} else {	
			// fluxo pós paused
			// pega a ultima tela...
		}
	}

}
