package br.com.fatecjp.telas;

import java.io.IOException;
import java.util.Timer;
import javax.microedition.lcdui.*;

import br.com.fatecjp.core.CountDown;

public final class SplashScreen extends Canvas {
	private static SplashScreen instance;
	private Display display;
	private Displayable next;
	private Timer timer;
	private Image image;
	private int dismissTime;
	
	public SplashScreen(Display display, Displayable next, int dismissTime) throws IOException {
		timer = new Timer();
		
		this.display = display;
		this.next = next;
		this.image = Image.createImage("/images/splash.png");
		this.dismissTime = dismissTime;
		
		display.setCurrent(this);
	}
	
	public static SplashScreen createInstance(Display display, Displayable next, int dismissTime) throws IOException {
		instance = new SplashScreen(display, next, dismissTime);
		return instance;
	}
	
	public static SplashScreen getInstance(Display display, Displayable next, int dismissTime) throws IOException {
		if (instance == null) {
			instance = createInstance(display, next, dismissTime);
		}
		return instance;
	}
	
	public static void access(SplashScreen splashScreen) {
		splashScreen.dismiss();
	}
	private void dismiss() {
		timer.cancel();
		display.setCurrent(next);
	}
	protected void keyPressed(int keyCode) {
		dismiss();
	}
	protected void paint(Graphics g) {
		g.drawImage(image, getWidth() / 2, getHeight() / 2 - 5, 3);
	}
	protected void pointerPressed(int x, int y) {
		dismiss();
	}
	protected void showNotify() {
		if(dismissTime > 0)
			timer.schedule(new CountDown(this), dismissTime);
	}
}