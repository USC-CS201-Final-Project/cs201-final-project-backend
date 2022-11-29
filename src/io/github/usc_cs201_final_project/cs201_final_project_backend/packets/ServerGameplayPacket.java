package io.github.usc_cs201_final_project.cs201_final_project_backend.packets;

import java.util.List;

public class ServerGameplayPacket {
	public int packetID;
    public int bossHP;
    public int playerHP;
    public String newWord;
    public int playerID;
    public List<Integer> costumeID;

    public ServerGameplayPacket(int pid, int bhp, int php, String w, int p, List<Integer> costumeIDs) {
        packetID = pid; // bossAttack = 0, CostumeChange = 1, PlayerAttack = 2;
        bossHP = bhp;
        playerHP = php;
        newWord = w;
        playerID = p;
        costumeID = costumeIDs;
    }
}
