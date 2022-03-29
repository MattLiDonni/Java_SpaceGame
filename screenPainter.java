package galagastylegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class screenPainter extends JPanel {

	PlayerMovementHandler moveHandler;
	int proj_x = 100;
	int proj_y = 200;
	int score = 0;
	
	ArrayList<RenderableObject> renderList = new ArrayList<RenderableObject>();	
	ArrayList<BasicEnemy> enemyList = new ArrayList<BasicEnemy>();	
	ImageIcon playerImg;
	String splashText = "";
	Font splashFont = new Font("Arial", Font.PLAIN, 50);
	boolean winTriggered = false;
	
	//Takes a moveHandler (which is really an input handler now) and a level object. Pulls info from level object to add to level.
	public screenPainter(PlayerMovementHandler moveHandler, Level lvl) {
		this.moveHandler = moveHandler;
		//Iterates through level data's enemy list, and adds them to the render list.
		for (int enem = 0; enem < lvl.getEnemyList().size(); enem++) {
			renderList.add(lvl.getEnemyList().get(enem));
			System.out.println("Enemy added: " + renderList.get(enem).toString());
		}
		playerImg = new ImageIcon("img/player.png");
	}
	
	//Is executed every time repaint is called, which happens at a max of 60 times per seconds (FPS)
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//Graphics2D paints each object every frame.
		Graphics2D g2d = (Graphics2D) g;
		Graphics2D g2dprojectile = (Graphics2D) g;
		
		//Anti aliasing to keep shapes from smearing across screen
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2dprojectile.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		setBackground(Color.BLACK);
		
		// x/yGo are where you actually move, and the move handler x/y_pos are where you are trying to move.
		// x/yGo are corrected values, accounting for the screen bounds.
		int xGo = moveHandler.x_pos;
		int yGo = moveHandler.y_pos;
		//Left bound
		if (moveHandler.x_pos < 0) {
			xGo = 0;
			moveHandler.x_pos = 0;
		}
		//Right bound
		else if (moveHandler.x_pos > getWidth() - 25) {
			moveHandler.x_pos = getWidth() - 25;
			xGo = moveHandler.x_pos;
		}
		//If not at X bound, X movement functions as normal.
		else {
			xGo = moveHandler.x_pos;
		}
		//Upper bound
		if (moveHandler.y_pos < 400) {
			yGo = 400;
			moveHandler.y_pos = 400;
		}
		//Lower bound
		else if (moveHandler.y_pos > getHeight() - 25) {
			moveHandler.y_pos = getHeight() - 25;
			yGo = moveHandler.y_pos;
		}
		//If not at Y bound, Y movement functions as normal.
		else {
			yGo = moveHandler.y_pos;
		}
		
		//Bounds rectangle
		g2dprojectile.setColor(new Color(15, 15, 15));
		g2dprojectile.fillRect(0, 400, 1000, 500);
		
		//player is rendered every frame at xGo, yGo
		Rectangle player = new Rectangle(xGo, yGo, 25, 25);
		Image pli = playerImg.getImage();
		g2d.drawImage(pli, xGo, yGo, this);
		
		//checks move handler to see if shoot is pressed. move handler only returns 1 if shoot key has been released before being pressed
		if (moveHandler.getShoot() == 1) {
			moveHandler.shootSuccess();
			renderList.add(new Projectile(moveHandler.x_pos + 10, moveHandler.y_pos - 5, 5, 15, -5, false, "img/greenProj.png"));
		}
		
		int projCount = 0;
		int enemyCount = 0;
		
		//Iterates through rendering list, running the update method on each object in the list.
		for (int i = 0; i < renderList.size(); i++) {
			RenderableObject ob = renderList.get(i);
			ob.update();
						
			//creates object based on the Collider bounds on the object. Checks if object is out of predefined bounds.
			if (ob.outOfBounds()) {
				//System.out.println("OOB: " + ob);
				renderList.remove(i);
				if (ob.getClass() == BasicEnemy.class || ob.getClass() == noShootEnemies.class) {
					enemyList.remove(ob);
				}
			}	
			
			//Renders images
			g2d.drawImage(ob.getRendImage(), ob.posX, ob.posY, this);

			//Adds enemies to enemy list, 
			if (ob.getClass() == BasicEnemy.class || ob.getClass() == noShootEnemies.class) {
				enemyCount++;
				enemyList.add((BasicEnemy) ob);
				if (((BasicEnemy)ob).fire()) {
					renderList.add(new Projectile(ob.posX + 10, ob.posY + 35, 5, 15, 2, true, "img/redProj.png"));
				}
				if (ob.getCol().intersects(player.getBounds())) {
					winTriggered = true;
					screenSplash("Game over", 4);
					gameLose(xGo, yGo);
				}
			}
			
			//Adds to current projectile count, and then runs a for loop checking every enemy position to see if it hits one, if it does,
			//then both enemy and projectile leave render list.
			if (ob.getClass() == Projectile.class) {
				
				//Check if player hit by hostile projectile
				if (((Projectile)ob).hostile()) {
					if (player.intersects(ob.getCol())) {
						winTriggered = true;
						screenSplash("Game over", 4);
						gameLose(xGo, yGo);
					}
				}
				
				projCount++;
				//Checks if current projectile is colliding with any enemies in the enemyList.
				boolean hitThisTime = true;
				for (int n = 0; n < enemyList.size(); n++) {
					if (ob.getCol().intersects(enemyList.get(n).getCol()) && !((Projectile)ob).hostile()) {
						//Prevents multiple collisions per enemy hit.
						if (hitThisTime) {
							score += enemyList.get(n).getScoreWorth();
							hitThisTime = false;
						}
						//Removes enemy and the projectile from the render list. Removes enemy from the enemy list too. Runs onDestroy
						//for both objects, TODO later.
						renderList.remove(ob);
						renderList.remove(enemyList.get(n));
						ob.onDestroy();
						enemyList.get(n).onDestroy();
						enemyList.remove(n);			
					}					
				}
			}
		}
		
		//Ammo Counter update
		Graphics2D gText = g2d;
		gText.setColor(Color.WHITE);
		gText.setFont(new Font("Arial", Font.PLAIN, 20));
		if (!winTriggered) {
			gText.drawString(moveHandler.getAmmo() + "/" + moveHandler.getAmmoMax(), player.x + 30, player.y - 5);
		}
		
		
		gText.setFont(splashFont);
		gText.drawString(splashText, 200, 250);
		
		//Win check
		if (projCount == 0 && enemyCount == 0 && !winTriggered) {
			winTriggered = true;
			screenSplash("You win!", 4);
			gameWin();
		}	
	}
	
	private void gameWin() {
		(new Thread() {
			  public void run() {
			    try {
					Thread.sleep(5000);
					System.exit(0);
				}
			    catch (InterruptedException e) { e.printStackTrace(); }
			    interrupt();
			  }
			 }).start();
	}
	
	private void gameLose(int x, int y) {
		moveHandler.speedMultiplier = 0;
		//Ends program
		(new Thread() {
			  public void run() {
			    try {
					Thread.sleep(5000);
					System.exit(0);
				}
			    catch (InterruptedException e) { e.printStackTrace(); }
			    interrupt();
			  }
			 }).start();
		
		//Player explosion
		(new Thread() {
			  public void run() {
			    try {
			    	playerImg = new ImageIcon("img/playerExplode.png");
					Thread.sleep(100);
			    	playerImg = new ImageIcon("img/empty.png");
			    	Thread.sleep(100);
			    	playerImg = new ImageIcon("img/playerExplode.png");
			    	Thread.sleep(100);
			    	playerImg = new ImageIcon("img/empty.png");
			    	Thread.sleep(100);
			    	playerImg = new ImageIcon("img/playerExplode.png");
			    	Thread.sleep(100);
			    	playerImg = new ImageIcon("img/empty.png");
			    }
			    catch (InterruptedException e) { e.printStackTrace(); }
			    interrupt();
			  }
			 }).start();
	}

	public void screenSplash(String text, float duration) {
		splashText = text;
		(new Thread() {
			  public void run() {
			    try {
					Thread.sleep((long)(1000 * duration));
					splashText = "";
				}
			    catch (InterruptedException e) { e.printStackTrace(); }
			    interrupt();
			  }
			 }).start();
	}
	
}
