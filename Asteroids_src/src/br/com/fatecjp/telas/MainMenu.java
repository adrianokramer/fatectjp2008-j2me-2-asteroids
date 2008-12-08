package br.com.fatecjp.telas;

import javax.microedition.lcdui.*;

import br.com.fatecjp.core.AsteroidsController;

public class MainMenu extends Canvas implements CommandListener {

	private Command cmd_new_game, cmd_settings, cmd_highscore, cmd_about, cmd_menu_exit, cmd_exit;
	private static MainMenu instance;
	private Image img_menu_screen;

	private MainMenu() {
		this.cmd_new_game = new Command("New Game", Command.SCREEN, 1);
		this.cmd_settings = new Command("Settings", Command.SCREEN, 2);		
		this.cmd_highscore = new Command("High Scores", Command.SCREEN, 3);
		this.cmd_about = new Command("About", Command.SCREEN, 4);
		this.cmd_menu_exit = new Command("Exit", Command.SCREEN, 5);
		this.cmd_exit = new Command("Exit", Command.EXIT, 1);		

		try {
			img_menu_screen = Image.createImage("/imagens/splash.png");
		} catch (java.io.IOException x) {
			x.printStackTrace();
		}

		this.addCommand(this.cmd_new_game);
		this.addCommand(this.cmd_settings);
		this.addCommand(this.cmd_highscore);
		this.addCommand(this.cmd_about);
		this.addCommand(this.cmd_menu_exit);
		this.addCommand(this.cmd_exit);
		this.setCommandListener(this);
		repaint();
	}
	
	public static MainMenu getInstance() {
		if (instance == null) {
			instance = new MainMenu();
		}
		return instance;
	}
	
	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_new_game)) {
				//instance = null;
				if(AsteroidsController.getInstance().started != 0)
					AsteroidsController.getInstance().ini = false; //Não inicia o thread
				else{
					AsteroidsController.getInstance().ini = true;
				}
				AsteroidsController.getInstance().startGame(false);
				AsteroidsController.getInstance().pausado= false;
				
				//repaint();
			} else
			if (command.equals(cmd_settings)) {
				AsteroidsController.getInstance().setCurrent(Settings.createInstance());
				repaint();				
			}
			if (command.equals(cmd_highscore)) { //Exibe os recordes
				AsteroidsController.getInstance().setCurrent(HighScores.getInstance());
				repaint();
			} else
			if (command.equals(cmd_about)) {
				AsteroidsController.getInstance().setCurrent(About.createInstance());
				repaint();
			} else
			if (command.equals(cmd_exit) || command.equals(cmd_menu_exit)) {
				instance = null;
				AsteroidsController.getInstance().sair();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	protected void paint(Graphics g) {
		g.drawImage(img_menu_screen, getWidth() / 2, getHeight() / 2 - 5, 3);
		
		g.setColor(0, 0, 0);
		g.fillRect(10, getHeight()/2+25, getWidth()-20, 20);
		
		g.setFont(Font.getFont(Font.FONT_STATIC_TEXT, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
		
		g.setColor(0, 221, 6);
		g.drawString("Press menu to start", getWidth()/2, getHeight()/2+27, Graphics.HCENTER | Graphics.TOP);		
	}
}
