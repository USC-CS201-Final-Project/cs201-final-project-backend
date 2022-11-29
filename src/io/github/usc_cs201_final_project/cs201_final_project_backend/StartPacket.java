package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.util.List;

public class StartPacket {
	public List<String> usernames;
	public int startBossHP;
	public List<String> words;
	public List<Integer> costumeIDs;
	
	public StartPacket(List<String> usernames, int startBossHP, List<String> words,
			List<Integer> costumeIDs) {
		this.usernames = usernames;
		this.startBossHP = startBossHP;
		this.words = words;
		this.costumeIDs = costumeIDs;
	}
	
}
