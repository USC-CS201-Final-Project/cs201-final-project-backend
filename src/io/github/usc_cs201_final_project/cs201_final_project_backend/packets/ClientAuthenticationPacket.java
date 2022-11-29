package io.github.usc_cs201_final_project.cs201_final_project_backend.packets;

public class ClientAuthenticationPacket extends PacketFormat {
	public String username;
    public String password;
    public boolean isGuest;
    public boolean registering;

    public ClientAuthenticationPacket(String u, String p, boolean g, boolean r)
    {
        username = u;
        password = p;
        isGuest = g;
        registering = r;
    }
    
    public boolean isValidFormat() {
    	return username != null && password != null;
    }
}
