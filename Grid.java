import java.applet.Applet;
import java.awt.Graphics;
import java.awt.*;
import java.util.*;
import java.util.Timer;
import java.awt.event.*;
import java.awt.image.*;

public class Grid extends Applet implements KeyListener
{
    private static BufferedImage offscreen;
    private static Graphics bufferGraphics;
    private static Dimension dim;
    private static HashMap<Location,Sprite> map;

    private static ImageObserver observer;

	public static final int CELL_SIZE = 32;

    private Location locO,locN;

    private Location locO2,locN2;

    private Player player1, player2;

    public static Graphics getBufferedGraphics() { return bufferGraphics; }
    public static Dimension getDimension() { return dim; }
    public static HashMap<Location,Sprite> getMap() { return map; }
    public static ImageObserver getObserver() { return observer; }




    public void init(){
    	observer = this;
    	map = new HashMap<Location,Sprite>();
        dim = getSize();
        offscreen = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
        bufferGraphics = offscreen.getGraphics();
        Timer ti = new Timer();
        Task ta = new Task(this);
        addKeyListener(this);



		for (int i = 0; i< 20; i++)
		{
			Brick brick= new Brick(false);
			Location temp = new Location ((int)(Math.random()*dim.width/CELL_SIZE),
				                    (int)(Math.random()*dim.height/CELL_SIZE));
			map.put(temp,brick);
		}

        for (int i = 0; i< 100; i++)
		{
			Brick brick= new Brick(true);
			Location temp = new Location ((int)(Math.random()*dim.width/CELL_SIZE),
				                    (int)(Math.random()*dim.height/CELL_SIZE));
			map.put(temp,brick);
		}


		map.remove(new Location(0,0));
		map.remove(new Location(0,1));
		map.remove(new Location(1,0));
		map.remove(new Location(19,0));
		map.remove(new Location(18,0));
		map.remove(new Location(19,1));


		locO = new Location(0,0);
		locO2 = new Location(19,0);
		player1 = new Player();
		player2 = new Player();

		map.put(locO,player1);
		map.put(locO2,player2);
        ti.schedule(ta,30,30);
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == e.VK_S)
        	{
        		locN = new Location (locO.x,locO.y+1);
        		if(player1.move(locO,locN))locO = locN;
        	}
        if(e.getKeyCode() == e.VK_W)
        	{
        		locN = new Location (locO.x,locO.y-1);
        		if(player1.move(locO,locN))locO = locN;
        	}
        if(e.getKeyCode() == e.VK_A)
        	{
        		locN = new Location (locO.x-1,locO.y);
        		if(player1.move(locO,locN))locO = locN;
        	}
        if(e.getKeyCode() == e.VK_D)
        	{
        		locN = new Location (locO.x+1,locO.y);
        		if(player1.move(locO,locN))locO = locN;
        	}
        if(e.getKeyCode() == e.VK_SPACE)
        	{
        		player1.placeBomb();
           	}


    	if(e.getKeyCode() == e.VK_DOWN)
        	{
        		locN2 = new Location (locO2.x,locO2.y+1);
        		if(player2.move(locO2,locN2))locO2 = locN2;
        	}
        if(e.getKeyCode() == e.VK_UP)
        	{
        		locN2 = new Location (locO2.x,locO2.y-1);
        		if(player2.move(locO2,locN2))locO2 = locN2;
        	}
        if(e.getKeyCode() == e.VK_LEFT)
        	{
        		locN2 = new Location (locO2.x-1,locO2.y);
        		if(player2.move(locO2,locN2))locO2 = locN2;
        	}
        if(e.getKeyCode() == e.VK_RIGHT)
        	{
        		locN2 = new Location (locO2.x+1,locO2.y);
        		if(player2.move(locO2,locN2))locO2 = locN2;
        	}
        if(e.getKeyCode() == e.VK_ENTER)
        	{
        		player2.placeBomb();
           	}

        // check to see if the players are at the new location
    }


    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}


    public void update(){
    	ArrayList<Location> myKeys = new ArrayList<Location>(map.keySet());
		for (Location l: myKeys){
			Sprite s = map.get(l);
			if(s != null) s.update();
			if(s instanceof Fire) ((Fire)s).move(l);
			if(s instanceof Bomb) ((Bomb)s).tryExplode(l);
		}

		paint(getGraphics());

    }

	public void paint(Graphics g)
	{

		Dimension d = getSize();
        if(d.width != dim.width || d.height != dim.height){
            dim = d;
            offscreen = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
            bufferGraphics = offscreen.getGraphics();
        }

		bufferGraphics.setColor(Color.white);
		bufferGraphics.fillRect(0,0,dim.width,dim.height);

    	ArrayList<Location> myKeys = new ArrayList<Location>(map.keySet());
		for (Location l: myKeys) {
			Sprite s = map.get(l);
			if(s != null) s.draw(l);
		}
        g.drawImage(offscreen,0,0,this);
	}
}