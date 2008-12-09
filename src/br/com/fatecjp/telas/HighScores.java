package br.com.fatecjp.telas;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.*;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

import br.com.fatecjp.core.AsteroidsController;

public class HighScores extends Form implements CommandListener{
	
	private static HighScores instance;
    RecordStore rs;
    SetOnlineRecords setOnlineRecords;
   
    Command cmd_back, cmd_local_high_scores, cmd_online_high_scores, cmd_upload_high_scores;    
	
	public static HighScores createInstance() {
		instance = new HighScores();
		return instance;
	}
	
	public static HighScores getInstance() {
		if (instance == null) {
			instance = createInstance();
		}
		return instance;
	}
	
	public HighScores(){
		super("Ateroids 2008 - High Scores");
		
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
		
		String instructions = "Press menu to select:" +
				"\n\nLocal High Scores : Display local High Scores." +
				"\n\nOnline High Scores: Displays on-line High Scores ." +
				"\n\nUpload High Scores: Upload local High Scores.";				
		
		TextField textField = new TextField("Instructions", instructions, 1024, TextField.UNEDITABLE);
		
		append(textField);
		
		this.cmd_back = new Command("Back", Command.EXIT, 1);
		this.cmd_local_high_scores = new Command("Local High Scores", Command.OK, 1);
		this.cmd_online_high_scores = new Command("Online High Scores", Command.OK, 2);
		this.cmd_upload_high_scores = new Command("Upload High Scores", Command.OK, 3);		
    
		this.addCommand(this.cmd_back);
		this.addCommand(this.cmd_local_high_scores);
		this.addCommand(this.cmd_online_high_scores);
		this.addCommand(this.cmd_upload_high_scores);
		
		this.setCommandListener(this);
	}
	
	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_back)) {
				instance = null;
				AsteroidsController.getInstance().setCurrent(MainMenu.getInstance());
			} 
			else if (command.equals(cmd_local_high_scores)) {
				// Ler os registros locais...
				AsteroidsController.getInstance().setCurrent(LocalHighScores.getInstance());
			}
			else if (command.equals(cmd_online_high_scores)) {
				AsteroidsController.getInstance().setCurrent(OnlineHighScores.getInstance());
			}
			else if (command.equals(cmd_upload_high_scores)) {
				// Ler os registros locais e gravar no servidor...
				try {
					for (int i = 1; i <= rs.getNumRecords(); i++) {
						byte data[] = rs.getRecord(i);
						String record = new String(data);
						String name = record.substring(0, record.indexOf("|"));
						String points = record.substring(record.indexOf("|") + 1, record.indexOf("@"));
						String time = record.substring(record.indexOf("@") + 1, record.length());
						
						setOnlineRecords = new SetOnlineRecords(name, points, time);						
					}					
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
				instance = null;
				AsteroidsController.getInstance().setCurrent(OnlineHighScores.getInstance());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
public class SetOnlineRecords extends Thread {
		
		private Thread runner;
		public String name, points, time;
		
		public SetOnlineRecords(String name, String points, String time) {
			this.name   = name;
			this.points = points;
			this.time   = time;
			
			if(runner == null) {				
				runner = new Thread(this);
				System.out.println("Nova thread!");
				runner.start();
			}
		}
		
		public void run() {
			String url = "http://www.heronmoura.com/midlet/record_incluir.php" +
					     "?nome="   + this.name +
					     "&pontos=" + this.points +
					     "&tempo="  + this.time;
			System.out.println(url);
			
			try {
				setOnlineHighScores(url);
			} catch (IOException e) {
				System.out.println("IOException " + e);
				e.printStackTrace();
			}		
		}
		
		public void setOnlineHighScores(String url) throws IOException {
			HttpConnection connection = null;
			InputStream is = null;
			OutputStream os = null;
			//StringBuffer stringBuffer = new StringBuffer();

			try {
				connection = (HttpConnection)Connector.open(url);
				connection.setRequestMethod(HttpConnection.GET);
				connection.setRequestProperty("IF-Modified-Since","20 Jan 2001 16:19:14 GMT");
				connection.setRequestProperty("User-Agent","Profile/MIDP-2.0 Confirguration/CLDC-1.0");
				connection.setRequestProperty("Content-Language", "en-CA");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				os = connection.openOutputStream();
				is = connection.openDataInputStream();
				/*
				int ch;
				while ((ch = is.read()) != -1) {
					stringBuffer.append((char) ch);
				}
				
				TextField textField = new TextField("Online High Scores", stringBuffer.toString(), 1024, TextField.UNEDITABLE);
				append(textField);
				//append(stringBuffer.toString());
			*/	
			} finally {
				if(is!= null) {
					is.close();
				}
				if(os != null) {
					os.close();
				}
				if(connection != null) {
					connection.close();
				}
				runner = null;
			}
		}		
	}	
}