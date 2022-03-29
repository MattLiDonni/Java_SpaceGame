package galagastylegame;

import java.util.ArrayList;

public class Level {
	
	String name;
	ArrayList<BasicEnemy> enemyList = new ArrayList<BasicEnemy>();
	
	public Level(String name) {
		this.name = name;
	}
	
	public void addEnemy(BasicEnemy enemy) {
		enemyList.add(enemy);
	}
	
	public int enemyCount() {
		return enemyList.size();
	}
	
	public ArrayList<BasicEnemy> getEnemyList() {
		return enemyList;
	}
	
}
