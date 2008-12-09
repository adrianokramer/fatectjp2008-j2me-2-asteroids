package br.com.fatecjp.core;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


public abstract class ObjGame
{
	protected Image image;
	protected int px;
	protected int py;
	protected int width;
	protected int heigth;

	public abstract void meDesenha(Graphics g);

	public int getPx()
	{
		return px;
	}
	public void setPx(int x)
	{
		px = x;
	} 
	public int getPy()
	{
		return py;
	}
	public void setPy(int y)
	{
		py = y;
	}

	public int getComprimento()
	{
		return width;
	}
	public int getAltura()
	{
		return heigth;
	}
}

	
	