package br.com.fatecjp.core;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;

import br.com.fatecjp.UIMidlet;
import br.com.fatecjp.telas.Menu;

import java.io.IOException;
import java.util.Stack;


public class AsteroidsCanvas extends GameCanvas implements Runnable, CommandListener
{
	private ObjVoador naveJogador;
	private ObjVoador ovniInimigo[];
	
	public MIDlet midlet;
	public Display dpy;
	
	private final static int 	col1 = 30,
					col2 = 110,
					col3 = 180;

	private final static int 	MARGEM_DIREITA = 200,
					MARGEM_ESQUERDA = 15;

	/* mapa = numero de ovnis - (tipo, px, py)) */;
	private int mapa1[]={50,1,col1,60,1,col3,60,1,col2,-40,1,col3,-140,4,col1,-200,
				2,col2,-270,2,col2,-500,1,col3, -580, 2,col1,-630, 4,col2,-730,
				2,col1,-780,3,col1,-850,2,col1,-930,3,col2,-990,4,col1,-1100,
				1,col3,-1200,2,col2,-1300,4,col1,-1400,1,col2,-1490,2,col3,-1600,
				3,col1,-1700,3,col3,-1800,2,col2,-1890,1,col1,-1960,4,col3,-2120,
				1,col2,-2200,2,col1,-2270,2,col3,-2350,4,col1,-2450,1,col2,-2500,
				4,col1,-2650,4,col3,-2650,3,col1,-2750,2,col2,-2830,1,col1,-2900,
				3,col1,-3100,3,col3,-3300,3,col1,-3500,1,col3,-3600,2,col2,-3670,
				1,col2,-3750,4,col3,-3770,3,col1,-3870,2,col3,-3950,1,col2,-4070,
				1,col1,-4140,3,col3,-4220,2,col2,-4300,3,col3,-4390,3,col2,-4500,
				1,col3,2,col2,-4500};
	private int mapa[];

	private final static int linhas[]={200,100,50,0,-50,-100,-200};

	private int limitepy = 240;
	private int tempo, pontuacao = 0;

	private Image	nave1, nave2, nave3,
			explosao1,explosao2,explosao3,explosao4,explosao5,explosao6,
			asteroid1,asteroid2,asteroid3,
			cometa1, cometa2,			
			limite,
			back;
	
	private Image explosao[] = new Image[6]; //Animação da explosão da nave
	private Image cometa[] = new Image[3]; //Animação do cometa

	private int 	numOvnis;

	private final static int 	sleepTime = 30;
	//private final static int bordaEsquerda = 36, bordaDireita = 200;

	private int iniciar=0; 

	private int count;
	private boolean pausado;
    
	//Comandos
	public static final int CMD_RESET	= 1;
	public static final int CMD_MENU	= 2;
	public static final int CMD_EXIT	= 3;
	public static final int CMD_ZLAST	= 4;
	
	public Command cmd[];
	
	private Stack telas;
	
	private static AsteroidsCanvas instance;
	
	
	public class BoardCommand extends Command {
		int tag;
		BoardCommand(String label, int type, int pri, int tag_) {
			super(label, type, pri);
			tag = tag_;
		}
	}
	
	void setState() {
		if(iniciar==4){//Diferencia jogo em andamento		
			addCommand(cmd[CMD_RESET]);
			addCommand(cmd[CMD_MENU]);
			addCommand(cmd[CMD_EXIT]);
		}
		else{//Jogo parado
			addCommand(cmd[CMD_RESET]);
			addCommand(cmd[CMD_MENU]);
			addCommand(cmd[CMD_EXIT]);
		}
		
	}
	
	public static AsteroidsCanvas createInstance(UIMidlet midlet) {
		if (instance == null) {
			instance = new AsteroidsCanvas(midlet);
		}
		return instance;
	}
	
	public static AsteroidsCanvas getInstance() throws Exception {
		if (instance == null) {
			throw new Exception("Instancia não criada");
		}
		return instance;
	}
	
	public AsteroidsCanvas(MIDlet md){
		/*GameCanvas(boolean suppressKeyEvents)*/
		super(true);	

		this.midlet = md;		
		this.dpy = Display.getDisplay(md);	
		this.telas = new Stack();
		//Instanciando objeto auxiliar
		
		cmd = new Command[CMD_ZLAST];
		cmd[CMD_MENU] = new BoardCommand("Main Menu", Command.SCREEN, 1, CMD_MENU);		
		cmd[CMD_RESET] = new BoardCommand("Restart", Command.SCREEN, 1, CMD_RESET);
		cmd[CMD_EXIT] = new BoardCommand("Sair", Command.EXIT, 7, CMD_EXIT);
		
		setCommandListener(this);				
		
		//Dados do jogo
		carregaImagens();
		
		//iniciaJogo(true); //Primeira inicialização - Título
	}
	
	public void iniciaJogo(boolean hard_reset)
	{
		setMapa(mapa1);
		carregaMapa();
		
		naveJogador = new ObjVoador(nave1, 0, col2, 200, 30, 6);

		pausado = false;
		count=0;
		iniciar=4;
		tempo = 0;
		pontuacao = 0;
		
		setState();
		
		//Inicializa na tela inicial
		if(hard_reset==true){
			pausado = true;
		}
		//Inicia thread e muda o display
		new Thread(this).start();
		setCurrent(this);
	}
	
	public void commandAction(Command c, Displayable d) {
		switch (((BoardCommand) c).tag) {		    
			
			case CMD_EXIT:
		    	midlet.notifyDestroyed();
			break;
		    
		   case CMD_RESET:
		    	iniciaJogo(false);
			break;

		    case CMD_MENU:	  
		    	setCurrent(Menu.getInstance());
		    	//pausado = true;
		    break;
		    
		}
	}

	private void setMapa(int m[])
	{
		mapa = m;
	}
	private void carregaImagens()
	{
		try
		{
			nave1 = Image.createImage("/imagens/nave-cima.png");
			nave2 = Image.createImage("/imagens/nave-direita.png");
			nave3 = Image.createImage("/imagens/nave-esquerda.png");
			asteroid1 = Image.createImage("/imagens/asteroid-2.png");
			asteroid2 = Image.createImage("/imagens/asteroid-3.png");
			asteroid3 = Image.createImage("/imagens/asteroid-4.png");
			limite = Image.createImage("/imagens/limite.png");
			cometa1 = Image.createImage("/imagens/cometa.png");
			cometa2 = Image.createImage("/imagens/cometa.png");
			back = Image.createImage("/imagens/universo240x300px.png");
			//Explosão nave
			
		}
		catch(IOException e)
		{
			System.out.println("Erro ao carregar Imagens");
			e.printStackTrace();
		}
	}

	private void carregaMapa()
	{
		Image img=null;
		int x,y;
		numOvnis = mapa[0];
		ovniInimigo = new ObjVoador[numOvnis];
		for(y=0, x=1; y<numOvnis; x+=3, y++)
		{
			
			if(mapa[x] == 1)
				img = asteroid1;
			else if(mapa[x] == 2)
				img = asteroid2;
			else if(mapa[x] == 3)
				img = asteroid3;
			else if(mapa[x] == 4)
				img = cometa1;		

			ovniInimigo[y] = new ObjVoador(img, mapa[x], mapa[x+1], mapa[x+2], -10 ,3);
			ovniInimigo[y].setVelocidade(-5);
		}
	}

	private void desenhaOvnis(Graphics g)
	{
		int x;
		naveJogador.meDesenha(g);

		for(x=0; x<numOvnis; x++)
		{
			if(ovniInimigo[x].getPy() +ovniInimigo[x].getAltura() > 0)
				ovniInimigo[x].meDesenha(g);
		}
	}

	private void andaOvnis()
	{

		int x;
		int px_ant;

		for(x=0; x<numOvnis; x++)
		{


			/***********************
		 	* Anda nave para fente
		 	***********************/
			ovniInimigo[x].incVelocidade(1);
			px_ant = naveJogador.getPx();
			ovniInimigo[x].setPy(ovniInimigo[x].getPy() + ((naveJogador.getVelocidade() - ovniInimigo[x].getVelocidade())/5));
			
			if(colisaoOvnis())
			{
				//for ()
				naveJogador.setPx(px_ant-20);
				
				naveJogador.setVelocidade(-10);
				naveJogador.incEnergia(-1);
			}
			
		}
				
	}

	private boolean colisaoOvnis()
	{
		int 	j_px = naveJogador.getPx(), 
			j_py = naveJogador.getPy(),
			j_c = naveJogador.getComprimento(), 
			j_a = naveJogador.getAltura();

		int 	i_px,
			i_py,
			i_c,
			i_a;
		

		for(int x=0;x<numOvnis;x++)
		{
			i_px = ovniInimigo[x].getPx();
			i_py = ovniInimigo[x].getPy();
			i_c = ovniInimigo[x].getComprimento();
			i_a = ovniInimigo[x].getAltura();

			if(!(i_py > -100 && i_py < 280))
				continue; 


			if((j_px <= i_px && j_px+j_c > i_px) || (j_px < i_px+i_c && j_px+j_c >= i_px+i_c) ||
				(j_px >= i_px && j_px+j_c <= i_px+i_c) || (i_px >= j_px && i_px+i_c <= j_px+j_c))
			{
				if((j_py < i_py+i_a && j_py > i_py) || (j_py+j_a > i_py && j_py+j_a < i_py+i_a) || 
					(j_py >= i_py && j_py+j_a <= i_py+i_a) || (i_py >= j_py && i_py+i_a <= j_py+j_a))
					return true;

			}

		}
		return false;
			
	}


	private void processaIniciar()
	{
		if(count == sleepTime && iniciar != 4){
			iniciar++;		    
		}
	}
			

	private int getPosicao()
	{
		//int ovnis = mapa[0];
		int p=0;
		for(int x=0; x<numOvnis; x++)
		{
			if(naveJogador.getPy() < ovniInimigo[x].getPy())
				p++;
		}
		return numOvnis-(p);	
	}

	private void andaUniverso()
	{
		int vel = naveJogador.getVelocidade();
		for(int x=0; x<linhas.length;x++)
		{
			if(vel > 5)
				linhas[x]+=vel/5;
			else if(vel != 0)
				linhas[x]++;

			if(linhas[x] > 180 )
				linhas[x]-=300;
		}			
	}

	private void andaLimite()
	{
		int vel = naveJogador.getVelocidade();

			if(vel > 0)
				limitepy+=vel;
			else if(vel != 0)
				limitepy++;
			else 
				limitepy++; //Default
			
			if(limitepy > 210 )
				limitepy-=210;
	}
		
	private void desenhaLimites(Graphics g)
	{
			g.drawImage(limite, 5, limitepy, Graphics.TOP | Graphics.LEFT);
			g.drawImage(limite, 225, limitepy, Graphics.TOP | Graphics.LEFT);
	
	}

	private void desenhaInterface(Graphics g)
	{		
		g.setColor(0,0,0);
		//g.fillRect(0,260,50,10);

		g.setColor(255,255,255);
		int velocidade = naveJogador.getVelocidade(); //Incrementado a amostragem da velocidade
		if(velocidade < 0)velocidade = 0; //Trata velocidade negativa
		g.drawString("Pontos: "+this.pontuacao+" Tempo: "+this.tempo+" s", 50,260, Graphics.TOP | Graphics.LEFT);
		g.drawString("Ovnis: "+ getPosicao()+"  Ener: "+naveJogador.getEnergia()+" Vel: " + velocidade*40 +" Km/h",50,270, Graphics.TOP | Graphics.LEFT);
		
		//RGB
		/*
		int flag1 = 0x960000; 
		int flag2 = 0x960000;
		int flag3 = 0x009600; 	
		*/
		int flag1 = 0x000000, flag2 = 0x000000, flag3 = 0x000000;
		
		if(iniciar  > 0)
			//flag1 = 0xFF0000;
			flag1 = 0xffffff;
		if(iniciar > 1)
			//flag2 = 0xFF0000;
			flag2 = 0xffffff;
		if(iniciar > 2)
			//flag3 = 0x00FF00;
			flag3 = 0xffffff;
		
		//g.fillArc(2,270,8,8,0,360); //desenha círculo
		
		g.setColor(flag1);
		g.drawString("3", 10, 272, 0);	
		
		g.setColor(flag2);
		g.drawString("2", 20, 272, 0);
	
		g.setColor(flag3);
		g.drawString("1", 30, 272, 0);	
				
		//Apagando números
		if(iniciar==4){
		g.setColor(0x000000); 
		g.drawString("1", 30, 272, 0);	
		g.drawString("2", 20, 272, 0);
		g.drawString("3", 10, 272, 0);
		}
	
	}
		

	public void paint(Graphics g)
	{	
		if(!pausado)
		{
			g.drawImage(back,0,0, Graphics.TOP | Graphics.LEFT);
			desenhaLimites(g);
			desenhaOvnis(g);
			desenhaInterface(g);
		}

		if(getPosicao() <= 0)
		{
			pausado = true;
			iniciar=0;
			g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
			g.setColor(255,0,0);
			g.drawString("VOCÊ VENCEU!", 73,70, Graphics.TOP | Graphics.LEFT);
			g.drawString("PRESSIONE MENU", 60,90, Graphics.TOP | Graphics.LEFT);
			//Grava recorde (RMS - Webservice)
			
		}

		if(naveJogador.getEnergia() <= 0)
		{
			pausado = true;
			iniciar=0;
			g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
			g.setColor(255,0,0);
			g.drawString("GAME OVER", 77,70, Graphics.TOP | Graphics.LEFT);
			g.drawString("PRESSIONE MENU", 60,90, Graphics.TOP | Graphics.LEFT);
			//Grava recorde (RMS - Webservice)
		}

	}

	public void run()
	{
		try
		{
			while(true)
			{
				Thread.sleep(sleepTime);
				
				if(iniciar == 4)
				{
					processaTeclas();
					setState();

					if(!pausado)
					{
						andaUniverso();
						andaLimite();
						andaOvnis();
					}

				}
				else
					processaIniciar();

				repaint();

				if(count<sleepTime){
					count++;
				}
				else{
					count=0;
					 
					//Incrementando tempo e pontuação
					if(iniciar==4)
						tempo++;
					//Cáculo da pontuação (80*velocidade + 20*energia)
					if(naveJogador.getVelocidade()>0)
						pontuacao += 80*(naveJogador.getVelocidade()) - 20*(this.naveJogador.getEnergia());
					if(pontuacao<0)
						pontuacao=0;
				}
			}
		}
		catch(Exception e){}
	}

	private void processaTeclas()
	{
		int keyStates = getKeyStates();


		if((keyStates & FIRE_PRESSED) != 0)
		{
			if(pausado)
			{
				pausado = false;
				iniciaJogo(false);
			}
		}

		if((keyStates & UP_PRESSED)!=0)
			naveJogador.incVelocidade(1); 
		/*else //Desaceleração da nave (Não ocorre, vácuo)
		{
			if(naveJogador.getVelocidade() >= 1)
			{
				if(loopCounter % 3 == 0)
					naveJogador.incVelocidade(-1);
			}
		}
		*/
		
		if((keyStates & DOWN_PRESSED)!=0)
		{
			if(naveJogador.getVelocidade() >= 3)
				naveJogador.incVelocidade(-1);
		}

		if((keyStates & LEFT_PRESSED)!=0)
		{
			naveJogador.image = nave3;
			int px_ant = naveJogador.getPx();

			if(px_ant - 3 >= MARGEM_ESQUERDA)
			{
				naveJogador.setPx(px_ant-3);

				if(colisaoOvnis())
				{
					naveJogador.setPx(px_ant+10);
					naveJogador.setVelocidade(0);
					naveJogador.incEnergia(-1);
				}
			}
		}

		if((keyStates & RIGHT_PRESSED)!=0)
		{
			naveJogador.image = nave2;
			int px_ant = naveJogador.getPx();
			
			if(px_ant + 3 <= MARGEM_DIREITA)
			{
				naveJogador.setPx(px_ant+3);
			
				if(colisaoOvnis())
				{
					naveJogador.setPx(px_ant-10);
					naveJogador.setVelocidade(0);
					naveJogador.incEnergia(-1);
				}
			}			
		}
		
		//Nem para direita nem para a esquerda
		if((keyStates & RIGHT_PRESSED)==0 && (keyStates & LEFT_PRESSED)==0)
			naveJogador.image = nave1; //Nave na posição padrão
		
	}
	
	public void sair() {
		//this.midlet.destroyApp(true);
		this.midlet.notifyDestroyed();
	}
	
	public void setCurrent(Displayable displayable) {
		this.telas.push(displayable);
		this.dpy.setCurrent(displayable);
	}
	
}