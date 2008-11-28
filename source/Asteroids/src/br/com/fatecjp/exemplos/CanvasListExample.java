package br.com.fatecjp.exemplos;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class CanvasListExample extends MIDlet implements CommandListener{
  private Display display;
  private CanvasList canvas;
  private List list;
  private Command show;

  public CanvasListExample() {
    canvas = new CanvasList();
    list = new List("Employee List", Choice.IMPLICIT);
    show = new Command("Show", Command.OK, 1);
    list.append("Miss. Anusmita", null);
    list.append("Miss. Neelam", null);
    list.append("Mr. Sandeep", null);
    list.append("Mr. Suman", null);
    list.append("Mr. Saurabh", null);
    list.addCommand(show);
    display = Display.getDisplay(this);
  }

  public void startApp(){
    list.setCommandListener(this);
    display.setCurrent(list);
  }

  public void pauseApp() {}

  public void destroyApp(boolean unconditional){
    notifyDestroyed();
  }

  public void commandAction(Command c, Displayable d){
    String label = c.getLabel();
    if(label.equals("Show")){
      display.setCurrent(canvas);
    }
  }
  class CanvasList extends Canvas implements CommandListener{
    private Command back;

    public CanvasList(){
      back = new Command ( "Back", Command.BACK, 1);
      addCommand (back);
      setCommandListener (this);
    }

    public void paint(Graphics g){
      int width = getWidth();
      int height = getHeight();
      String str = list.getString(list.getSelectedIndex());

      g.setColor(0, 0, 255);
      g.drawString(str, width/2, height/2, Graphics.HCENTER | Graphics.TOP);
    }

    public void commandAction(Command c, Displayable d){
      String label = c.getLabel();      
      if(label.equals("Back")){
        display.setCurrent(list);
      }
    }
  }
}