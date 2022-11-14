package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class NetworkManager {
	public static final int PLAYERS_PER_GAME = 4;
	public static final int PORT_NUMBER = 20100;
	
	private ArrayList<ClientConnectionThread> connections;
	private LinkedList<ClientConnectionThread> queue;
	private ServerSocket serverSocket;

	private ArrayList<GameManager> currentGames;
	
	public static void main(String[] args) {
		System.out.println("Hello, world!");
		NetworkManager n;
		try {
			n = new NetworkManager();
		}
		catch (IOException e) {
			System.out.println("[ERROR] Could not create NetworkManager:");
			e.printStackTrace();
			return;
		}
		
		while (true) {
			try {
				n.awaitClient();
				if (n.getPlayersInQueue() >= PLAYERS_PER_GAME) {
					System.out.println("[LOG] Starting new game.");
					n.startNextGame();
				}
			} catch (IOException e) {
				System.out.println("[ERROR] Could not await next client:");
				e.printStackTrace();
			}
		}
	}
	
	public NetworkManager() throws IOException {
		//TODO Synchronized or no?
		connections = new ArrayList<>();
		queue = new LinkedList<>();
		serverSocket = new ServerSocket(PORT_NUMBER);
		
		currentGames = new ArrayList<>();
		
	}
	
	public int getPlayersInQueue() {
		return queue.size();
	}
	
	public void awaitClient() throws IOException {
		ClientConnectionThread cli = new ClientConnectionThread(serverSocket.accept());
		connections.add(cli);
		queue.add(cli);
	}
	
	public void startNextGame() {
		ArrayList<ClientConnectionThread> players = new ArrayList<>();
		while (players.size() < PLAYERS_PER_GAME && ! queue.isEmpty()) players.add(queue.poll());
		GameManager game = new GameManager(players);
		game.startGame();
		currentGames.add(game);
	}
	
	public void removeGame(GameManager gm) {
		if (! currentGames.remove(gm)) System.out.println("[ERROR] Could not find game in list of current games!");
		for (ClientConnectionThread c : gm.getClients()) queue.add(c);
	}
}
