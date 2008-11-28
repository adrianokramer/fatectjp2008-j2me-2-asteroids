package br.com.fatecjp.exemplos;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class SlideImage extends MIDlet{
  private Display display;

  public void startApp(){
    display = Display.getDisplay(this);
    display.setCurrent(new IconsCanvas()); 
  }

  public void pauseApp(){}

  public void destroyApp(boolean unconditional){
    notifyDestroyed();
  }
}

class IconsCanvas extends Canvas implements Runnable{
  SlideMenu menu = null;

  public IconsCanvas(){
    Image[] image = new Image[10];     
    try{
      image[0] = Image.createImage("/asteroid-1.png");
      image[1] = Image.createImage("/asteroid-2.png");
      image[2] = Image.createImage("/asteroid-3.png");
      image[3] = Image.createImage("/asteroid-4.png");
      image[4] = Image.createImage("/asteroid-5.png");
      image[5] = Image.createImage("/asteroid-6.png");
      image[6] = Image.createImage("/asteroid-7.png");
      image[7] = Image.createImage("/asteroid-8.png");
      image[8] = Image.createImage("/asteroid-9.png");
      image[9] = Image.createImage("/asteroid-10.png");
      
      menu = new SlideMenu(new String[]{"1", "2", "3", "4", 
      "5", "6", "7", "8", "9", "10"},  
        image,  getWidth(),  getHeight());
      new Thread(this).start();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void paint(Graphics g){
    menu.paint(g);
  }

  public void keyPressed(int key){
    int gameKey = getGameAction(key);
    if(gameKey == Canvas.RIGHT){
      menu.slideItem(1);
    }else if(gameKey == Canvas.LEFT){
      menu.slideItem(- 1);
    }
  }

  public void run(){
    try{
      while(true){
        repaint();
        synchronized(this){
          wait(100L);
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}