package br.com.fatecjp.telas;

import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

import br.com.fatecjp.core.AsteroidsController;


public class SaveLocalHighScores extends Form implements CommandListener{
		
	private static SaveLocalHighScores instance;
    private static final String copyright =
	"ASTEROIDS(2008)"
	+"\nAdriano Kramer / Daniel Viana"
	+"\nSoftware v1.0.0.0\n";
    private Image image = null;
    private static String icon = "/imagens/nave-cima.png";
    private int points, time;
    private TextField txtPlayerName;
    
    private Command cmd_back, cmd_save;
    
	RecordStore rs;
    
	public static SaveLocalHighScores createInstance(int points, int time) {
		instance = new SaveLocalHighScores(points, time);
		return instance;
	}
	
	public static SaveLocalHighScores getInstance(int points, int time) {
		if (instance == null) {
			instance = createInstance(points, time);
		}
		return instance;
	}
	
    public SaveLocalHighScores(int points, int time) {
    	super("Ateroids 2008 - Novo Recorde");
    	
		try {
			rs = RecordStore.openRecordStore("asteroids", true);
		} catch (RecordStoreFullException e) {
			System.out.println("DB Error.");
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			System.out.println("DB is FULL!");
			e.printStackTrace();
		} catch (RecordStoreException e) {
			System.out.println("DB Not Found!");
			e.printStackTrace();
		}    	
    	
    	this.points = points;
    	this.time = time;
   
    	this.cmd_back = new Command("Back", Command.EXIT, 1);
    	this.cmd_save = new Command("Save", Command.OK, 1);
    	
	    try {	    	
	        image = Image.createImage(icon);
	        append(image);
	        append(copyright);	        
	    } catch (java.io.IOException x) {}
	    
	    
	    this.addCommand(this.cmd_back);
	    this.addCommand(this.cmd_save);
	    this.setCommandListener(this);
	    
	    this.txtPlayerName = new TextField("Player Name", "", 30, TextField.ANY);
	    TextField txtPontuacao = new TextField("Points",Integer.toString(points),20,TextField.UNEDITABLE);
	    TextField txttime = new TextField("Time",Integer.toString(time),20,TextField.UNEDITABLE);
	    
	    append(txtPlayerName);
	    append(txtPontuacao);
	    append(txttime);
	    
    }

	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_back)) {
				AsteroidsController.getInstance().setCurrent(MainMenu.getInstance());
			}
			if (command.equals(cmd_save)) {		
				System.out.println("Salvando recorde...");
				try {
					String record = txtPlayerName.getString() + "|" + points + "@" + time;
					System.out.println(record);
					byte data[] = record.getBytes();
					System.out.println(data);
					rs.addRecord(data, 0, data.length);
					System.out.println("Registro Gravado com sucesso.");
				} catch (Exception e) {
					System.out.println("Erro ao Gravar Registro.");
				}
				AsteroidsController.getInstance().setCurrent(MainMenu.getInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
