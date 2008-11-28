package br.com.fatecjp.telas;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import br.com.fatecjp.core.UIController;

public class Menu extends List implements CommandListener {
	
	private Command cmd_sair;
	private static Menu instance;

	private Menu() {
		super("Menu", List.IMPLICIT);
		
		this.cmd_sair = new Command("Sair", Command.EXIT, 1);
		append("New Game", null);
		append("Settings", null);
		append("High Scores", null);
		append("About", null);
		append("Exit", null);
		
		this.addCommand(this.cmd_sair);
		this.setCommandListener(this);
	}
	
	public static Menu getInstance() {
		if (instance == null) {
			instance = new Menu();
		}
		return instance;	}

	
	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_sair)) {
				UIController.getInstance().sair();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
