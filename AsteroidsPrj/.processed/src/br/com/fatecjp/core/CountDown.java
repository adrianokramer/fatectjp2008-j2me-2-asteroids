package br.com.fatecjp.core;

import java.util.TimerTask;

import br.com.fatecjp.telas.SplashScreen;
public class CountDown extends TimerTask {
	private final SplashScreen splashScreen;
	public CountDown(SplashScreen splashScreen) {
		this.splashScreen = splashScreen;
	}
	public void run() {
		SplashScreen.access(this.splashScreen);
	}
}
