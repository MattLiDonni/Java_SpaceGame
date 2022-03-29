package galagastylegame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputs implements KeyListener 
{
	/*
	 Inputs:
	 W: Up
	 A: Left
	 S: Down
	 D: Right
	 K: Shoot
	 	 
	 
	 */
	int x_axis = 0, y_axis = 0;
	int wPress = 0, aPress = 0, sPress = 0, dPress = 0, kPress = 0, bPress = 0;
	@Override
	public void keyTyped(KeyEvent e) {
		//Ignore
	}

	//Executed when a key is pressed, calls processInput() and passes the key event.
	@Override
	public void keyPressed(KeyEvent e) {
		processInput(KeyEvent.getKeyText(e.getKeyCode()));	
	}

	//Executed when a key is released, calls releaseInput() and passes the key event.
	@Override
	public void keyReleased(KeyEvent e) {
		releaseInput(KeyEvent.getKeyText(e.getKeyCode()));
	}
	
	
	public void processInput(String keyName) {
		//Sets the right and up (d and w) to 1 and left and down (a and s) to -1, adds at end. Allows no movement on directions if both are pressed,
		//but instantaneous reaction when one is let go. Hopefully stops and stuttering movement due to multiple movement inputs and how keyHandler works
		
		if (keyName.equals("W")) {
			wPress = 1;
			//y_axis = 1;
		}
		if (keyName.equals("S")) {
			sPress = -1;
			//y_axis = -1;
		}
		if (keyName.equals("D")) {
			dPress = 1;
			//x_axis = 1;
		}
		if (keyName.equals("A")) {
			aPress = -1;
			//x_axis = -1;
		}
		if (keyName.equals("K")) {
			kPress = 1;
		}
		if (keyName.equals("B")) {
			bPress = 1;
		}
		x_axis = dPress + aPress;
		y_axis = wPress + sPress;
		//Debug for key presses
		//System.out.println(x_axis + " " + y_axis);
	}
	public int getXInput() {
		return x_axis;
	}
	public int getYInput() {
		return y_axis;
	}
	public int getShoot() {
		return kPress;
	}
	public int getBPress() {
		return bPress;
	}
	
	//Sets inputs to 0 if input is released
	private void releaseInput(String keyName) {
		if (keyName.equals("W")) {
			wPress = 0;
			//y_axis = 0;
		}
		if (keyName.equals("S")) {
			sPress = 0;
			//y_axis = 0;
		}
		if (keyName.equals("D")) {
			dPress = 0;
			//x_axis = 0;
		}
		if (keyName.equals("A")) {
			aPress = 0;
			//x_axis = 0;
		}
		if (keyName.equals("K")) {
			kPress = 0;
		}
		if (keyName.equals("B")) {
			bPress = 0;
		}
		x_axis = dPress + aPress;
		y_axis = wPress + sPress;
		
		//Debugging code for key release
		//System.out.println(x_axis + " " + y_axis);
	}
		
}
