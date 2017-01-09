import java.util.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;

class Player extends Sprite
{
	private Boolean hasPlacedBomb;
	private Boolean canMove;
	private int numBombsCanBeActive;
	private int numActiveBombs;
	private int speed;// number of updates between movessd;ok
	private int bombPower;
	private int moveCount;

	private ArrayList<PowerUp> powerUpList;
	private Color myColor;
	private static Image playerImage;

	public Player()
	{
		canMove = true;
		powerUpList = new ArrayList<PowerUp>();
		hasPlacedBomb = false;
		numBombsCanBeActive = 1;
		numActiveBombs = 0;
		bombPower = 1;
		speed = 15;
		moveCount = 0;
		if(playerImage == null)
		{
			try{
				playerImage = ImageIO.read(new File("Player.png"));
			}catch(IOException e){
				e.printStackTrace();
			}
		}

		//make a random color for the player
		int red = (int)(Math.random()*255);
		int green = (int)(Math.random()*255);
		int blue = (int)(Math.random()*255);
		myColor = new Color(red, green, blue);
	}

	public boolean move (Location src, Location dst)
	{
		//if(!canMove) return false;
		HashMap<Location,Sprite> map; map = Grid.getMap();
		Sprite s = map.get(dst);
		if( s instanceof Brick ) return false;
		if( s instanceof Bomb ) return false;
		if( s instanceof Player ) return false;
		map.remove(src);
		if( s instanceof Fire ) return true;
		if( s instanceof PowerUp ){
			PowerUp p = (PowerUp)s;
			if (p.getType().equals("speedOfPlayer")) speed++;
			else if (p.getType().equals("powerOfBomb")) bombPower++;
			else if (p.getType().equals("numActiveBombs")) numBombsCanBeActive++;
			powerUpList.add(p);
			map.remove(dst);
		}
		map.put(dst, this);
		if(hasPlacedBomb){
			map.put(src, new Bomb(bombPower,this,myColor));
			hasPlacedBomb = false;
		}
		canMove = false;
		return true;
	}

	public void update()
	{
		if(moveCount % speed == 0) canMove = true;
		moveCount++;

		//iterate through the power up list
		for (int i = 0; i < powerUpList.size(); i++)
		{
			PowerUp p = powerUpList.get(i);
			p.update();
			//if the powerup is expired, remove the effects
			if (p.isExpired())
			{
				if (p.getType().equals("speedOfPlayer")) speed--;
				else if (p.getType().equals("powerOfBomb")) bombPower--;
				else if (p.getType().equals("numActiveBombs")) numBombsCanBeActive--;
				powerUpList.remove(i);
				i--;
			}
		}
	}

	public void draw (Location loc)
	{
		Graphics gr = Grid.getBufferedGraphics();
		int cs = Grid.CELL_SIZE;
		gr.setColor(myColor);
		gr.fillOval(loc.x*cs, loc.y*cs, cs, cs);
		int x = loc.x*cs;
		int y = loc.y*cs;
		gr.drawImage(playerImage, x,y,
				x+cs,y+cs,0,0,64,64,Grid.getObserver());
	}

	public void placeBomb()
	{
		if(numBombsCanBeActive>numActiveBombs){
			hasPlacedBomb = true;
			numActiveBombs++;
		}
	}

	public void decrementBombs()
	{
		numActiveBombs--;
	}
}
