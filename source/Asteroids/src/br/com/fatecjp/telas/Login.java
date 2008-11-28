package br.com.fatecjp.telas;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

import br.com.fatecjp.core.UIController;

public class Login extends Form implements CommandListener {
	
	private static Login instance;
	
	private TextField login;
	private TextField senha;
	
	private Command cmd_login;
	private Command cmd_sair;

	private Login() {
		super("Login");
		
		this.login = new TextField("login:", "", 10, TextField.ANY);
		this.senha = new TextField("senha:", "", 10, TextField.PASSWORD);
		
		this.cmd_login = new Command("logar", Command.OK, 1);
		this.cmd_sair = new Command("sair", Command.EXIT, 1);
		
		append(this.login);
		append(this.senha);
		
		addCommand(cmd_login);
		addCommand(cmd_sair);
		
		setCommandListener(this);
	}

	public static Login getInstance() {
		if (instance == null) {
			instance = new Login();
		}
		return instance;
	}

	public void commandAction(Command command, Displayable displayable) {
		try {
			if (command.equals(cmd_login)) {
				UIController.getInstance().login(login.getString(), senha.getString());
			} else if (command.equals(cmd_sair)) {
				UIController.getInstance().sair();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
