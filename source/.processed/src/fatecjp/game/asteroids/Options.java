package fatecjp.game.asteroids;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

public class Options extends Form implements CommandListener {

	boolean hard;

	Command ok;
	Command cancel;

	Display dpy;
	Displayable prev;

	ChoiceGroup cg1;

	boolean[] scratch;

	Options(Display dpy_, Displayable prev_) {
		super("Options");

		dpy = dpy_;
		prev = prev_;

		scratch = new boolean[2];

		hard = true; //Profissional

		append("Nível:");

		cg1 = new ChoiceGroup(null, Choice.EXCLUSIVE);
		cg1.append("Amador", null);
		cg1.append("Profissional", null);
		append(cg1);

		loadUI();

		ok = new Command("OK", Command.OK, 0);
		cancel = new Command("Cancel", Command.CANCEL, 1);

		addCommand(ok);
		addCommand(cancel);
		setCommandListener(this);
	}

	public void commandAction(Command c, Displayable d) {
		if (c == ok) {
			readUI();
		} else if (c == cancel) {
			loadUI();
		}
		dpy.setCurrent(prev);
	}

	void loadUI() {
		cg1.setSelectedIndex((hard ? 1 : 0), true);
	}

	void readUI() {
		hard = cg1.isSelected(1);
	}
}
