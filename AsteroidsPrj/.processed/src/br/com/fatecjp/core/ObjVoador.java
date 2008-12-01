package br.com.fatecjp.core;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


public class ObjVoador extends ObjJogo
{
	private int	tipo,
		 	velocidade,
 			velocidadeMaxima,
 			energia;
	
	public ObjVoador()
	{
		tipo = 0;
		velocidade = 0;
		velocidadeMaxima = 0;
		energia = 0;
	}

	public ObjVoador(Image i, int t, int x, int y, int veloM, int e)
	{
		super.image = i;
		super.comprimento = image.getWidth();
		super.altura = image.getHeight();

		super.px = x;
		super.py = y;
		
		this.tipo = t;
		this.velocidade = 0;
		this.velocidadeMaxima = veloM;
		this.energia = e;
	}

	public int getTipo()
	{
		return tipo;
	}
	public void incEnergia(int i)
	{
		energia+=i;
	}
	public void setEnergia(int i)
	{
		energia = i;
	}

	public int getEnergia()
	{
		return energia;
	}

	public void incVelocidade(int x)
	{
		if(velocidade+x <= velocidadeMaxima)
			velocidade+=x;
		else
			velocidade = velocidadeMaxima;
	}

	public void setVelocidade(int i)
	{
		this.velocidade = i;
	}
	
	public int getVelocidade()
	{   
			return velocidade;
	}

	public void setVelocidadeMaxima(int i)
	{
		velocidadeMaxima = i;
	}
	public void meDesenha(Graphics g)
	{
		g.drawImage(super.image, super.px, super.py, Graphics.TOP | Graphics.LEFT);
	}
}
		
		
