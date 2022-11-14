package io.github.usc_cs201_final_project.cs201_final_project_backend;

public class Player extends Combatant {
	private String username;

	public Player(String u, int cid, int health) {
		super(cid, health);
		username = u;
	}
	
	public String getUsername() {
		return username;
	}
}
