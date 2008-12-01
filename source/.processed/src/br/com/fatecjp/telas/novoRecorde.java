package br.com.fatecjp.telas;

import javax.microedition.lcdui.*;

import br.com.fatecjp.core.AsteroidsCanvas;
import br.com.fatecjp.core.Recorde;


public class novoRecorde extends Form implements CommandListener{
		
	private static novoRecorde instance;
    private static final String copyright =
	"ASTEROIDS(2008)"
	+"\nAdriano Kramer / Daniel Viana"
	+"\nSoftware v1.0.0\n";
    private Image image = null;
    private static String icon = "/imagens/nave-cima.png";
    private int pontos, tempo;
    private TextField txtPlayerName;
    
    private Command cmd_close, cmd_save;
    
	public static novoRecorde createInstance(int pontos, int tempo) {
		instance = new novoRecorde(pontos, tempo);
		return instance;
	}
	
	public static novoRecorde getInstance(int pontos, int tempo) {
		if (instance == null) {
			instance = new novoRecorde(pontos, tempo);
		}
		return instance;
	}
	
    public novoRecorde(int pontos, int tempo) {
    	super("Ateroids 2008 - Novo Recorde");
    	
    	this.pontos = pontos;
    	this.tempo = tempo;
   
    	this.cmd_close = new Command("Fechar", Command.EXIT, 1);
    	this.cmd_save = new Command("Salvar", Command.OK, 1);
    	
	    try {	    	
	        image = Image.createImage(icon);
	        append(image);
	        append(copyright);	        
	    } catch (java.io.IOException x) {}
	    
	    
	    this.addCommand(this.cmd_close);
	    this.addCommand(this.cmd_save);
	    this.setCommandListener(this);
	    
	    this.txtPlayerName = new TextField("Nome do Jogador", "", 50, TextField.ANY);
	    TextField txtPontuacao = new TextField("Pontuação",Integer.toString(pontos),20,TextField.ANY);
	    TextField txtTempo = new TextField("Tempo",Integer.toString(tempo),20,TextField.ANY);
	    
	    append(txtPlayerName);
	    append(txtPontuacao);
	    append(txtTempo);
	    
    }

	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_close)) {
				AsteroidsCanvas.getInstance().setCurrent(Menu.getInstance());
			}
			if (command.equals(cmd_save)) {		
				System.out.println("Salvando recorde...");
				Recorde r = new Recorde(0, txtPlayerName.getString(), pontos, tempo);
                int id  = r.salvarRecorde(); //Grava via Framework Floggy
                if(id==0)
                	System.out.println("Erro no processo!"); 
                else
                	System.out.println("Recorde salvo com êxito!"); 
				AsteroidsCanvas.getInstance().setCurrent(Menu.getInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
