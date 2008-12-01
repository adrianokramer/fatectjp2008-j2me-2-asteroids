package br.com.fatecjp.telas;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

import br.com.fatecjp.core.AsteroidsCanvas;
import br.com.fatecjp.core.Recorde;

public class highScores extends Form implements CommandListener{
	
	private static highScores instance;
    String nome = "", pontos = "", tempo = "";
    private Command cmd_close;
	
	public static highScores createInstance() {
		instance = new highScores();
		return instance;
	}
	
	public static highScores getInstance() {
		if (instance == null) {
			instance = new highScores();
		}
		return instance;
	}
	
	public highScores(){
		super("Ateroids 2008 - High Scores");
		// Alimentar o Array abaixo com os recordes salvos
		String[] stringElements = null;
		this.cmd_close = new Command("Fechar", Command.EXIT, 1);

		Recorde hscore = new Recorde();
		//quiz.apagarTodos();

		Recorde[] highscores = hscore.listarRecordes();
    
		stringElements = new String[highscores.length];

		for (int cont1 = 0; cont1 < highscores.length; cont1++) {
			System.out.println(highscores[cont1].getNome());
			if (highscores[cont1].getNome().equals("")) {
				nome = "Não há recordes!";
			} else {
				nome = highscores[cont1].getNome();
			}

			stringElements[cont1] = nome;
		}

		ChoiceGroup cgQuiz = new ChoiceGroup("High Scores: ", Choice.POPUP, stringElements, null);
		append(cgQuiz);
		// Carregar nome do jogador
		if(nome.equals("Não há recordes!") || nome.equals(""))
			nome="Não há recordes salvos!";
		else nome = highscores[0].getNome();
    
		TextField txtDescricao = new TextField("Descrição", nome, 200, TextField.UNEDITABLE);
		append(txtDescricao);
    
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
