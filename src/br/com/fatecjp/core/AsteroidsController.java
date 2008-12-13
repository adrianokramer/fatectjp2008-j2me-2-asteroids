package br.com.fatecjp.core;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.midlet.MIDlet;

import br.com.fatecjp.AsteroidsUIMidlet;
import br.com.fatecjp.telas.MainMenu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Stack;
import br.com.fatecjp.telas.SaveLocalHighScores;

public class AsteroidsController extends GameCanvas implements Runnable, CommandListener
{
	private ObjFlying playerShip;
	private ObjFlying ovniInimigo[];
	
	public MIDlet midlet;
	public Display dpy;
	
	private int maxX; // Largura da tela
	private int maxY; // Altura da tela
	
	private Sprite spr[];
	
	private final static int 	col1 = 30,
					col2 = 110,
					col3 = 180;

	/* mapa = Quantidade de asteroids - (tipo, px, py)) */
	private int map1[]=
				{
				50,1,col1,60,1,col3,60,1,col2,-40,1,col3,-140,4,col1,-200,
				2,col2,-270,2,col2,-500,1,col3, -580, 2,col1,-630, 4,col2,-730,
				2,col1,-780,3,col1,-850,2,col1,-930,3,col2,-990,4,col1,-1100,
				1,col3,-1200,2,col2,-1300,4,col1,-1400,1,col2,-1490,2,col3,-1600,
				3,col1,-1700,3,col3,-1800,2,col2,-1890,1,col1,-1960,4,col3,-2120,
				1,col2,-2200,2,col1,-2270,2,col3,-2350,4,col1,-2450,1,col2,-2500,
				4,col1,-2650,4,col3,-2650,3,col1,-2750,2,col2,-2830,1,col1,-2900,
				3,col1,-3100,3,col3,-3300,3,col1,-3500,1,col3,-3600,2,col2,-3670,
				1,col2,-3750,4,col3,-3770,3,col1,-3870,2,col3,-3950,1,col2,-4070,
				1,col1,-4140,3,col3,-4220,2,col2,-4300,3,col3,-4390,3,col2,-4500
				};
	
	private int map[];

	//private int limitepy = 240;
	private int time, score = 0;

	private Image img_asteroid1,img_asteroid2,img_asteroid3,img_asteroid4,	
			img_asteroid5,img_asteroid6,img_asteroid7,img_asteroid8,
			img_ship, img_red_explosion,
			img_earth, img_fire,
			img_universe;
			
	private Image[] images = new Image[8];
	
	Sprite spr_ship, spr_red_explosion, spr_earth, spr_fire;
	
	// Distancia da nave para a parte inferior e superior do mapa
	int padding_ship = 3;
	
	// Tamanho da nave
	int ship_width 		 = 29;
	
	// Velocidade lateral da nave
	int speed_left_right = 6;
	
	// Velocidade m�xima da nave
	int max_speed		 = 50;
	
	// Velocidade atual da nave
	int speed_run = 0;			
	
	private int 	numOvnis;

	private final static int 	sleepTime = 30;
	//private final static int bordaEsquerda = 36, bordaDireita = 200;

	public int started = 0;
        
	private int count;
	public boolean pausado;
	public boolean ini = true;
        public int dificult = 1;
        public int stop_sound = 1;
        public int fires = 10;
        public int destroyed = 0;
        
	//Comandos
	public static final int CMD_RESET	= 1;
	public static final int CMD_MENU	= 2;
	public static final int CMD_EXIT	= 3;
	public static final int CMD_ZLAST	= 4;
	
	public Command cmd[];
	
	private Stack screens;
	
	private static AsteroidsController instance;
	
	int upper_limit_map, upper_limit_scr, lower_limit_scr, map_x, map_y, ship_x, ship_y, earth_y;
	
	int fire_x=0, fire_y=0, fire_side=0;
	boolean reload_fire=true;
	
	int lower_limit_map = 12000;
	
	int rotation = 90; //Define o tipo de rota��o inicial que ser� feita nos asteroids...
	//////////////////////////////////////////
	
	// Mapeamento do mapa do jogo (universo.png)
	int[] asteroids_map = {
			2, 18, 1, 17,
			19, 18, 5, 18,
			9, 9, 1, 19,
			18, 9, 4, 10,
			1, 17, 9, 14,
			5, 17, 17, 1,
			17, 1, 19, 19,
			19, 4, 19, 2,
			19, 1, 19, 1,
			17, 19, 17, 19,
			17, 1, 2, 1,
			18, 18, 1, 17,
			1, 5, 1, 18,
			17, 1, 4, 17,
			11, 13, 13, 1,
			2, 13, 17, 13,
			13, 18, 1, 13,
			13, 1, 5, 1,
			4, 13, 14, 13,
			17, 1, 13, 1,
			17, 5, 1, 14,
			1, 17, 18, 17,
			14, 19, 17, 1,
			17, 1, 17, 2,
			18, 18, 6, 19,
			17, 1, 6, 1,
			6, 15, 1, 4,
			1, 11, 1, 1,
			5, 1, 15, 6,
			1, 6, 13, 9,
			6, 1, 9, 6,
			1, 2, 1, 1,
			13, 1, 13, 13,
			13, 1, 1, 8,
			12, 13, 4, 1,
			1, 9, 1, 12,
			2, 1, 9, 1,
			1, 13, 1, 13,
			15, 15, 15, 1,
			4, 1, 11, 5,
			1, 15, 1, 12,
			12, 11, 15, 1,
			1, 11, 2, 11,
			5, 11, 1, 7,
			15, 1, 11, 13,
			7, 13, 1, 12,
			12, 1, 4, 7,
			15, 13, 1, 15,
			15, 1, 13, 12,
			11, 15, 1, 2,			
			2, 18, 1, 17,
			19, 18, 5, 18,
			9, 9, 1, 19,
			18, 9, 4, 10,
			1, 17, 9, 14,
			5, 17, 17, 1,
			17, 1, 19, 19,
			19, 4, 19, 2,
			19, 1, 19, 1,
			17, 19, 17, 19,
			17, 1, 2, 1,
			18, 18, 1, 17,
			1, 5, 1, 18,
			17, 1, 4, 17,
			11, 13, 13, 1,
			2, 13, 17, 13,
			13, 18, 1, 13,
			13, 1, 5, 1,
			4, 13, 14, 13,
			17, 1, 13, 1,
			17, 5, 1, 14,
			1, 17, 18, 17,
			14, 19, 17, 1,
			17, 1, 17, 2,
			18, 18, 6, 19,
			17, 1, 6, 1,
			6, 15, 1, 4,
			1, 11, 1, 1,
			5, 1, 15, 6,
			1, 6, 13, 9,
			6, 1, 9, 6,
			1, 2, 1, 1,
			13, 1, 13, 13,
			13, 1, 1, 8,
			12, 13, 4, 1,
			1, 9, 1, 12,
			2, 1, 9, 1,
			1, 13, 1, 13,
			15, 15, 15, 1,
			4, 1, 11, 5,
			1, 15, 1, 12,
			12, 11, 15, 1,
			1, 11, 2, 11,
			5, 11, 1, 7,
			15, 1, 11, 13,
			7, 13, 1, 12,
			12, 1, 4, 7,
			15, 13, 1, 15,
			15, 1, 13, 12,
			11, 15, 1, 2,			
			2, 18, 1, 17,
			19, 18, 5, 18,
			9, 9, 1, 19,
			18, 9, 4, 10,
			1, 17, 9, 14,
			5, 17, 17, 1,
			17, 1, 19, 19,
			19, 4, 19, 2,
			19, 1, 19, 1,
			17, 19, 17, 19,
			17, 1, 2, 1,
			18, 18, 1, 17,
			1, 5, 1, 18,
			17, 1, 4, 17,
			11, 13, 13, 1,
			2, 13, 17, 13,
			13, 18, 1, 13,
			13, 1, 5, 1,
			4, 13, 14, 13,
			17, 1, 13, 1,
			17, 5, 1, 14,
			1, 17, 18, 17,
			14, 19, 17, 1,
			17, 1, 17, 2,
			18, 18, 6, 19,
			17, 1, 6, 1,
			6, 15, 1, 4,
			1, 11, 1, 1,
			5, 1, 15, 6,
			1, 6, 13, 9,
			6, 1, 9, 6,
			1, 2, 1, 1,
			13, 1, 13, 13,
			13, 1, 1, 8,
			12, 13, 4, 1,
			1, 9, 1, 12,
			2, 1, 9, 1,
			1, 13, 1, 13,
			15, 15, 15, 1,
			4, 1, 11, 5,
			1, 15, 1, 12,
			12, 11, 15, 1,
			1, 11, 2, 11,
			5, 11, 1, 7,
			15, 1, 11, 13,
			7, 13, 1, 12,
			12, 1, 4, 7,
			15, 13, 1, 15,
			15, 1, 13, 12,
			11, 15, 1, 2,			
			2, 18, 1, 17,
			19, 18, 5, 18,
			9, 9, 1, 19,
			18, 9, 4, 10,
			1, 17, 9, 14,
			5, 17, 17, 1,
			17, 1, 19, 19,
			19, 4, 19, 2,
			19, 1, 19, 1,
			17, 19, 17, 19,
			17, 1, 2, 1,
			18, 18, 1, 17,
			1, 5, 1, 18,
			17, 1, 4, 17,
			11, 13, 13, 1,
			2, 13, 17, 13,
			13, 18, 1, 13,
			13, 1, 5, 1,
			4, 13, 14, 13,
			17, 1, 13, 1,
			17, 5, 1, 14,
			1, 17, 18, 17,
			14, 19, 17, 1,
			17, 1, 17, 2,
			18, 18, 6, 19,
			17, 1, 6, 1,
			6, 15, 1, 4,
			1, 11, 1, 1,
			5, 1, 15, 6,
			1, 6, 13, 9,
			6, 1, 9, 6,
			1, 2, 1, 1,
			13, 1, 13, 13,
			13, 1, 1, 8,
			12, 13, 4, 1,
			1, 9, 1, 12,
			2, 1, 9, 1,
			1, 13, 1, 13,
			15, 15, 15, 1,
			4, 1, 11, 5,
			1, 15, 1, 12,
			12, 11, 15, 1,
			1, 11, 2, 11,
			5, 11, 1, 7,
			15, 1, 11, 13,
			7, 13, 1, 12,
			12, 1, 4, 7,
			15, 13, 1, 15,
			15, 1, 13, 12,
			11, 15, 1, 2};
	
	// O tiled layer
	TiledLayer tiled_layer;
	// O layer manager
	LayerManager layer_manager;	
	
	//Textos 
	StringBuffer text1 = new StringBuffer();
	StringBuffer text2 = new StringBuffer();
	StringBuffer text3 = new StringBuffer();
	StringBuffer text4 = new StringBuffer();
        StringBuffer text5 = new StringBuffer();
	
	/////////////////////////////////////////
	public class BoardCommand extends Command {
		int tag;
		BoardCommand(String label, int type, int pri, int tag_) {
			super(label, type, pri);
			tag = tag_;
		}
	}
	
	void setState() {
		if(started==4){//Diferencia jogo em andamento		
			addCommand(cmd[CMD_RESET]);
			addCommand(cmd[CMD_MENU]);
			//addCommand(cmd[CMD_EXIT]);
		}
		else{//Jogo parado
			addCommand(cmd[CMD_RESET]);
			addCommand(cmd[CMD_MENU]);
			//addCommand(cmd[CMD_EXIT]);
		}
		
	}
	
	public static AsteroidsController createInstance(AsteroidsUIMidlet midlet) {
		instance = new AsteroidsController(midlet);
		return instance;
	}
	
	public static AsteroidsController getInstance() throws Exception {
		if (instance == null) {
			//instance = createInstance();
			throw new Exception("Instancia n�o criada");
		}
		return instance;
	}
	
	public AsteroidsController(MIDlet md){
		super(true);	
		
		// Pega a largura m�xima do visor.
		maxX = getWidth();
		
		// Ajusta a largura m�xima do jogo. 
		// O jogo pode ter uma largura m�xima de 240 px,
		// portanto, se o visor for maior que isso, ajusta para 240px.
		
		if (maxX > 240)
			maxX = 240;		

		// Pega a altura m�xima do visor.
		maxY = getHeight();
		
		
		this.midlet = md;		
		this.dpy = Display.getDisplay(md);	
		this.screens = new Stack();
		
		cmd = new Command[CMD_ZLAST];
		cmd[CMD_MENU] = new BoardCommand("Main Menu", Command.EXIT, 7, CMD_MENU);		
		cmd[CMD_RESET] = new BoardCommand("Restart", Command.SCREEN, 1, CMD_RESET);
		//cmd[CMD_EXIT] = new BoardCommand("Sair", Command.EXIT, 7, CMD_EXIT);
		
		setCommandListener(this);				
		
		//Dados do jogo
		//carregaimages();		
		//iniciaJogo(true); //Primeira inicializa��o - T�tulo
	}
	
	public void startGame(boolean hard_reset)
	{
		//Inicializando vari�veis de jogo
		pausado = false;
		count = 0;
		started = 0;
		time = 0;
		score = 0;
		earth_y = 10000;
		fires = 10;
		destroyed = 0;
                lower_limit_map = 12000;
		
		setState(); //Adiciona op��es dispon�veis
		
		carregaimages();
		
		// Carrega o tileset
		try {
			
			spr_fire     = new Sprite(img_fire, 3, 40);
			
			// Define um sprite de anima��o para a nave:
			img_ship 			= Image.createImage("/images/ship.png");
			spr_ship            = new Sprite(img_ship, 29, 33);
			
			// Define um sprite para a anima��o da explos�o da nave:
			img_red_explosion	= Image.createImage("/images/red_explosions_25x25.png");
			spr_red_explosion   = new Sprite(img_red_explosion, 25, 25);
			
			// Define o planeta - Fim do jogo:
			img_earth     = Image.createImage("/images/earth.png");
			spr_earth     = new Sprite(img_earth, 100, 100);
			
			img_universe = Image.createImage("/images/universe.png");
			
			
		} catch (IOException e) {}
		
		
		/* Cria o Tiled Layer: 
		 * 4   = Quantidade de blocos horizontais.
		 * 200 = Quantidade de blocos verticais.
		 * 60  = Altura/Largura de cada bloco em Pixels.
		 */
		tiled_layer = new TiledLayer(4, 200, img_universe, 60,60);
		
		// Associa as posi��es no objeto com o tiled_layer			
		int lin, col;			
		for(int i=0; i < 800;i++) {
			col  = i % 4;       
			lin  = i / 4;        

			tiled_layer.setCell(col, lin, asteroids_map[i]);	
		}
		
		// Cria o layer manager
		layer_manager = new LayerManager();
		
		// Adiciona os Sprites e o TiledLayer no layer_manager
		layer_manager.append(spr_red_explosion);
		layer_manager.append(spr_fire);
		layer_manager.append(spr_ship);
		layer_manager.append(spr_earth);
		
		setMapa(map1);
		carregaMapa();
		createAsteroids();
		
		layer_manager.append(tiled_layer);	
		
		playerShip = new ObjFlying(img_ship, 0, col2, 200+lower_limit_map, 30, 100);
		
		
		//Inicializa na tela inicial
		if(hard_reset==true){
			pausado = true;
		}
		else pausado = false;
		
		//Inicia thread e muda o display
		if(ini==true)new Thread(this).start();
		setCurrent(this);
		
		//Inicializa��o
		
		lower_limit_map -= maxY;
		upper_limit_map = 0;
		
		//Limites do mapa:	
		lower_limit_scr = lower_limit_map + maxY - padding_ship - spr_ship.getHeight();
		upper_limit_scr = lower_limit_scr - 250; //Comprimento do espa�o jog�vel
				
		// Posi��o inicial do mapa		
		map_x = 0;
		map_y = lower_limit_map;
		
		// Posicao inicial da nave
		ship_x = maxX/2 - spr_ship.getWidth()/2;
		ship_y = lower_limit_map + maxY - padding_ship - spr_ship.getHeight();	
		
		// Atualizando objeto
		playerShip.px = ship_x;
		playerShip.px = ship_y;
		
	}
	
	public void commandAction(Command c, Displayable d) {
		switch (((BoardCommand) c).tag) {		    
			
			case CMD_EXIT:
		    	midlet.notifyDestroyed();
			break;
		    
		   case CMD_RESET:
			   //if(started!=4)
			   this.ini = false; //N�o inicia o thread
		       startGame(false);
			break;

		    case CMD_MENU:	  
		    	this.started = 0;
		    	this.ini = false;
		    	pausado = true;			    
		    	setCurrent(MainMenu.getInstance());		
		    	break;
		}
	}

	private void setMapa(int m[])
	{
		map = m;
	}
	
	private void carregaimages()
	{
		try
		{
			img_asteroid1 = Image.createImage("/images/asteroid-1.png");
			img_asteroid2 = Image.createImage("/images/asteroid-2.png");
			img_asteroid3 = Image.createImage("/images/asteroid-3.png");
			img_asteroid4 = Image.createImage("/images/asteroid-4.png");
			img_asteroid5 = Image.createImage("/images/asteroid-5.png");
			img_asteroid6 = Image.createImage("/images/asteroid-6.png");
			img_asteroid7 = Image.createImage("/images/asteroid-7.png");
			img_asteroid8 = Image.createImage("/images/asteroid-8.png");
			img_fire 	  = Image.createImage("/images/fire.png");
		}
		catch(IOException e)
		{
			System.out.println("Erro ao carregar images!");
			e.printStackTrace();
		}
		//Adicionando images no array
		images[0] = img_asteroid1;
		images[1] = img_asteroid2;
		images[2] = img_asteroid3;
		images[3] = img_asteroid4;
		images[4] = img_asteroid5;
		images[5] = img_asteroid6;
		images[6] = img_asteroid7;
		images[7] = img_asteroid8;
	}

	private void carregaMapa()
	{
		Image img = null;
		int x, y, start=1, it=0, tipo=0, ini_pos=0;
		numOvnis = map[0];
		ovniInimigo = new ObjFlying[numOvnis];
		Random r = new Random();
		
		for(y=0, x=1; y<numOvnis; x+=3, y++) //Para os 50 Asteroids
		{		
			tipo = r.nextInt(8);
			img = images[tipo]; //Selecionando modelo do asteroide randomicamente
			
                        ini_pos = r.nextInt(3);			
			//Randomizando asteroids em colunas
			int[] col = new int[3];
			col[0] = 30;
			col[1] = 110;
			col[2] = 180;
			
			//Evitando espa�os vazios constantes
			int posicao = col[ini_pos]+(ini_pos*5);
			it++;
			if(posicao>=30 && posicao<=110 && (start==1 || it>1)) {
				posicao += posicao+(ini_pos*5);
				it++;
			}		
			if(posicao >=110 && posicao<=180 && (start==1 || it>1)){
				posicao = posicao+(ini_pos*5);
				it++;
			}
			if(it>2)it=0;
			start=0;
			////////////////////////////////////
												//Coluna + ajuste
			ovniInimigo[y] = new ObjFlying(img, map[x], posicao, (map[x+2]+lower_limit_map-(2*maxY)), -10 ,3);
			//ovniInimigo[y].setVelocidade(-5);
		}
	}

	private void createAsteroids()
	{
		spr = new Sprite[numOvnis];
		//Sprite sp = naveJogador.meDesenha();
		//layer_manager.append(sp);
		//naveJogador.meDesenha(g);

		for(int c=0; c < numOvnis; c++)
		{
			Sprite sp = ovniInimigo[c].selfDraw();
			layer_manager.append(sp);
			spr[c] = sp; //Array de sprites
		}
	}
	
	private void moveAsteroids()
	{

		int x;
		//int px_ant;

		for(x=0; x<numOvnis; x++)
		{

			ovniInimigo[x].incSpeed(1);
			//px_ant = naveJogador.getPx();
			ovniInimigo[x].setPy(ovniInimigo[x].getPy() + (playerShip.getSpeed() - ovniInimigo[x].getSpeed()));
			
		}
				
	}

	private void handleStart()
	{
		if(count == sleepTime && started != 4){
			started++;		    
		}
	}
			

	private int getPosicao()
	{
		//int ovnis = mapa[0];
		int p=0;
		for(int x=0; x<numOvnis; x++)
		{
			if(/*naveJogador.getPy()*/ship_y < ovniInimigo[x].getPy())
				p++;
		}
		return numOvnis-(p);	
	}

	private void andaUniverso()
	{		
		int mov = 2;
		
		//Altera movimentos conforme a dificuldade
		mov = mov+dificult;
		
		if(map_y > upper_limit_map)
			map_y = map_y - speed_run-mov;
		
		//Movendo a nave - acompanha o mapa
		ship_y = ship_y - mov;	
		
                //Deslocamento do planeta (fim da fase)
		if(dificult==1)
                    earth_y = earth_y + 1;
                else if(dificult==2)
                    earth_y = earth_y + 0; 
		else
                    earth_y = earth_y + mov;
		
		if(fire_y!=0)
		fire_y = fire_y - 50;
		
		if(fire_y<ship_y-200)reload_fire=false; 
		
		//Movendo limites verticais
		lower_limit_scr -= mov;
		upper_limit_scr -= mov;
	}
	
	
	private void drawInterface(Graphics g)
	{	
		//// Informa��es do jogo ///
		int velocidade = playerShip.getSpeed(); //Incrementado a amostragem da velocidade
		int energia = playerShip.getEnergy();
		if(velocidade < 0)velocidade = 0; //Trata velocidade negativa
		if(energia < 0)energia = 0;
		/////////////////////////////
		
		if(started != 4){
			
			text2.setLength(0);
			text2.append("Fires:"+fires+" Destroy: "+destroyed);
			text3.setLength(0);
			text3.append("Remain:"+ getPosicao()+" Ener: "+energia+"%");//" Vel: " + velocidade*40 +" Km/h");		
			text4.setLength(0);
                        text4.append("Score:"+this.score+" Time:"+this.time+"s");//" Vel: " + velocidade*40 +" Km/h");		
                        String txt_dificult = (dificult==0) ? "Padrao" : Integer.toString(dificult);
			text5.append("Dificult: "+txt_dificult);
			text1.setLength(0);
                        text1.append("Loading...");			
						
		}
		else{
			
			//desenhaOvnis(g);
			text2.setLength(0);
			text2.append("Fires:"+fires+" Destroy: "+destroyed);
			text3.setLength(0);
			text3.append("Remain:"+ getPosicao()+" Ener: "+energia+"%");//" Vel: " + velocidade*40 +" Km/h");		
			text4.setLength(0);
                        text4.append("Score:"+this.score+" Time:"+this.time+"s");//" Vel: " + velocidade*40 +" Km/h");		
                        String txt_dificult = (dificult==0) ? "Padrao" : Integer.toString(dificult);
			text5.append("Dificult: "+txt_dificult);
                        text1.setLength(0);
			text1.append("Loading...");						
		}
		//repaint();
		
		//Carregando jogo...
		g.setColor(255, 0, 0);
		// Escreve a tecla pressionada e as posi��es x/y na tela.
		// g.drawString(text1.toString(), 100,150, Graphics.LEFT | Graphics.BOTTOM);	

	}
		

	public void checkEndGame(Graphics g)
	{	
		if(!pausado)
		{
			drawInterface(g);
		}

		if((getPosicao()-destroyed) <= 0 && (ship_y<=spr_earth.getY() || spr_ship.collidesWith(spr_earth, true)))
		{
			pausado = true;
			started = 0;
			this.ini = false;
			
			g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
			g.setColor(255,0,0);
			g.drawString("VOCE VENCEU!", 73,70, Graphics.TOP | Graphics.LEFT);
			g.drawString("PRESSIONE MENU", 60,90, Graphics.TOP | Graphics.LEFT);
			//Grava recorde (RMS - Webservice)
			setCurrent(SaveLocalHighScores.createInstance(score, time));
			//instance = null;
		}

		if(playerShip.getEnergy() <= 0)
		{
			pausado = true;
			started = 0;
			this.ini = false; //N�o inicia o thread
			
			g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
			g.setColor(255,0,0);
			g.drawString("GAME OVER", 77,70, Graphics.TOP | Graphics.LEFT);
			g.drawString("PRESSIONE MENU", 60,90, Graphics.TOP | Graphics.LEFT);
			
			//Local para testes... Este c�digo deve ser removido desta posi��o
			setCurrent(SaveLocalHighScores.createInstance(score, time));
			//instance = null;
			
		}

	}

	public void run()
	{			
		//speed_run = naveJogador.getVelocidade();
		//max_speed = naveJogador.velocidadeMaxima;
		
		//Graphics
		Graphics g = getGraphics();
		
		drawInterface(g); //Iniciando...
		flushGraphics();
		
			while(!pausado)
			{
				
				//playSound("/sounds/ship.wav");
				//System.out.println("Rodando...");
				//Thread.sleep(sleepTime);
				
				if(started == 4) //Rodando
				{					
					
					if(!pausado)
					{
						checkEndGame(g);
						drawInterface(g);
						andaUniverso();
						moveAsteroids();
						
						//setState();
						
						//Atualiza posi��o Sprites
						if(fire_side==1)
							spr_fire.setPosition(fire_x+20, fire_y);
						else
							spr_fire.setPosition(fire_x+8, fire_y);
						   
						
						spr_ship.setPosition(ship_x, ship_y);						
						
                                                //Posicionando o planeta
						spr_earth.setPosition(maxX/2 - spr_earth.getWidth()/2, earth_y);
						
						// Atualiza a posi��o do mapa
						layer_manager.setViewWindow(map_x, map_y, maxX, maxY);
						
						//Atualiza o planeta - fim do jogo
						spr_earth.paint(g);
						
						//Atualiza sprites na tela
						spr_fire.paint(g);
						spr_ship.paint(g);
						
						spr_ship.setTransform(Sprite.TRANS_NONE);						
                                                //spr_red_explosion.setFrame(10);
						
						for(int x=0; x<numOvnis; x++)
						{	
							//if(ship_y - ovniInimigo[x].getPy() > 10){ //Desenha os asteroids mais pr�ximos � nave...
								
								spr[x].setPosition(ovniInimigo[x].getPx(), ovniInimigo[x].getPy());
								
								//Mostrando coordenadas dos sprites
								//System.out.println("Nave: " + "("+spr_ship.getX()+","+spr_ship.getY()+") Asteroid: ("+spr[x].getX()+","+spr[x].getY()+")");	
								
								spr[x].paint(g);
								
								if(crashTest(spr_ship, spr[x])){
								    // Renderizando a explos�o... 	
									for(int n=0; n < 11; n++) {
										spr_red_explosion.setPosition(ship_x +2, ship_y);
										spr_red_explosion.nextFrame();
										spr_red_explosion.paint(g);
										//flushGraphics();
										//spr_red_explosion.paint(g);
										//System.out.println("Frame: "+spr_red_explosion.getFrame());
										//flushGraphics();
									}	
									System.out.println("Colisao com o asteroid "+x+" detectada!");									
									if(stop_sound==0)playSound("/sounds/damage_ship.wav");
								}	
								
								//Disparo
								if(spr_fire.collidesWith(spr[x], true)){
									
								  //Exibindo a explos�o do asteroid atingido
								  for(int n=0; n < 11; n++) {
									spr_red_explosion.setPosition(spr[x].getX() +2, spr[x].getY());
									spr_red_explosion.nextFrame();
									spr_red_explosion.paint(g);
									//flushGraphics();
						   		  }	
						
								  fire_x = 0;
								  fire_y = 0;
								  spr[x].setPosition(0, 0); 
								  ovniInimigo[x].setPx(20000);
								  ovniInimigo[x].setPy(20000);
								  
								  destroyed++;
								  score = score + 10000; //Ganha 10000 pontos por acerto
								  
								  if(stop_sound==0)playSound("/sounds/explosion_asteroid.wav");
								}
								
								
							//} //End if
						}

						layer_manager.paint(g, 0, 0);
						
					}
					
					//System.out.println("Limite inferior: "+lower_limit_map+" Limite superior: "+upper_limit_map+" Posi��o da nava: "+spr_ship.getY());					
				
					
					// Escolhe a cor vermelha
					g.setColor(255, 0, 0);
					//Limpando texto
					//text1.setLength(0);
					
					// Escreve a tecla pressionada e as posi��es x/y na tela.
					g.drawString(text2.toString(), 5,60, Graphics.LEFT | Graphics.BOTTOM);	
					g.drawString(text3.toString(), 5,40, Graphics.LEFT | Graphics.BOTTOM);	
					g.drawString(text4.toString(), 5,20, Graphics.LEFT | Graphics.BOTTOM);	
					
					flushGraphics(); //Atualizando gr�ficos
					
				   					
				}//End Rodando
				else
					handleStart();

				
				//Executa sempre...
				if(count<sleepTime){
					count++;
				}
				else{
					count=0;
					 
					//Incrementando tempo e pontua��o
					if(started==4)
						time++;
					//C�culo da pontua��o (60*velocidade + 30*energia - 10*tempo)
					//A velocidade � prioridade, a energia aumenta o ganho de pontua��o
					//e o tempo diminui os pontos
					//if(naveJogador.getVelocidade()>0)
						score += 60*(this.playerShip.getEnergy()) - 40*time;
					if(score<0)
						score=0;
				}
				
				handleGame(); //Recebe entradas do jogo
				//System.out.println("MAP_LIMIT_LO:"+lower_limit_scr+" MAP_LIMIT_UP:"+upper_limit_scr+" Posi��o da nave:"+ship_y);
				
				 //Pausa para exibir a explos�o
			    try{
					Thread.sleep(30);
				}
				catch(InterruptedException e){}
				
			} //While			
			
	}

	private void handleGame()
	{
		int keyStates = getKeyStates();
		
		//Inicializando posi��o da nave
		spr_ship.setFrame(0);

		
		if((keyStates & GAME_A_PRESSED) != 0)
		{
			/*if(pausado)
			{
				pausado = false;
				startGame(false);
			}
			*/
			System.out.println("FIRE!");
			
			if(!reload_fire && fires>0){
				fire_y = ship_y; //Inicializando FIRE position - Partindo da nave
				fire_x = ship_x;
				reload_fire = true; //Carregando arma
				if(fire_side==1)fire_side=0;
				else fire_side=1;
				fires--;
			}
			
			
		}
		

		if((keyStates & UP_PRESSED)!=0){
			// avan�a a nave
			if (ship_y > upper_limit_scr) {
				ship_y = ship_y - 10;	
				playerShip.py = ship_y;
			}
		}
		
		if((keyStates & DOWN_PRESSED)!=0)
		{	
			// retrocede a nave
			//if (ship_y > lower_limit_map - spr_ship.getHeight() - padding_ship)
			if (ship_y < lower_limit_scr){
					ship_y = ship_y + 10;
					playerShip.py = ship_y;
			}
		}

		if((keyStates & LEFT_PRESSED)!=0)
		{

			if (ship_x > 0) {
				ship_x = ship_x - speed_left_right;
				playerShip.px = ship_x;
			}
			//texto.append("left | " + ship_x + "," + ship_y + " ");
			// Nave no frame 1 (esquerda)
			spr_ship.setFrame(1);
			
		}

		if((keyStates & RIGHT_PRESSED)!=0)
		{

			if(ship_x < maxX - spr_ship.getWidth()) {
				ship_x = ship_x + speed_left_right;
				playerShip.px = ship_x;
				
			}
			//texto.append("right | " + ship_x + "," + ship_y + " ");
			// Nave no frame 2 (direita)
			spr_ship.setFrame(2);
		}

		//Teste de colis�es, ap�s atualiza��o das posi��es (ship x asteroids)
		//crashTest();

	}
	
	public boolean crashTest(Sprite ship, Sprite x) {
		
		if(ship.collidesWith(x, true)) {
			
			    ship.defineReferencePixel(ship.getWidth()/2, ship.getHeight()/2);
				//Decrementa energia da nave do jogador
			    playerShip.incEnergy(-10);	
				
			    switch (rotation) {
					case 90:
						ship.setTransform(Sprite.TRANS_ROT90);
						rotation = 180;
                                                spr_red_explosion.nextFrame();
						break;
					case 180:
						ship.setTransform(Sprite.TRANS_ROT180);
						rotation = 270;
                                                spr_red_explosion.nextFrame();
						break;
					case 270:
						ship.setTransform(Sprite.TRANS_ROT270);
						rotation = 0;
                                                spr_red_explosion.nextFrame();
						break;				
					default:
						ship.setTransform(Sprite.TRANS_NONE);
						rotation = 90;
                                                spr_red_explosion.nextFrame();
						break;
				}
			
				//spr_red_explosion.setFrame(10); //Apaga da tela o rastro da explos�o
				return true;
		}
		else return false;
	}
	
	private void playSound(String file) {
            try {
                InputStream in = getClass().getResourceAsStream (file);
                Player player = Manager.createPlayer(in, "audio/x-wav");
                player.start();
            } 
            catch (Exception e) {
                //Alert a = new Alert("Exception", e.toString(), null, null);
                //a.setTimeout(Alert.FOREVER);
                System.out.println("Erro ao reproduzir arquivo!");
                return;
            }
        }

	public void sair() {
		//this.midlet.destroyApp(true);
		this.midlet.notifyDestroyed();
	}
	
	public void setCurrent(Displayable displayable) {
		this.screens.push(displayable);
		this.dpy.setCurrent(displayable);
	}
	
} //AsteroidsController