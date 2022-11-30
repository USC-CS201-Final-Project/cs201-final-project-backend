package io.github.usc_cs201_final_project.cs201_final_project_backend;

public class Player extends Combatant {
	private String username;
	private int playerId;

	public Player(String username, int costumeId) {
		super(60, costumeId);
		this.username = username;
		playerId = -1;
	}
	
	public void enterGame(int pid, int maxHealth) {
		playerId = pid;
		setMaxHealth(maxHealth);
	}

	public String getUsername() {
		return username;
	}

	public void changeCostume(int costumeID) {
		setCostumeID(costumeID);
	}
	
	public int getId() {
		return playerId;
	}
}
