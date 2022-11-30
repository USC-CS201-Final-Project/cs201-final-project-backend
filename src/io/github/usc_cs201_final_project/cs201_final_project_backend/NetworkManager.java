package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

import com.google.gson.Gson;

public class NetworkManager {
	public static final int PLAYERS_PER_GAME = 4;
	public static final int PORT_NUMBER = 8080;
	
	private static DatabaseManager db;
	private static Gson gson;
	
	private static ArrayList<GameManager> currentGames;
	private ArrayList<ClientConnectionThread> connections;
	private static LinkedList<ClientConnectionThread> queue;
	private ServerSocket serverSocket;

	
	public static void main(String[] args) {
		System.out.println("Hello, world!");

		db = new DatabaseManager();
		gson = new Gson();
		
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
				//System.out.println("Awaiting Client");
				n.awaitClient();
				//System.out.println("Client Received");
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
	
	public static int getPlayersInQueue() {
		return queue.size();
	}
	
	public void awaitClient() throws IOException {
		ClientConnectionThread cli = new ClientConnectionThread(serverSocket.accept());
		connections.add(cli);
		//queue.add(cli);
	}
	
	public static void startNextGame() throws IOException {
		//System.out.println("THIS IS BEING RUN");
		ArrayList<ClientConnectionThread> players = new ArrayList<>();
		int i = 0;
		while (players.size() < PLAYERS_PER_GAME && ! queue.isEmpty()) {
			ClientConnectionThread p = queue.poll();
			players.add(p);
			p.getPlayer().enterGame(i++, 60);
		}
		//System.out.println("Made past while loop");
		GameManager game = new GameManager(players, db);
		//System.out.println("Made it past startGame");
		//System.out.println("Size of games = " + currentGames.size());
		if(! currentGames.add(game)) System.out.println("[ERROR] Could not add game to list of current games!");
		//System.out.println("Size of games = " + currentGames.size());
	}
	
	public static void removeGame(GameManager gm) {
		//System.out.println("Size of games = " + currentGames.size());
		if (! currentGames.remove(gm)) System.out.println("[ERROR] Could not find game in list of current games??!");
	}
	
	public static void rejoinQueue(ClientConnectionThread c) throws IOException {
		queue.add(c);
		if (getPlayersInQueue() >= PLAYERS_PER_GAME) {
			System.out.println("[LOG] Starting new game.");
			startNextGame();
		}
	}
	
	//called by clientConnectionThread when creating new acc, checks if username exists, if it does return false
	//if not creates account
	//returns value "isValid" for server authentication packet
	public static boolean createUser(String username, String password)
	{
		if (db.userExists(username))
		{
			return false;
		}
		else
		{
			db.createUser(username, password);
			return true;
		}
		
	}
	
	public static boolean authenticateUser(String username, String password)
	{
		if(db.userExists(username))
		{
			db.authenticateUser(username, password);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static synchronized DatabaseManager getDBManager() {
		return db;
	}
	
	public static String toJsonString(Object o) {
		return gson.toJson(o);
	}
	
	public static Gson getGson() {
		return gson;
	}
}
