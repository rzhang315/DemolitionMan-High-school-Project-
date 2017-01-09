import java.util.*;

class Task extends TimerTask
{
	private Grid applet;

	public Task(Grid a){
		applet = a;
	}

	public void run(){
		applet.update();
	}
}
