package br.com.fatecjp.telas;

import javax.microedition.lcdui.*;

import br.com.fatecjp.core.AsteroidsCanvas;


public class About extends Form implements CommandListener{
		
	private static About instance;
    private static final String copyright =
	"ASTEROIDS(2008)"
	+"\nAdriano Kramer / Daniel Viana"
	+"\nSoftware v1.0.0\n";
    private Image image = null;
    private static String icon = "/imagens/nave-cima.png";
    
    private Command cmd_close;
    
	public static About createInstance() {
		if (instance == null) {
			instance = new About();
		}
		return instance;
	}
	
	public static About getInstance() {
		if (instance == null) {
			instance = new About();
		}
		return instance;
	}
	
    public About() {
    	super("About Ateroids 2008");
    	
    	this.cmd_close = new Command("Fechar", Command.SCREEN, 1);
    	
	    try {	    	
	        image = Image.createImage(icon);
	        append(image);
	        append(copyright);	        
	    } catch (java.io.IOException x) {}
	    
	    
	    this.addCommand(this.cmd_close);
	    this.setCommandListener(this);
    	
	    
    }

	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_close)) {
				AsteroidsCanvas.getInstance().setCurrent(Menu.getInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
