package io.github.usc_cs201_final_project.cs201_final_project_backend;

public class Combatant {
	private int healthCurrent;
	private int healthMax;
	private int costumeID;
	
	public Combatant(int cid, int health) {
		costumeID = cid;
		healthCurrent = health;
		healthMax = health;
	}
	
	public void takeDamage(int damage) {
		healthCurrent -= damage;
	}
	
	public boolean isDead() {
		return healthCurrent <= 0;
	}

}
