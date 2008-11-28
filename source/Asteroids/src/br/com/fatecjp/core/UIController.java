package br.com.fatecjp.core;

import java.util.Stack;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import br.com.fatecjp.UIMidlet;
import br.com.fatecjp.telas.Menu;

public class UIController {
	
	private Display disp;
	private UIMidlet midlet;
	
	private Stack telas;
	
	private static UIController instance;
	
	private UIController(UIMidlet midlet) {
		this.midlet = midlet;
		this.disp = Display.getDisplay(midlet);
		this.telas = new Stack();
	}

	public static UIController createInstance(UIMidlet midlet) {
		if (instance == null) {
			instance = new UIController(midlet);
		}
		return instance;
	}
	
	public static UIController getInstance() throws Exception {
		if (instance == null) {
			throw new Exception("Instancia não criada");
		}
		return instance;
	}
	
	public void login(String login, String senha) {
		if (login == null || login.length()<=0) {
			System.out.println("#UIController: falta o login");
		} else if (senha == null || senha.length()<=0) {
			System.out.println("#UIController: falta a senha");
		} else  if (login.equals("admin") && senha.equals("123")) {// ir ao servidor e validar login
			// encaminha para tela de menu
			setCurrent(Menu.getInstance());
		} else {
			System.out.println("#UIController: login e senha inválidos");
		}
	}
	
	public void sair() {
		this.midlet.destroyApp(true);
		this.midlet.notifyDestroyed();
	}
	
	public void setCurrent(Displayable displayable) {
		this.telas.push(displayable);
		this.disp.setCurrent(displayable);
	}



}
