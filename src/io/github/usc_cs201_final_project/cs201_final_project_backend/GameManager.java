package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager extends Thread{
	private Boss boss;
	private ArrayList<ClientConnectionThread> clients;
	private DatabaseManager databaseManager;
	private long startTime;
	private int numAttacks = 0;
	
	static int maxBossHealth = 100;
	static int numBosses = 3;
	
	GameManager(ArrayList<ClientConnectionThread> clients, DatabaseManager db) {
		//network managers passes the client threads
		this.clients = clients;
		this.databaseManager = db;
		startGame();
		this.run();
	}
	
	/*
	 * initializes startTime and generates a boss
	 * creates and broadcasts GameStart packet
	 */
	public void startGame() {
		Random rand = new Random();
		
		//do bosses even need a costume ID?
		//generates a boss id from 0 to numBosses-1
		boss = new Boss(maxBossHealth, 0, rand.nextInt(numBosses));
		
		List<String> usernames = new ArrayList<String>();
		List<String> words = new ArrayList<String>();
		List<Integer> costumes = new ArrayList<Integer>();
		
		//collects all usernames from the client threads
		//picks a randomly generated word from database for player to start with
		for(ClientConnectionThread client : clients) {
			usernames.add(client.getPlayer().getUsername());
			words.add(databaseManager.getWord());
			costumes.add(client.getPlayer().getCostumeID());
		}
		
		startTime = System.currentTimeMillis();
		broadcastStart(usernames, words, costumes);		
	}
	
	public void run() {
		//every 5 seconds send a boss attack (-12 hp)
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
	
	public void broadcastStart(List<String> usernames, List<String> words, List<Integer> costumes) {
		
		//sends the same gameStart packet to each client
		for(ClientConnectionThread client : clients) {
			client.sendStart(usernames, boss.getMaxHealth(), words, costumes, boss.getId());
		}
	}
	
	public void updateCostume(int clientID, String username) {
		
	}
	
	public void completedWord(int clientID, String username) {
		
	}
}
