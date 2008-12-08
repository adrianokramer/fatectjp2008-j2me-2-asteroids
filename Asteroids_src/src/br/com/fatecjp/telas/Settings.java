package br.com.fatecjp.telas;

import javax.microedition.lcdui.*;

import br.com.fatecjp.core.AsteroidsController;


public class Settings extends Form implements CommandListener{
		
	private static Settings instance;
    private Command cmd_back, cmd_ok;
    
    private Gauge gauge_dificult;
    //private Gauge gauge_speed;
    
	public static Settings createInstance() {
		instance = new Settings();
		return instance;
	}
	
	public static Settings getInstance() {
		if (instance == null) {
			instance = createInstance();
		}
		return instance;
	}
	
    public Settings() {
    	super("Ateroids Settings");
    	
    	gauge_dificult = new Gauge("Dificult", true, 3, 1);
    	//gauge_speed = new Gauge("Speed", true, 10, 5);
    	
    	append(gauge_dificult);
    	//append(gauge_speed);
    	
    	this.cmd_back = new Command("Back", Command.EXIT, 1);
    	this.cmd_ok = new Command("OK", Command.EXIT, 1);    	
    	
	    this.addCommand(this.cmd_back);
	    this.addCommand(this.cmd_ok);
	    this.setCommandListener(this);	    
    }
    
	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_back)) {
				AsteroidsController.getInstance().setCurrent(MainMenu.getInstance());
			} else
			if (command.equals(cmd_ok)) {
				// Gravar dificuldade e velocidade do jogo via RMS/Floggy.
				AsteroidsController.getInstance().dificult = gauge_dificult.getValue();
				AsteroidsController.getInstance().setCurrent(MainMenu.getInstance());
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
