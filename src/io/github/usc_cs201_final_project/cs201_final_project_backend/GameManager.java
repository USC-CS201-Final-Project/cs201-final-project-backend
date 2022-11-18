package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.util.ArrayList;
import java.util.List;

public class GameManager extends Thread{
	private Boss boss;
	private ArrayList<ClientConnectionThread> clients;
	
	GameManager(ArrayList<ClientConnectionThread> clients) {
		network managers passes the client threads
		this.clients = clients;
		startGame();
	}
	
	public void startGame() {
		List<String> usernames = new ArrayList<String>();
		for(ClientConnectionThread client : clients) {
			usernames.add(client.getPlayer().getUsername());
		}
		
	}
}
