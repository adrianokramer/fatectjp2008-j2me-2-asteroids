package br.com.fatecjp.telas;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;

import br.com.fatecjp.core.AsteroidsCanvas;

public class Menu extends Form implements CommandListener {
	
	private Command cmd_play, cmd_about, cmd_exit, cmd_highscore;
	private static Menu instance;
	
	private Menu() {
		super("Asteroids 2008");
		
		this.cmd_play = new Command("Start", Command.SCREEN, 1);
		this.cmd_highscore = new Command("High Scores", Command.SCREEN, 1);
		this.cmd_about = new Command("About", Command.SCREEN, 1);
		this.cmd_exit = new Command("Exit", Command.EXIT, 1);
		
		Image image = null;
    	String icon = "/imagens/splash240x300px.png";
    	
		    try {
		        image = Image.createImage(icon);
		    } catch (java.io.IOException x) {}
		
		/*
		append("New Game", null);
		append("Settings", null);
		append("High Scores", null);
		append("About", null);
		append("Exit", null);
		*/
		append(image);		
		
		this.addCommand(this.cmd_play);
		this.addCommand(this.cmd_highscore);
		this.addCommand(this.cmd_about);
		this.addCommand(this.cmd_exit);
		this.setCommandListener(this);
	}
	
	public static Menu getInstance() {
		if (instance == null) {
			instance = new Menu();
		}
		return instance;	
	}

	
	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_play)) {
				if(AsteroidsCanvas.getInstance().iniciar != 0)
					AsteroidsCanvas.getInstance().ini = false; //Não inicia o thread
				else
					AsteroidsCanvas.getInstance().ini = true;
				AsteroidsCanvas.getInstance().iniciaJogo(false);
			}
			if (command.equals(cmd_highscore)) { //Exibe os recordes
				AsteroidsCanvas.getInstance().setCurrent(highScores.getInstance());
			}
			if (command.equals(cmd_about)) {
				AsteroidsCanvas.getInstance().setCurrent(About.createInstance());
			}
			if (command.equals(cmd_exit)) {
				AsteroidsCanvas.getInstance().sair();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
