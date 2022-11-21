package io.github.usc_cs201_final_project.cs201_final_project_backend;

import java.util.List;

public class StartPacket {
	@SuppressWarnings("unused")
	private List<String> usernames;
	@SuppressWarnings("unused")
	private int startBossHP;
	@SuppressWarnings("unused")
	private List<String> words;
	@SuppressWarnings("unused")
	private List<Integer> costumeIDs;
	@SuppressWarnings("unused")
	private int bossCostume;
	
	public StartPacket(List<String> usernames, int startBossHP, List<String> words,
			List<Integer> costumeIDs, int bossCostume) {
		this.usernames = usernames;
		this.startBossHP = startBossHP;
		this.words = words;
		this.costumeIDs = costumeIDs;
		this.bossCostume = bossCostume;
	}
	
}
