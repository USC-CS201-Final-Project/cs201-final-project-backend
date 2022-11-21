package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;


public class ClientConnectionThread extends Thread {
	
	//data members
	private PrintWriter pw;
	private BufferedReader br;
	@SuppressWarnings("unused")
	private Socket connection;
	@SuppressWarnings("unused")
	private GameManager manager;
	private Player player;
	@SuppressWarnings("unused")
	private int clientID;
	
	public ClientConnectionThread(Socket connection, GameManager manager, Player player, int clientID) throws IOException {
		this.connection = connection;
		this.manager = manager;
		this.player = player;
		this.clientID = clientID;
		
		pw = new PrintWriter(connection.getOutputStream());
		br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}
	
	public void run() {
		
		try{
			while(true) {
				@SuppressWarnings("unused")
				String line = br.readLine();
				//manager.broadcastStart();
			}
		}
		catch(IOException ioe) {
			System.out.println("ioe in ServerThread.run(): " + ioe.getMessage());
		}
		
	}
	
	/**
	 * Creates a Server gameStart packet and serializes it
	 * sends it to stream
	 * @throws IOException 
	 * @throws JsonIOException 
	 */
	public void sendStart(List<String> usernames, int startBossHP, List<String> words,
			List<Integer> costumeIDs, int bossCostume) throws JsonIOException, IOException {
		
		StartPacket startpacket = new StartPacket(usernames, startBossHP, words, costumeIDs, bossCostume);
		
		Gson gson = new GsonBuilder()
				  .setPrettyPrinting()
				  .create();
		
		String filePath = "startpacket";
		gson.toJson(startpacket, new FileWriter(filePath));
		
	}
	
	public void sendBossAttack()
	{
		//send a message that shows that a boss is attacking
		pw.println("Boss is Attacking!");
		pw.flush();
	}

	public Player getPlayer() {
		return player;
	}
}
