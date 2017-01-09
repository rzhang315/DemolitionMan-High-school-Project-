import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import javax.imageio.*;
import java.io.*;

class PowerUp extends Sprite{
	private static Image imgR, imgB, imgG;
	private String type;
	private int timeToExpire;

	public PowerUp(){
		timeToExpire = 900;
		try{
			if(imgR==null) imgR=ImageIO.read(new File("PowerUpRed.png"));
			if(imgG==null) imgG=ImageIO.read(new File("PowerUpGreen.png"));
			if(imgB==null) imgB=ImageIO.read(new File("PowerUpBlue.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		double rand = Math.random();
		if      (rand<.33333)  type = "speedOfPlayer";
		else if (rand<.666666) type = "powerOfBomb";
		else                   type = "numActiveBombs";
	}

	public boolean isExpired(){return timeToExpire<=0;}
	public void draw(Location loc){
		int cs = Grid.CELL_SIZE;
		int x = loc.x*cs;
		int y = loc.y*cs;
		if(type.equals("speedOfPlayer")){Grid.getBufferedGraphics().drawImage(imgR,x,y,x+cs,y+cs,0,0,64,64,Grid.getObserver());}
		if(type.equals("powerOfBomb")){Grid.getBufferedGraphics().drawImage(imgG,x,y,x+cs,y+cs,0,0,64,64,Grid.getObserver());}
		if(type.equals("numActiveBombs")){Grid.getBufferedGraphics().drawImage(imgB,x,y,x+cs,y+cs,0,0,64,64,Grid.getObserver());}

	}
	public void update(){timeToExpire--;}
	public String getType(){return type;}
}
