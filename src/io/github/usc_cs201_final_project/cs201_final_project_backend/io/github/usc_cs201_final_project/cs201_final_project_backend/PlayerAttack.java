package io.github.usc_cs201_final_project.cs201_final_project_backend;

public class PlayerAttack {
	public int playerID;
	public String newWord;
	public int bossHP;
	
	public PlayerAttack(int playerID, String newWord, int bossHP) {
		this.playerID = playerID;
		this.newWord = newWord;
		this.bossHP = bossHP;
	}
}
