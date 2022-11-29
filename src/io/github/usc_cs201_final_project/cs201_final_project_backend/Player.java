package io.github.usc_cs201_final_project.cs201_final_project_backend;

public class Player extends Combatant {
	private String username;
	private int playerId;

	Player(int pid, int maxHealth,  int costumeId, String username) {
		super(maxHealth, costumeId);
		this.setUsername(username);
		playerId = pid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void changeCostume(int costumeID) {
		setCostumeID(costumeID);
	}
}
