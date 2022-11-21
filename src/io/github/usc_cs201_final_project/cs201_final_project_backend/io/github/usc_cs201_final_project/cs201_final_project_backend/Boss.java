package io.github.usc_cs201_final_project.cs201_final_project_backend;

public class Boss extends Combatant {
	private int id;

	Boss(int maxHealth, int costumeID, int id) {
		super(maxHealth,  costumeID);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}