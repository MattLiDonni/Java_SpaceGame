package galagastylegame;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Projectile extends RenderableObject{

	int velocity;
	boolean hitsPlayer;
	
	public Projectile (int x, int y, int width, int height, int velocity, boolean hitsPlayer) {
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
		this.velocity = velocity;
		this.hitsPlayer = hitsPlayer;
	}
	
	public Projectile (int x, int y, int width, int height, int velocity, boolean hitsPlayer, String imgPath) {
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
		this.velocity = velocity;
		this.hitsPlayer = hitsPlayer;
		this.img = new ImageIcon(imgPath);
	}
	
	@Override
	public void update() {
		this.posY += velocity;
	}

	@Override
	public boolean outOfBounds() {
		if (posY < -15) {
			return true;
		}
		
		//TODO change this to take in screen height
		else if (posY > 800) {
			return true;
		}
		return false;
	}
	//If hostile, will hit player and not hit enemies. Otherwise, will not hit player (self) but will hit enemies.
	public boolean hostile() {
		return hitsPlayer;
	}
	//TODO 
	public void onCollision(RenderableObject object) {
		
		
	}
	
	public void onDestroy() {
		
		
	}
}
