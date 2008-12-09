package br.com.fatecjp.telas;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.*;

import br.com.fatecjp.core.AsteroidsController;

public class UploadHighScores extends Form implements CommandListener{
	
	private static UploadHighScores instance;
	
    Command cmd_back, cmd_show_online_highscores;
    SetOnlineRecords setOnlineRecords;
	
	public static UploadHighScores createInstance(String name, int points, int time) {
		instance = new UploadHighScores(name, points, time);
		return instance;
	}
	
	public static UploadHighScores getInstance(String name, int points, int time) {
		if (instance == null) {
			instance = createInstance(name, points, time);
		}
		return instance;
	}
	
	public UploadHighScores(String name, int points, int time){
		super("Ateroids 2008 - High Scores");
		
		setOnlineRecords = new SetOnlineRecords(name, points, time);	
		
		setOnlineRecords = null;
		
		this.cmd_back = new Command("Back", Command.EXIT, 1);
		this.cmd_show_online_highscores = new Command("Show HighScores", Command.SCREEN, 1);
		this.addCommand(this.cmd_back);
		this.addCommand(this.cmd_show_online_highscores);
	
		this.setCommandListener(this);
	}
	
	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_back)) {
				instance = null;
				AsteroidsController.getInstance().setCurrent(HighScores.getInstance());
			} else
			if (command.equals(cmd_show_online_highscores)) {
				instance = null;
				AsteroidsController.getInstance().setCurrent(OnlineHighScores.getInstance());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public class SetOnlineRecords extends Thread {
		
		private Thread runner;
		public String name;
		public int points, time;
		
		public SetOnlineRecords(String name, int points, int time) {
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
					     "?name="   + this.name +
					     "&points=" + this.points +
					     "&time="  + this.time;
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
/*				connection.setRequestMethod(HttpConnection.GET);
				connection.setRequestProperty("IF-Modified-Since","20 Jan 2001 16:19:14 GMT");
				connection.setRequestProperty("User-Agent","Profile/MIDP-2.0 Confirguration/CLDC-1.0");
				connection.setRequestProperty("Content-Language", "en-CA");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				os = connection.openOutputStream();
				is = connection.openDataInputStream();
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