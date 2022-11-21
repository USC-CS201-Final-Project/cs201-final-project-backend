package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.util.List;

public class ClientConnectionThread extends Thread {
	private Player player;
	private int clientID;
	
	public ClientConnectionThread(int clientID) {
		this.clientID = clientID;
	}
	
	@Override
	public void run() {}
	
	/**
	 * Creates a Server gameStart packet and serializes it
	 * sends it to stream
	 */
	public void sendStart(List<String> usernames, int startBossHP, List<String> words,
			List<Integer> costumeIDs) {
		
	}
	public void sendBossAttack()
	{
		//send a message that shows that a boss is attacking
	}
	
	public void sendCostumeChange(int playerID, int costumeID)
	{
		
	}

	public void playerAttack(int playerID, String newWord, int bossHP)
	{
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void sendAuthentication(boolean isValid)
	{
		
	}
}
