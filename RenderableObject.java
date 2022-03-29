package galagastylegame;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class RenderableObject {

	//This class is the base for any object that will be rendered on screen.
	//This is done so the render loops can just execute certain methods regardless of what the object actually is.
	//Not made to be directly used, but should be extended when creating a new type of object.
	
	int width, height, posX, posY;
	public Rectangle getCol() { return new Rectangle(posX, posY, width, height); }
	ImageIcon img;
	
	abstract public void update();
	abstract public boolean outOfBounds();
	
	//Not necessary, but probably will be useful sometimes.
	public void onCollision(RenderableObject object) {}
	public void onDestroy() {}
	
	public void setRendImage(String location) {
		this.img = new ImageIcon(location);
	}
	
	public Image getRendImage() {
		if (img != null) {
			return img.getImage();
		}
		else {
			ImageIcon ei = new ImageIcon("img/errorImg.png");
			return ei.getImage();
		}
		
	}
}
