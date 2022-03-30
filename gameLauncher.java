package galagastylegame;

import java.awt.Label;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class gameLauncher {
	private static boolean running = true;
	public static Scanner consoleInput;
	public static double secondsForEachTick = 1/60.0; 
	static JFrame frame = new JFrame("Space Game | ");
	static String fps;
	static KeyInputs keyin = new KeyInputs();
	static PlayerMovementHandler playerMovement = new PlayerMovementHandler();
	
	//Inputs, get passed to movement handler
	static int xMove, yMove, shoot;
	
	//creates game window at launch, and then starts the game looper.
	public static void main(String[] args) {
		instantiateGame();
		gameLooper();
	}
	//Creates and initializes game window
	public static void instantiateGame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setSize(700, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
	
	//contains a while(running) loop that runs until told to stop. Handles all ticks/frames.
	private static void gameLooper() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		
	    int tickCount = 0;
	    boolean ticked = false;
		
	    //TODO create separate level files that works similarly to this.
	    Level l1 = new Level("Level 1");
	    l1.addEnemy(new BasicEnemy(100, 100, 25, 25, 50, "img/basicEnemy.png"));
	    l1.addEnemy(new BasicEnemy(200, 100, 25, 25, 50, "img/basicEnemy.png"));
	    l1.addEnemy(new BasicEnemy(300, 100, 25, 25, 50, "img/basicEnemy.png"));
	    l1.addEnemy(new noShootEnemies(100, 175, 25, 25, 50, "img/noShootEnemy.png"));
	    l1.addEnemy(new noShootEnemies(175, 175, 25, 25, 50, "img/noShootEnemy.png"));
	    l1.addEnemy(new noShootEnemies(250, 175, 25, 25, 50, "img/noShootEnemy.png"));
	    l1.addEnemy(new noShootEnemies(325, 175, 25, 25, 50, "img/noShootEnemy.png"));
	    l1.addEnemy(new noShootEnemies(400, 175, 25, 25, 50, "img/noShootEnemy.png"));
	    l1.addEnemy(new noShootEnemies(50, 225, 25, 25, 50, "img/noShootEnemy.png"));
	    l1.addEnemy(new noShootEnemies(125, 225, 25, 25, 50, "img/noShootEnemy.png"));
	    l1.addEnemy(new noShootEnemies(200, 225, 25, 25, 50, "img/noShootEnemy.png"));
	    l1.addEnemy(new noShootEnemies(275, 225, 25, 25, 50, "img/noShootEnemy.png"));
	    l1.addEnemy(new noShootEnemies(350, 225, 25, 25, 50, "img/noShootEnemy.png"));
	    
	    //Creates screenpaint object, which takes a player movement handler and a level file.
	    JPanel screenpaint = new screenPainter(playerMovement, l1);
	    frame.add(screenpaint);
	    
	    //Adds KeyInputs key listener object to window
		screenpaint.addKeyListener(keyin);
		screenpaint.setFocusable(true);
		screenpaint.grabFocus();
		
		//Runs until game stops. 
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds = unprocessedSeconds + passedTime / 1000000000.0;
			
			//Ticks happen here until enough ticks happen to incur a render
			while (unprocessedSeconds > secondsForEachTick)
			{
				tick();
				unprocessedSeconds -= secondsForEachTick;
				ticked = true;
				tickCount++;
				
	            if(tickCount % 60 == 0){
	                //System.out.println(frames + " fps");
	                fps = frames + " fps";
	                previousTime += 1000;
	                frame.setTitle("Space Game | " + fps);
	                frames = 0;       
	            }
	        }		
			
	        if(ticked)
	        {
	            render(screenpaint);
	            frames++;
	            ticked = false;           
	        }
		}
	}

	//Executed every render, processes inputs so they happen only when they'll actually show. 
	private static void render(JPanel screenpaint) {
		playerMovement.processMove(xMove, yMove);
		playerMovement.shootCheck(shoot);
		//player.update();
		
		//Repaints screen every render() call.
		//Entire game is in here basically, which is WRONG
		screenpaint.repaint();
		frame.setVisible(true);
	}

	//Executed every tick. Good for taking inputs as it happens a lot more often than frame renders
	private static void tick() {
		xMove = keyin.getXInput();
		yMove = keyin.getYInput();
		shoot = keyin.getShoot();
	}

	
	
}
