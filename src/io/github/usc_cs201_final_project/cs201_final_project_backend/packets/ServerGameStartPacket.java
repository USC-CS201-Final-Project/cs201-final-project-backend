package io.github.usc_cs201_final_project.cs201_final_project_backend.packets;

import java.util.List;

public class ServerGameStartPacket {
	public List<String> usernames;
    public int startingPlayerHealth;
    public int startingBossHealth;
    public List<String> startingWord;
    public List<Integer> startingCostumeID;
    public int bossCostumeID;

    public ServerGameStartPacket(List<String> u, int sph, int sbh, List<String> sw, List<Integer> scid, int bcid)
    {
        usernames = u;
        startingPlayerHealth = sph;
        startingBossHealth = sbh;
        startingWord = sw;
        startingCostumeID = scid;
        bossCostumeID = bcid;
    }
}
