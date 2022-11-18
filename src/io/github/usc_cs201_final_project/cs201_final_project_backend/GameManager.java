package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.util.ArrayList;
import java.util.List;

public class GameManager extends Thread{
	private Boss boss;
	private ArrayList<ClientConnectionThread> clients;
	private long startTime;
	private int numAttacks = 0;
	
	GameManager(ArrayList<ClientConnectionThread> clients) {
		//network managers passes the client threads
		this.clients = clients;
		startGame();
	}
	
	public void startGame() {
		List<String> usernames = new ArrayList<String>();
		for(ClientConnectionThread client : clients) {
			usernames.add(client.getPlayer().getUsername());
		}
		
		startTime = System.currentTimeMillis();
		
		
		
		//check for game-play packets, parse and apply damage
		while(true)
		{
			//every five seconds boss attack
			if ((System.currentTimeMillis() - startTime) > (numAttacks + 1)*(5000))
			{
				for(ClientConnectionThread client : clients) {
					client.sendBossAttack();
				}
			}
			
			
			//also check for game-play packets
		}
	}
}
