package io.github.usc_cs201_final_project.cs201_final_project_backend.packets;

public class ClientGameplayPacket {
	public boolean completedWord;
    public int costumeID;

    public ClientGameplayPacket(boolean c, int cid)
    {
        completedWord = c;
        costumeID = cid;
    }
}
