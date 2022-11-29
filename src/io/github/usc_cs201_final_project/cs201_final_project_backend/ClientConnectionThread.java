package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonSyntaxException;

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
	}
	
	/**
	 * notes: I moved the authentication methods to networkManager, so use those
	 * when we receive a completedWord packet, increment wordsTyped and call manager.completedWord()
	 * all other functions that do the work is in GameManager
	 */
	public void run() {
		try {
			while (true) {
				String p = "";
				//TODO Make sure that there's exactly one complete packet per line
				if (br.ready()) p = br.readLine();
				
				switch(clientState) {
					case Authenticating:
						try {
							ClientAuthenticationPacket cap = NetworkManager.getGson().fromJson(p, ClientAuthenticationPacket.class);
							if (cap.isValidFormat()) {
								
								boolean valid;
								if (cap.registering) valid = NetworkManager.createUser(cap.username, cap.password);
								else valid = NetworkManager.authenticateUser(cap.username, cap.password);
								
								sendAuthentication(valid);
								if (valid) {
									int costume = NetworkManager.getDBManager().getCostumeID(cap.username);
									player = new Player(cap.username, costume);
								}
							}
						}
						catch (JsonSyntaxException e) {
							System.out.println("JsonSyntaxException in CCT:\n" + p);
						}
						
						break;
					case Loading:
						//If loading, we don't expect any packets at all.
						//  We do nothing with this packet (essentially discarding it)
						break;
					case InGame:
						try {
							ClientGameplayPacket cgp = NetworkManager.getGson().fromJson(p, ClientGameplayPacket.class);
							if (cgp.isValidFormat()) {
								if (cgp.completedWord) manager.completedWord(player);
								if (cgp.costumeID != player.getCostumeID()) manager.updateCostume(player, cgp.costumeID);
							}
							
						}
						catch (JsonSyntaxException e) {
							System.out.println("JsonSyntaxException in CCT:\n" + p);
						}
						break;
					case PostGame:
						try {
							ClientPlayAgainPacket cpap = NetworkManager.getGson().fromJson(p, ClientPlayAgainPacket.class);
							if (cpap.isValidFormat()) {
								if (cpap.playAgain) {
									manager.rejoinQueue(this);
									this.manager = null;
								}
							}
						}
						catch (JsonSyntaxException e) {
							System.out.println("JsonSyntaxException in CCT:\n" + p);
						}
						break;
				}
			}
		}
		catch (IOException ioe) {
			System.out.println("ioe in ServerThread.run(): " + ioe.getMessage());
			//remove this thread from NetworkManager(or game manager?) if connection drops
		}
	}
	
	public void sendAuthentication(boolean isValid) {
		if (isValid) clientState = ClientState.Loading;
		sendPacketObject(new ServerAuthenticationPacket(isValid));
	}
	
	public void sendGameOverPacket(int wpm) {
		clientState = ClientState.PostGame;
		sendPacketObject(new ServerGameOverPacket(wpm));
	}
	
	public void sendBossAttackPacket() {
		//only thing entries that are important are packet id and playerHealth, everything else should be ignored
		//packetID 0 means BossAttack
		sendPacketObject(new ServerGameplayPacket(1, -1, player.getCurrentHealth(), "", -1, new ArrayList<Integer>()));
		
	}
	
	public void sendCostumeChangePacket(List<Integer> costumeIDs) {
		//packet ID 1 means CostumeChange
		//only requires costumeIDs and (maybe) clientID
		sendPacketObject(new ServerGameplayPacket(2, -1, -1, "", clientID, costumeIDs));
	}
	
	public void sendPlayerAttackPacket(int bossHP, String newWord, int playerId) {
		//packet ID 2 means playerAttack
		//only requires bossHP, newWord, and playerID
		sendPacketObject(new ServerGameplayPacket(3, bossHP, -1, newWord, playerId, new ArrayList<Integer>()));
	}
	
	public void sendGameStartPacket(List<String> usernames, int bossHP, List<String> words, List<Integer> costumes) {
		clientState = ClientState.InGame;
		//last arg is boss costume id, not sure if still needed
		sendPacketObject(new ServerGameStartPacket(usernames, player.getMaxHealth(), bossHP, words, costumes, 0)); 
	}

	public Player getPlayer() {
		return player;
	}
	

	public synchronized void sendPacket(String packet) {
		pw.println(packet);
		pw.flush();
	}
	
	public synchronized void sendPacketObject(Object o) {
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
