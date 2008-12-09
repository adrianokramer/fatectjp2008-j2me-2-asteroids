package br.com.fatecjp.core;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;


public class ObjFlying extends ObjGame
{
	public int	type,
		 	speed,
 			maxSpeed,
 			energy;
	
	public ObjFlying()
	{
		type = 0;
		speed = 0;
		maxSpeed = 0;
		energy = 0;
	}

	public ObjFlying(Image i, int t, int x, int y, int veloM, int e)
	{
		super.image = i;
		super.width = image.getWidth();
		super.heigth = image.getHeight();

		super.px = x;
		super.py = y;
		
		this.type = t;
		this.speed = 0;
		this.maxSpeed = veloM;
		this.energy = e;
	}

	public int getType()
	{
		return type;
	}
	public void incEnergy(int i)
	{
		energy+=i;
	}
	public void setEnergy(int i)
	{
		energy = i;
	}

	public int getEnergy()
	{
		return energy;
	}

	public void incSpeed(int x)
	{
		if(speed+x <= maxSpeed)
			speed+=x;
		else
			speed = maxSpeed;
	}

	public void setSpeed(int i)
	{
		this.speed = i;
	}
	
	public int getSpeed()
	{   
			return speed;
	}
	
	public int getMaxSpeed()
	{   
			return maxSpeed;
	}

	public void setMaxSpeed(int i)
	{
		maxSpeed = i;
	}
	public Sprite selfDraw()
	{		
		Sprite spr_asteroid;
		//g.drawImage(super.image, super.px, super.py, Graphics.TOP | Graphics.LEFT);
		// Define um sprite de animação para a nave:
		spr_asteroid = new Sprite(super.image, super.image.getWidth(), super.image.getHeight());
		return spr_asteroid;
	}

	public void meDesenha(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
		
		
