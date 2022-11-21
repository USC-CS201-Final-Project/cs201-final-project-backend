package io.github.usc_cs201_final_project.cs201_final_project_backend;

public class SendCostumeChange {
	public int playerID;
	public int costumeID;
	
	public SendCostumeChange(int playerID, int costumeID) {
		this.playerID = playerID;
		this.costumeID = costumeID;
	}
}
