package br.com.fatecjp.telas;

import javax.microedition.lcdui.*;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

import br.com.fatecjp.core.AsteroidsController;

public class LocalHighScores extends Form implements CommandListener{
	
	private static LocalHighScores instance;
    String name = "", points = "", time = "";
    private Command cmd_back;
    RecordStore rs;
	
	public static LocalHighScores createInstance() {
		instance = new LocalHighScores();
		return instance;
	}
	
	public static LocalHighScores getInstance() {
		if (instance == null) {
			instance = createInstance();
		}
		return instance;
	}
	
	public LocalHighScores(){
		super("Ateroids 2008 - Local High Scores");
		
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

		TextField txtList = new TextField("Name | Points | Time \n\n","", 1024, TextField.UNEDITABLE);
		
		try {
			for (int i = 1; i <= rs.getNumRecords(); i++) {
				byte data[] = rs.getRecord(i);
				String record = new String(data);
				String name = record.substring(0, record.indexOf("|"));
				String points = record.substring(record.indexOf("|") + 1, record.indexOf("@"));
				String time = record.substring(record.indexOf("@") + 1, record.length());
				
				txtList.setString(txtList.getString() + "#" + name + " | " + points + " | " + time + "\n");		
			}
			append(txtList);
			
		} catch (RecordStoreNotOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRecordIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
/*		// Alimentar o Array abaixo com os recordes salvos
		String[] stringElements = null;		

		Recorde hscore = new Recorde();
		//quiz.apagarTodos();

		Recorde[] highscores = hscore.listarRecordes();
    
		stringElements = new String[highscores.length];

		for (int cont1 = 0; cont1 < highscores.length; cont1++) {
			System.out.println(highscores[cont1].getNome());
			if (highscores[cont1].getNome().equals("")) {
				name = "Não há recordes!";
			} else {
				name = highscores[cont1].getNome();
			}

			stringElements[cont1] = name;
		}

		ChoiceGroup cgQuiz = new ChoiceGroup("High Scores: ", Choice.POPUP, stringElements, null);
		append(cgQuiz);
		// Carregar name do jogador
		if(name.equals("Não há recordes!") || name.equals(""))
			name="Não há recordes salvos!";
		else name = highscores[0].getNome();
    
		TextField txtDescricao = new TextField("Descrição", name, 200, TextField.UNEDITABLE);
		append(txtDescricao);*/
		
		this.cmd_back = new Command("Back", Command.EXIT, 1);
		
		this.addCommand(this.cmd_back);
		this.setCommandListener(this);
	}
	
	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_back)) {
				instance = null;
				AsteroidsController.getInstance().setCurrent(HighScores.getInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
}