package br.com.fatecjp.telas;

import javax.microedition.lcdui.*;

import br.com.fatecjp.core.AsteroidsController;


public class Settings extends Form implements CommandListener{
		
	private static Settings instance;
    private Command cmd_back, cmd_ok;
    
    private Gauge gauge_dificult;
    private ChoiceGroup cg_sound;
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
        try {

            gauge_dificult = new Gauge("Dificult", true, 2, 1);
            cg_sound = new ChoiceGroup(null, Choice.EXCLUSIVE);
            
            append("Sound");
            cg_sound.append("Yes", null);
            cg_sound.append("No", null);
            append(cg_sound);
            
            //Populando o gauge
            gauge_dificult.setValue(AsteroidsController.getInstance().dificult);
            cg_sound.setSelectedIndex(AsteroidsController.getInstance().stop_sound, true);
            
            append(gauge_dificult);
            //append(gauge_speed);
            this.cmd_back = new Command("Back", Command.EXIT, 1);
            this.cmd_ok = new Command("OK", Command.EXIT, 1);

            this.addCommand(this.cmd_back);
            this.addCommand(this.cmd_ok);
            this.setCommandListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_back)) {
				AsteroidsController.getInstance().setCurrent(MainMenu.getInstance());
			} else
			if (command.equals(cmd_ok)) {
				// Gravar dificuldade e velocidade do jogo via RMS/Floggy.
                                AsteroidsController.getInstance().stop_sound = cg_sound.getSelectedIndex();
				AsteroidsController.getInstance().dificult = gauge_dificult.getValue();
				AsteroidsController.getInstance().setCurrent(MainMenu.getInstance());
                                System.out.println("Dificult Selected: "+gauge_dificult.getValue());
                        }				
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
