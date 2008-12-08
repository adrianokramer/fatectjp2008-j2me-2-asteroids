package br.com.fatecjp.telas;

import javax.microedition.lcdui.*;

import br.com.fatecjp.core.AsteroidsController;


public class About extends Form implements CommandListener{
		
	private static About instance;
    private static final String copyright =
	" ASTEROIDS (12/2008)"
	+"\n Adriano Kramer / Daniel Viana"
	+"\n Software v1.0.0.0\n";
    
    private static final String contact =
   	"\n Contact: "
    +"\n adrianokramer@gmail.com or"
    +"\n danielvpl@gmail.com";    	
    
    private Image image = null;
    private static String icon = "/imagens/nave-cima.png";
    
    private Command cmd_back;
    
	public static About createInstance() {
		instance = new About();
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
    	
    	this.cmd_back = new Command("Back", Command.EXIT, 1);
    	
	    try {	    	
	        image = Image.createImage(icon);
	        append(image);
	        append(copyright);
	        append(contact);
	    } catch (java.io.IOException x) {}
	    
	    
	    this.addCommand(this.cmd_back);
	    this.setCommandListener(this);
    	
	    
    }
    
	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_back)) {
				instance = null;
				AsteroidsController.getInstance().setCurrent(MainMenu.getInstance());
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
