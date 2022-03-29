package galagastylegame;

public class PlayerMovementHandler {

	int x_pos, y_pos, shoot, ammo, ammoMax = 3, speedMultiplier = 4; //ammoMax = 3, speedMultiplier = 4
	
	//multiplier for the refresh speed of your ammo.
	float refSpdMul = .75f; //float refSpdMul = 1f;
	boolean readyShoot = true;
	KeyInputs keyin;
	public PlayerMovementHandler() {
		this.x_pos = 300;
		this.y_pos = 500;
		ammo = ammoMax;
	}
	public PlayerMovementHandler(int x_pos, int y_pos) {
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		ammo = ammoMax;
	}
	
	public int[] getPos() {
		int[] coords = {x_pos, y_pos};
		return coords;
	}
	
	public void setPos(int x, int y) {
		this.x_pos = x;
		this.y_pos = y;
	}
	
	public void processMove(int x_axis, int y_axis) {
		if (x_axis == 1) {
			x_pos += 1 * speedMultiplier;
		}
		if (x_axis == -1) {
			x_pos-= 1 * speedMultiplier;
		}
		if (y_axis == 1) {
			y_pos-= 1 * speedMultiplier;
		}
		if (y_axis == -1) {
			y_pos+= 1 * speedMultiplier;
		}
		
	}
	//Returns if shoot is ready.
	public int getShoot() {
		return shoot;
	}
	
	//s is if K is held down. ready to shoot is set to true after release, and set to false upon pressing.
	//This makes shooting only as fast as you can mash the button.
	public void shootCheck(int s) {
		if (s == 1 && readyShoot && ammo > 0) {
			
			//If you fire while ammo max, it will start ammoRefresh.
			if (ammo == ammoMax) {
				ammoRefresh();
			}
			ammo -= 1;
			shoot = 1;
			readyShoot = false;
		}
		if (s == 0) {
			shoot = 0;
			readyShoot = true;
		}
	}
	//After a shot is successfully done, sets shoot to 0, meaning you cannot currently shoot.
	public void shootSuccess() {
		shoot = 0;
	}	
	
	//Refreshes ammo on a set cooldown, so long as player has less than ammoMax shots left. refSpdMul is a float that is made to shorten the
	//refresh speed, should be a float less than 1 and greater than 0. 
	public void ammoRefresh() {
		(new Thread() {
			  public void run() {
			    try {
			    	while (ammo < ammoMax) {
			    		sleep((int)(1000 * refSpdMul));
			    		ammo+=1;
			    	}
				}
			    catch (InterruptedException e) { e.printStackTrace(); }
			    interrupt();
			  }
			 }).start();
	}
	
	public int getAmmo() {
		return ammo;
	}
	public int getAmmoMax() {
		return ammoMax;
	}
	
	
}
