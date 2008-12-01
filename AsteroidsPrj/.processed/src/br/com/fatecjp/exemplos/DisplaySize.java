package br.com.fatecjp.exemplos;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class DisplaySize extends MIDlet{
	private Display display;

	public void startApp(){
		Canvas canvas = new DisplaySizeCanvas();
		display = Display.getDisplay(this);
		display.setCurrent(canvas);
	}

	public void pauseApp(){}

	public void destroyApp(boolean unconditional){}
}

class DisplaySizeCanvas extends Canvas {
    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();
		
        g.setColor(255, 0, 0);
        g.fillRect(0, 0, width, height);
        
        g.setColor(0, 0, 255);
        g.drawString("Sandeep Kumar Suman", 0, 0, Graphics.TOP | Graphics.LEFT);
        
        Font font = g.getFont();
        g.drawString("Software Developer", 0, font.getHeight(), Graphics.TOP | Graphics.LEFT);
        
        g.drawString("Mobile No: +919313985248", width, height, Graphics.BOTTOM | Graphics.RIGHT);
        
        String str = "Roseindia Tech. Pvt. Ltd.";        
        font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_UNDERLINED, Font.SIZE_LARGE);
        g.setFont(font);
        g.drawString(str, 0, height/2, Graphics.LEFT | Graphics.BASELINE);
     
        int x = font.stringWidth(str);
        g.setColor(0, 0, 255);
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD | Font.STYLE_ITALIC, Font.SIZE_MEDIUM));
        g.drawString(" Delhi, India", x, height/2, Graphics.LEFT | Graphics.BASELINE);

		System.out.println("Height Of Display Screen: " + height);
		System.out.println("Width Of Display Screen: " + width);
    }
}
