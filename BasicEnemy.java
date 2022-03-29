package galagastylegame;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.Timer;
public class BasicEnemy extends RenderableObject{

	int scoreWorth, moveInt = 1, speed = 4;
	private boolean canFire = true;
	public BasicEnemy() {
		this.posX = 100;
		this.posY = 100;
		this.width = 25;
		this.height = 25;
		this.scoreWorth = 10;
	}
	public BasicEnemy(int x, int y, int width, int height, int scoreWorth) {
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
		this.scoreWorth = scoreWorth;
	}
	public BasicEnemy(int x, int y, int width, int height, int scoreWorth, String imgPath) {
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
		this.scoreWorth = scoreWorth;
		this.img = new ImageIcon(imgPath);
	}
	
	@Override
	public void update() {
		if (posX < 15) {
			moveInt = 1;
		}
		else if (posX > 650) {
			moveInt = -1;
		}
		posX += moveInt*speed;
		
		if (canFire == true) {
			//creates a new thread to run the timer for in between shots
			(new Thread() {
				  public void run() {
				    try {
						canFire = false;
						sleep((500));
						canFire = true;
					}
				    catch (InterruptedException e) { e.printStackTrace(); }
				    interrupt();
				  }
				 }).start();
			
		}
	}
	
	public boolean fire() {
		return canFire;
	}

	
	public void waitToFire() {
		try {
			Thread.sleep(1000);
			
			canFire = true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public boolean outOfBounds() {
		if (posY > 625) 
			return true;
		return false;
	}
	
	public void onDestroy() {
		this.posX = -100;
		this.posY = -100;
	}
	
	public int getScoreWorth() {
		return scoreWorth;
	}
	
	
	public String toString() {
		return ("[BasicEnemy: pos(" + posX + ", " + posY + "), " + width + "x" + height + "px, "  + scoreWorth + " points]");
	
	}

}
