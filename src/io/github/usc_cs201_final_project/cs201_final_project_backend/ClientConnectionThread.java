package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
	private int clientID;
	private Gson gson = new Gson();
	private int wordsTyped = 0;
	
	private ClientState clientState;
	
	public ClientConnectionThread(Socket connection) throws IOException {
		this.connection = connection;
		this.clientState = ClientState.Authenticating;
		
		pw = new PrintWriter(connection.getOutputStream());
		br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}
	
	public void setGame(GameManager manager, int clientID) {
		this.manager = manager;
		this.clientID = clientID;
		this.clientState = ClientState.InGame;
	}
	
	/**
	 * notes: I moved the authentication methods to networkManager, so use those
	 * when we receive a completedWord packet, increment wordsTyped and call manager.completedWord()
	 * all other functions that do the work is in GameManager
	 */
	public void run() {
		try {
			while (true) {
				String line = br.readLine();
				manager.sendPacket(line);
				//TODO Instantiate player, process inputs. Joseph will handle this.
			}
		}
		catch (IOException ioe) {
			System.out.println("ioe in ServerThread.run(): " + ioe.getMessage());
			//remove this thread from NetworkManager(or game manager?) if connection drops
		}
	}
	
	public void sendAuthentication(boolean isValid) {
		sendPacketObject(new ServerAuthenticationPacket(isValid));
		
		if(isValid) {
			clientState = ClientState.Loading;
		}
	}
	
	public void sendGameOverPacket(int wpm) {
		sendPacketObject(new ServerGameOverPacket(wpm));
		
		clientState = ClientState.PostGame;	
	}
	
	public void sendBossAttackPacket() {
		//only thing entries that are important are packet id and playerHealth, everything else should be ignored
		//packetID 0 means BossAttack
		sendPacketObject(new ServerGameplayPacket(0, -1, player.getCurrentHealth(), "", -1, new ArrayList<Integer>()));
		
	}
	
	public void sendCostumeChangePacket(List<Integer> costumeIDs) {
		//packet ID 1 means CostumeChange
		//only requires costumeIDs and (maybe) clientID
		sendPacketObject(new ServerGameplayPacket(1, -1, -1, "", clientID, costumeIDs));
	}
	
	public void sendPlayerAttackPacket(int bossHP, String newWord) {
		//packet ID 2 means playerAttack
		//only requires bossHP, newWord, and playerID
		sendPacketObject(new ServerGameplayPacket(2, bossHP, -1, newWord, clientID, new ArrayList<Integer>()));
	}
	
	public void sendGameStartPacket(List<String> usernames, int bossHP, List<String> words, List<Integer> costumes) {
		//last arg is boss costume id, not sure if still needed
		sendPacketObject(new ServerGameStartPacket(usernames, player.getMaxHealth(), bossHP, words, costumes, 0)); 
	}

	public Player getPlayer() {
		return player;
	}
	

	public void sendPacket(String packet) {
		pw.println(packet);
		pw.flush();
	}
	
	public void sendPacketObject(Object o) {
		pw.println(NetworkManager.toJsonString(o));
		pw.flush();
	}
	
	public int getWordsTyped() {
		return wordsTyped;
	}

	private enum ClientState {
		Authenticating,
		Loading,
		InGame,
		PostGame
	}
}
