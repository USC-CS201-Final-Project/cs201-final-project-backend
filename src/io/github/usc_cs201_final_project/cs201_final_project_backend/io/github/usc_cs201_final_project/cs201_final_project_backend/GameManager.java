package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.JsonIOException;

public class GameManager extends Thread{
	private Boss boss;
	private ArrayList<ClientConnectionThread> clients;
	private DatabaseManager databaseManager;
	private long startTime;
	private int numAttacks = 0;
	
	
	private static int maxBossHealth = 100; 
	private static int numBosses = 3;
	private static int damage = 10;
	GameManager(ArrayList<ClientConnectionThread> clients, DatabaseManager db) throws JsonIOException, IOException {
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
	public void startGame() throws JsonIOException, IOException {
		Random rand = new Random();
		
		//do bosses even need a costume ID?
		//generates a boss id from 0 to numBosses-1
		boss = new Boss(maxBossHealth, 0); // *edit* got rid of third parameter
		
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
		}
	}
	
	public void broadcastStart(List<String> usernames, List<String> words, List<Integer> costumes) throws JsonIOException, IOException {
		
		//sends the same gameStart packet to each client
		for(ClientConnectionThread client : clients) {
			client.sendStart(usernames, boss.getMaxHealth(), words, costumes); //*edited*
		}
	}
	
	public void updateCostume(int clientID, String username, int costumeID) {
		databaseManager.changeCostumeID(username, costumeID);
		for(ClientConnectionThread client : clients) {
			client.sendCostumeChange(clientID, costumeID);
		}
	}
	
	public void completedWord(int clientID, String username) {
		
		String word = databaseManager.getWord();
		boss.takeDamage(damage);
		for(ClientConnectionThread client : clients) {
			client.playerAttack(clientID, word, boss.getCurrentHealth());
		}
	}
	
	public void sendPacket(String packet) {
		for(ClientConnectionThread client : clients) {
			client.sendPacket(packet);
		}
	}
	
	//called by clientConnectionThread when creating new acc, checks if username exists, if it does return false
	//if not creates account
	//returns value "isValid" for server authentication packet
	public boolean createUser(String username, String password)
	{
		if (databaseManager.userExists(username))
		{
			return false;
		}
		else
		{
			databaseManager.createUser(username, password);
			return true;
		}
		
	}
	
	public boolean authenticateUser(String username, String password)
	{
		if(databaseManager.userExists(username))
		{
			databaseManager.authenticateUser(username, password);
			return true;
		}
		else
		{
			return false;
		}
	}
}