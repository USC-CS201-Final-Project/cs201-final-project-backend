package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import io.github.usc_cs201_final_project.cs201_final_project_backend.packets.*;

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
	
	private ClientState clientState;
	
	public ClientConnectionThread(Socket connection, int clientID) throws IOException {
		this.connection = connection;
		this.clientID = clientID;
		this.clientState = ClientState.Authenticating;
		
		pw = new PrintWriter(connection.getOutputStream());
		br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}
	
	public void setGame(GameManager manager) {
		this.manager = manager;
		this.clientState = ClientState.InGame;
	}
	
	public void run() {
		
		try{
			while(true) {
				String line = br.readLine();
				manager.sendPacket(line);
				//TODO Instantiate player
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
		ServerGameStartPacket startpacket = new ServerGameStartPacket(usernames, startBossHP, words, costumeIDs);
		
		//use send packet to send the startpacket json to the client
		sendPacket(new Gson().toJson(startpacket));
	}
	
	public void sendGameOverPacket(int wpm) {
		//TODO
		clientState = ClientState.PostGame;
	}
	
	public void sendBossAttack()
	{
		//send a message that shows that a boss is attacking
		pw.println("Boss is Attacking!");
		pw.flush();
	}

	public void sendCostumeChange(int playerID, int costumeID)
	{
		ClientAuthenticationPacket sendCostumeChangePacket = new ClientAuthenticationPacket(playerID, costumeID);
		sendPacket(new Gson().toJson(sendCostumeChangePacket));
	}

	public void playerAttack(int playerID, String newWord, int bossHP)
	{
		ServerGameplayPacket playerAttackPacket = new ServerGameplayPacket(playerID, newWord, bossHP);
		sendPacket(new Gson().toJson(playerAttackPacket));
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void sendAuthentication(boolean isValid)
	{
		//Send authentication results to front end
		if (isValid) clientState = ClientState.Loading;
	}
	
	public void sendPacket(String packet) {
		//send the packet to all the client by printing it serially
		pw.println(packet);
		pw.flush();
	}
	
	private enum ClientState {
		Authenticating,
		Loading,
		InGame,
		PostGame
	}
}
