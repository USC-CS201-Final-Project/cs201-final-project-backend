package io.github.usc_cs201_final_project.cs201_final_project_backend;

public class ClientConnectionThread extends Thread {
	
	//data members
	private Socket connection;
	private GameManager manager;
	private Player player;
	
	public ClientConnectionThread(Socket c, GameManager m, Player p) {
		this.connection = c;
		this.manager = m;
		this.player = p;
	}
	
	private void syncOtherPlayers(int BossHP, Player playerWhoPerformedAction, int costumeID, String newWord){
		//update bossHP to all players
		
		//give the player who attaked a new word
		
		//if a player changed costumes, update that player's costume to all the other players
		
	}
	
	private void BossAttack(int newplayerHealth){ //sync the new player health to all the players 
		//sync player health every 12 seconds
		//5 total sync should occur since total starting player health is 60
		
		
	}	
	@Override
	public void run() {
		
	}
}
