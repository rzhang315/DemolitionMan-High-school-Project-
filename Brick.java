import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

class Brick extends Sprite
{
	public final boolean isDestructable;
	private static Image destructableimg;
	private static Image indestructableimg;

	public Brick(boolean d){
		isDestructable = d;
		if(destructableimg == null){
			try {
	            destructableimg = ImageIO.read(new File("destructablebrick.jpg"));
	            indestructableimg = ImageIO.read(new File("indestructablebrick.png"));
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	    }
	}
	public boolean hasPowerUp(){return Math.random()<0.5;}

	public void draw(Location loc){
		Graphics g = Grid.getBufferedGraphics();
		int cs = Grid.CELL_SIZE;
		int x = loc.x*cs;
		int y = loc.y*cs;
		if(isDestructable)
			g.drawImage(destructableimg,x,y,x+cs,y+cs,0,0,204,204,Grid.getObserver());
		else g.drawImage(indestructableimg,x,y,x+cs,y+cs,0,0,250,250,Grid.getObserver());
	}

	public void update(){
	}
}
