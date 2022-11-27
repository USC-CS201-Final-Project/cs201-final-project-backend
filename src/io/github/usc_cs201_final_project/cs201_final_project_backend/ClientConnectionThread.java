package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
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
				String line = br.readLine();
				manager.sendPacket(line);
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
			List<Integer> costumeIDs) throws JsonIOException, IOException {
		
		//create a start packet object
		StartPacket startpacket = new StartPacket(usernames, startBossHP, words, costumeIDs);
		
		//use send packet to send the startpacket json to the client
		sendPacket(new Gson().toJson(startpacket));
		
	}
	
	public void sendBossAttack()
	{
		//send a message that shows that a boss is attacking
		pw.println("Boss is Attacking!");
		pw.flush();
	}

	public void sendCostumeChange(int playerID, int costumeID)
	{
		SendCostumeChange sendCostumeChangePacket = new SendCostumeChange(playerID, costumeID);
		sendPacket(new Gson().toJson(sendCostumeChangePacket));
	}

	public void playerAttack(int playerID, String newWord, int bossHP)
	{
		PlayerAttack playerAttackPacket = new PlayerAttack(playerID, newWord, bossHP);
		sendPacket(new Gson().toJson(playerAttackPacket));
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void sendAuthentication(boolean isValid)
	{
		
	}
	
	public void sendPacket(String packet) {
		//send the packet to all the client by printing it serially
		pw.println(packet);
		pw.flush();
	}
}
