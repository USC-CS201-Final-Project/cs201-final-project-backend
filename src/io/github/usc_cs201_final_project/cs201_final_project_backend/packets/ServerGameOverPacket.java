package io.github.usc_cs201_final_project.cs201_final_project_backend.packets;

public class ServerGameOverPacket {
	public int wordsPerMinute;

    public ServerGameOverPacket(int wpm)
    {
        wordsPerMinute = wpm;
    }
}
