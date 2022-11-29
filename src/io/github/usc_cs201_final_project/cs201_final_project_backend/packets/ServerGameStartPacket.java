package io.github.usc_cs201_final_project.cs201_final_project_backend.packets;

public class ServerGameStartPacket {
	public String[] usernames;
    public int startingPlayerHealth;
    public int startingBossHealth;
    public String[] startingWord;
    public int[] startingCostumeID;
    public int bossCostumeID;

    public ServerGameStartPacket(String[] u, int sph, int sbh, String[] sw, int[] scid, int bcid)
    {
        usernames = u;
        startingPlayerHealth = sph;
        startingBossHealth = sbh;
        startingWord = sw;
        startingCostumeID = scid;
        bossCostumeID = bcid;
    }
}
