package io.github.usc_cs201_final_project.cs201_final_project_backend.packets;

public class ServerGameplayPacket {
	public int packetID;
    public int bossHP;
    public int playerHP;
    public String newWord;
    public int playerID;
    public int[] costumeID;

    public ServerGameplayPacket(int pid, int bhp, int php, String w, int p, int[] cid) {
        packetID = pid;
        bossHP = bhp;
        playerHP = php;
        newWord = w;
        playerID = p;
        costumeID = cid;
    }
}
