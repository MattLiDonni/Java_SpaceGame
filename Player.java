package galagastylegame;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Player extends RenderableObject {

	
	public Player(int width, int height, int posX, int posY) {
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
	}
	
	public Player(int width, int height, int posX, int posY, String imgPath) {
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
		this.img = new ImageIcon(imgPath);
	}

	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	public void moveTo(int x, int y) {
		this.posX = x;
		this.posY = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public Rectangle getCol() {
		return new Rectangle(posX, posY, width, height);
	}
	
	@Override
	public void update() {
		
	}
	
	public boolean outOfBounds() {
		return false;
	}
	
	@Override
	public void onDestroy() {
		
	}
	
}
