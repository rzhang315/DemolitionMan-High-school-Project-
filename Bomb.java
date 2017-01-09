import java.awt.*;
import java.util.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.*;

public class Bomb extends Sprite
{
	private Player myPlayer;
	private int timeTilExplosion;
	private int maxBlast;
	private static Image myImage;
	private Color myColor;

	public Bomb(int power, Player p, Color c){
		if(myImage == null){
			try{
				myImage = ImageIO.read(new File("CartoonBomb.jpg"));
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		myPlayer = p;
		myColor = c;
		maxBlast = power;
		timeTilExplosion = 100;
	}

	public void update(){
		timeTilExplosion--;

	}

	public void tryExplode(Location loc){
		if(timeTilExplosion > 0) return;
		HashMap<Location,Sprite> m = Grid.getMap();
		for(int i=0 ; i<360 ; i+=90){
			Fire f = new Fire(i,maxBlast+1);
			m.put(loc,f);
			f.move(loc);
		}
		myPlayer.decrementBombs();
	}

	public void draw(Location loc){
		Graphics g = Grid.getBufferedGraphics();
        int cs = Grid.CELL_SIZE;
		g.fillOval(loc.x*cs, loc.y*cs, cs, cs);
		int x = loc.x*cs;
		int y = loc.y*cs;

		g.drawImage(myImage, // The image to be drawn
		            x,y,x+cs,y+cs, // destination x,y,width,height
		            0,0,128,210, // source x,y,with,height
		            Grid.getObserver()); // stupid java junk
	}
}
