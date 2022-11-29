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
	
	private ClientState clientState;
	
	public ClientConnectionThread(Socket connection) throws IOException {
		this.connection = connection;
		this.clientState = ClientState.Authenticating;
		
		pw = new PrintWriter(connection.getOutputStream());
		br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}
	
	public void setGame(GameManager manager) {
		this.manager = manager;
		this.clientState = ClientState.InGame;
	}
	
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
		}
	}
	
	public void sendAuthentication(boolean isValid) {
		//TODO Send packet and update ClientState if necessary.
	}
	
	public void sendGameOverPacket(int wpm) {
		//TODO Send packet and update ClientState if necessary.
	}
	
	public void sendBossAttackPacket() {
		//TODO Send packet and update ClientState if necessary.
	}
	
	public void sendCostumeChangePacket(int[] costumeIDs) {
		//TODO Verify complete
		sendPacketObject(new ServerGameplayPacket(1, -1, -1, "", -1, costumeIDs));
	}
	
	public void sendPlayerAttackPacket(int bossHP, String newWord, int playerID) {
		//TODO Verify complete
		sendPacketObject(new ServerGameplayPacket(2, bossHP, -1, newWord, playerID, new int[0]));
	}
	
	public void sendGameStartPacket() {
		//TODO Send packet and update ClientState if necessary.
	}
	
	public void sendGameOverPacket() {
		//TODO Send packet and update ClientState if necessary.
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
	
	private enum ClientState {
		Authenticating,
		Loading,
		InGame,
		PostGame
	}
}
