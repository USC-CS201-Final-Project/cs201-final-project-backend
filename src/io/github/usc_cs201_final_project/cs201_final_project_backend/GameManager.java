package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonIOException;

public class GameManager extends Thread {
	private Boss boss;
	private ArrayList<ClientConnectionThread> clients;
	private List<Integer> costumes = new ArrayList<Integer>();

	private DatabaseManager databaseManager;
	private NetworkManager networkManager;
	private long startTime;
	private int numAttacks = 0;
	private boolean gameOver = false;
	
	private static int maxBossHealth = 100;
	private static int numBosses = 3;
	private static int playerAttackDamage = 10;
	private static int bossAttackDamage = 5;
	
	GameManager(ArrayList<ClientConnectionThread> clients, DatabaseManager db, NetworkManager nm) throws JsonIOException, IOException {
		//network managers passes the client threads
		this.clients = clients;
		this.databaseManager = db;
		this.networkManager = nm;
		startGame();
		this.run();
	}
	
	/*
	 * initializes startTime and generates a boss
	 * creates and broadcasts GameStart packet
	 */
	public void startGame() throws JsonIOException, IOException {
		Random rand = new Random();
		
		//do bosses even need a costume ID?
		//generates a boss id from 0 to numBosses-1
		boss = new Boss(maxBossHealth, 0); // *edit* got rid of third parameter
		
		List<String> usernames = new ArrayList<String>();
		List<String> words = new ArrayList<String>();
		
		//collects all usernames from the client threads
		//picks a randomly generated word from database for player to start with
		int i = 0;
		for(ClientConnectionThread client : clients) {
			client.setGame(this, i);
			i++;
			usernames.add(client.getPlayer().getUsername());
			words.add(databaseManager.getWord());
			costumes.add(client.getPlayer().getCostumeID());
		}
		
		startTime = System.currentTimeMillis();
		broadcastStart(usernames, words, costumes);		
	}
	
	public void run() {
		//every 5 seconds send a boss attack (-12 hp)
		while(!gameOver)
		{
			//every five seconds boss attack
			if ((System.currentTimeMillis() - startTime) > (numAttacks + 1)*(5000))
			{
				for(ClientConnectionThread client : clients) {
					client.getPlayer().takeDamage(bossAttackDamage);
					client.sendBossAttackPacket();
					
					//if player health reaches 0, they lose, and game is over
					if(client.getPlayer().getCurrentHealth() <= 0) {
						gameOver = true;
					}
				}
			}
		}
		//send end game packet
		int timeElapsed = (int) (System.currentTimeMillis() - startTime) / 1000;
		for(ClientConnectionThread client : clients) {
			client.sendGameOverPacket(client.getWordsTyped() / timeElapsed);
		}
		networkManager.removeGame(this);	
	}
	
	public void broadcastStart(List<String> usernames, List<String> words, List<Integer> costumes) throws JsonIOException, IOException {
		
		//sends the same gameStart packet to each client
		for(ClientConnectionThread client : clients) {
			client.sendGameStartPacket(usernames, boss.getMaxHealth(), words, costumes); //*edited*
		}
	}
	
	public void updateCostume(Player sourcePlayer, int costumeID) {
		databaseManager.changeCostumeID(sourcePlayer.getUsername(), costumeID);
		costumes.set(sourcePlayer.getId(), costumeID);
		
		for (ClientConnectionThread client : clients) {
			client.sendCostumeChangePacket(costumes);
		}
	}
	
	public void completedWord(Player sourcePlayer) {
		
		String word = databaseManager.getWord();
		boss.takeDamage(playerAttackDamage);
		for(ClientConnectionThread client : clients) {
			client.sendPlayerAttackPacket(boss.getCurrentHealth(), word, sourcePlayer.getId());
		}
		//if player attack kills boss, game is over
		if(boss.getCurrentHealth() <= 0) {
			gameOver = true;
		}
	}
	
	public void rejoinQueue(ClientConnectionThread c) {
		networkManager.rejoinQueue(c);
	}
	
	public Iterable<ClientConnectionThread> getClients() {
		return clients;
	}
	
	public void sendPacket(String s) {
		for (ClientConnectionThread c : clients) c.sendPacket(s);
	}
	
}
