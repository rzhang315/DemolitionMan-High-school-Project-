import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.*;


class Fire extends Sprite
{
	private int direction; // range 0-360 where 0 is North increasing clockwise
	private int distanceLeft; // distance left to travel
	private static Image myImage;

	public Fire(int dir, int dis){
		if(myImage == null){
			try{
				myImage = ImageIO.read(new File("Fire.png"));
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		direction = dir;
		distanceLeft = dis;
	}

	public void update(){
	}

	// postcondition loc is unchanged
	public void move(Location loc){
		System.out.print(direction);
		System.out.println("locX=" + loc.x + " locY=" + loc.y);
		HashMap<Location,Sprite> m = Grid.getMap();
		m.remove(loc);
		Location l = new Location(loc);
		if(distanceLeft==0) return;
		if(direction == 0){
			l.y--;
		}
		if(direction == 90){
			l.x++;
		}
		if(direction == 180){
			l.y++;
		}
		if(direction == 270){
			l.x--;
		}
		System.out.println("locX=" + l.x + " locY=" + l.y);
		Sprite s = m.get(l);
		if(s == null){
			m.put(l, this);
			distanceLeft--;
		}else{
			if(s instanceof Brick ){
				if(((Brick)s).isDestructable){
					m.remove(l);
					Brick b = (Brick)s;
					if(b.hasPowerUp()) m.put(l,new PowerUp());
				}else return;
			}
		}
	}

	public void draw(Location loc){
		Graphics g = Grid.getBufferedGraphics();
        int cs = Grid.CELL_SIZE;
		int x = loc.x*cs;
		int y = loc.y*cs;

		g.drawImage(myImage, // The image to be drawn
		            x,y,x+cs,y+cs, // destination x,y,width,height
		            0,0,32,32, // source x,y,with,height
		            Grid.getObserver()); // stupid java junk

	}
}
