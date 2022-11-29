package io.github.usc_cs201_final_project.cs201_final_project_backend.packets;

public class ServerAuthenticationPacket {
	public boolean isValid;

    public ServerAuthenticationPacket(boolean v) {
        isValid = v;
    }
}
