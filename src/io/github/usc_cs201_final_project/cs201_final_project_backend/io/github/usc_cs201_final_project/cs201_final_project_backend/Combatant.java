package io.github.usc_cs201_final_project.cs201_final_project_backend;

public class Combatant {
	private int maxHealth;
	private int currentHealth;
	private int costumeID;

	Combatant(int maxHealth, int costumeID) {
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.costumeID = costumeID;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	public int getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(int hp) {
		this.currentHealth = hp;
	}
	public int getCostumeID() {
		return costumeID;
	}
	public void setCostumeID(int costumeID) {
		this.costumeID = costumeID;
	}
	public void takeDamage(int damage) {
		setCurrentHealth(currentHealth - damage);
	}

}