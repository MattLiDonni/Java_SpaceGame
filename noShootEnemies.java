package galagastylegame;

import javax.swing.ImageIcon;

public class noShootEnemies extends BasicEnemy	{
	
	public noShootEnemies(int x, int y, int width, int height, int scoreWorth, String imgPath) {
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
		this.scoreWorth = scoreWorth;
		this.img = new ImageIcon(imgPath);
		this.speed = 5;
	}
	
	public void update() {
		if (posX < 15) {
			moveInt = 1;
			posY += 25;		
		}
		else if (posX > 650) {
			moveInt = -1;
			posY += 25;		
		}
		posX += moveInt*speed;
	}
	
	@Override
	public boolean fire() {
		return false;
	}
	
	@Override
	public String toString() {
		return ("[UnnamedEnemy: pos(" + posX + ", " + posY + "), " + width + "x" + height + "px, "  + scoreWorth + " points]");
	
	}
}
